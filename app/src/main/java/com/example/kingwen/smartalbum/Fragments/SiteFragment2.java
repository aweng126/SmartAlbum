package com.example.kingwen.smartalbum.Fragments;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.example.kingwen.smartalbum.Activities.ShowPhotoBySite;
import com.example.kingwen.smartalbum.R;
import com.example.kingwen.smartalbum.Utils.DataHelper;
import com.example.kingwen.smartalbum.Utils.DateFormatUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by kingwen on 2016/11/27.
 * 用于测试我们之后的定位功能
 */
public class SiteFragment2 extends Fragment {

    public  static  final  String TAG="SiteFragment2";

    private MapView mapView;

    private AMap map;

   //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;

    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

   //默认精度
   private double lng=117.140803;

    //默认纬度
    private double lat=36.667634;


    //设置UIsetting
    private UiSettings mUiSetting;


    //地图要显示的点
    private ArrayList<String> points = new ArrayList<>();

    //判断是否是第一次定位
    private boolean firstLocation=true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //初始化定位
        mLocationClient = new AMapLocationClient(getActivity().getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mAMapLocationListener);

        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();

        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
       /* //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //设置定位模式为AMapLocationMode.Device_Sensors，仅设备模式。
        mLocationOption.setLocationMode(AMapLocationMode.Device_Sensors);
        */


        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        // mLocationOption.setInterval(1000);


        //设置定位一次
        mLocationOption.setOnceLocation(true);
        mLocationOption.setOnceLocationLatest(true);


        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);


        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
       //启动定位
        mLocationClient.startLocation();

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_site, container, false);
        mapView= (MapView) view.findViewById(R.id.map);

        mapView.onCreate(savedInstanceState);

        if(map==null){
            map=mapView.getMap();
        }

        //设置当前画面中心
        setCurrentLocation(lat, lng);

        setMapSetting();

        setMapMaker();

        setMakerListener();

        return view;

    }


    /**
     * 添加覆盖物的监听事件
     */
    private void setMakerListener() {

        map.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                Intent intent = new Intent(getActivity(), ShowPhotoBySite.class);

                switch (marker.getTitle()) {
                    case "marker1":

                        Toast.makeText(getActivity(), "marker1", Toast.LENGTH_SHORT).show();

                        //可以传递数据，传递我们当前点的经纬度
                        intent.putExtra("lng", points.get(0));
                        intent.putExtra("lat", points.get(1));
                        startActivity(intent);

                        break;
                    case "marker2":
                        //可以传递数据，传递我们当前点的经纬度
                        intent.putExtra("lng", points.get(2));
                        intent.putExtra("lat", points.get(3));
                        Toast.makeText(getActivity(), "marker2", Toast.LENGTH_SHORT).show();
                        startActivity(intent);

                        break;
                    default: {

                    }
                }
                return false;
            }
        });

    }


    /**
     * 设置覆盖物，在拍照最多的两个点上。
     */
    private void setMapMaker() {

        //得到makers的定位地址
        points= DataHelper.getLongLati();

        /**
         * 这里留下一个坑：关于地址的转换，地球地址和火星地址之间的转换
         *LatLng latlng=DateFormatUtil.getCorrectLatLng(getActivity(), lat2, lng2);
         *
         * 而下面的这里的地址转换,是通过手动调整的，非常的不优雅
         */


        // 精度
        double lng1=Double.parseDouble(points.get(0))+0.005;
        // 纬度
        double lat1=Double.parseDouble(points.get(1))+0.0015;

        double lng2=Double.parseDouble(points.get(2))+0.0228;

        double lat2=Double.parseDouble(points.get(3))-0.0189;


        //第一个点：拍照最多的一个点
        map.addMarker(new MarkerOptions()
                .position(new LatLng(lat1, lng1))
                .title("marker1")
                        //.icon(dfd)
                .snippet("DefaultMaker1"))
        ;

        //第二个点，拍多次多的一个位置
        map.addMarker(new MarkerOptions()
                .position(new LatLng(lat2, lng2))
                .title("marker2")
                        // .icon(dfd)
                .snippet("DefaultMaker2"));

    }



    /**
     * 设置map属性
     */
    private void setMapSetting() {

        mUiSetting=map.getUiSettings();
        //支持所有手势
        mUiSetting.setAllGesturesEnabled(true);
        //比例尺控件
        mUiSetting.setScaleControlsEnabled(true);
        //支持自我定位
        map.setMyLocationEnabled(true);
    }


    /**
     * 定位监听器
     */
      AMapLocationListener mAMapLocationListener = new AMapLocationListener(){
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {

            amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
           double lat= amapLocation.getLatitude();//获取纬度
            double  lng=amapLocation.getLongitude();//获取经度
            amapLocation.getAccuracy();//获取精度信息
            amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
            amapLocation.getCountry();//国家信息
            amapLocation.getProvince();//省信息
            amapLocation.getCity();//城市信息
            amapLocation.getDistrict();//城区信息
            amapLocation.getStreet();//街道信息
            amapLocation.getStreetNum();//街道门牌号信息
            amapLocation.getCityCode();//城市编码
            amapLocation.getAdCode();//地区编码
            amapLocation.getAoiName();//获取当前定位点的AOI信息
            amapLocation.getBuildingId();//获取当前室内定位的建筑物Id
            amapLocation.getFloor();//获取当前室内定位的楼层
            //amapLocation.getGpsStatus();//获取GPS的当前状态
            //获取定位时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(amapLocation.getTime());
            df.format(date);

            setCurrentLocation(lat,lng);

        }
    };

    /**
     *  设置屏幕地图中心位置
     */
    public  void setCurrentLocation(double lat,double lng){

        CameraPosition cp=map.getCameraPosition();
        float zoom=firstLocation?15:cp.zoom;
        CameraPosition cpNew = CameraPosition.fromLatLngZoom(new LatLng(lat,lng), zoom);
        CameraUpdate cu = CameraUpdateFactory.newCameraPosition(cpNew);
        map.moveCamera(cu);
        firstLocation=false;

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();

        mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
        mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。

    }


    @Override
    public  void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        mapView.onResume();
    }


    @Override
    public  void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mapView.onPause();
    }


    @Override
    public  void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mapView.onSaveInstanceState(outState);
    }


}
