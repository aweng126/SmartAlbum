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



    //定位
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
         mLocationOption.setInterval(1000);

        //设置定位一次
       // mLocationOption.setOnceLocation(true);
        //mLocationOption.setOnceLocationLatest(true);


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
        setCurrentLocation(lat,lng);

        mUiSetting=map.getUiSettings();
        mUiSetting.setAllGesturesEnabled(true);

        //比例尺控件
        mUiSetting.setScaleControlsEnabled(true);

        /**
         * 设置地图显示监听
         */
        map.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                double lat = cameraPosition.target.latitude;
                double lng = cameraPosition.target.longitude;
                //setCurrentLocation(lat, lng);
            }
        });

        map.setMyLocationEnabled(true);


        /**
         * 添加覆盖物
         */

        //初始化数据
        points= DataHelper.getLongLati();

        double lng1=Double.parseDouble(points.get(0))-0.0065;

        double lat1=Double.parseDouble(points.get(1))-0.0060;

        double lng2=Double.parseDouble(points.get(2))+0.0228;

        double lat2=Double.parseDouble(points.get(3))-0.0189;

        Log.e(TAG,"lat变化值"+lat+" "+lat2+" "+"lng变化值 "+lng+" "+lng2)                      ;

       // private double lng=117.140803;

        //默认纬度
        //private double lat=36.667634;



        Log.e(TAG, "onCreateView: lat1"+lat1+" "+lng1+" "+lat2+" "+lng2 );

       //第一个点
        map.addMarker(new MarkerOptions()
                .position(new LatLng(lat1, lng1))
                .title("marker1")
                        //.icon(dfd)
                .snippet("DefaultMaker1"))
                 ;

         //第二个点
        map.addMarker(new MarkerOptions()
                .position(new LatLng(lat2, lng2))
                .title("marker2")
                        // .icon(dfd)
                .snippet("DefaultMaker2"));

        /**
         * 添加覆盖物的监听事件
         */
        map.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                Intent intent=new Intent(getActivity(),ShowPhotoBySite.class);

                switch (marker.getTitle()){
                    case "marker1":

                        Toast.makeText(getActivity(),"marker1",Toast.LENGTH_SHORT).show();

                            //可以传递数据，传递我们当前点的经纬度
                        intent.putExtra("lng",points.get(0));
                        intent.putExtra("lat",points.get(1));

                        startActivity(intent);

                        break;
                    case "marker2":
                        //可以传递数据，传递我们当前点的经纬度
                        intent.putExtra("lng",points.get(2));
                        intent.putExtra("lat",points.get(3));
                        Toast.makeText(getActivity(),"marker2",Toast.LENGTH_SHORT).show();

                        startActivity(intent);

                        break;
                    default:{


                    }
                }
                return false;
            }
        });

        return view;

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

    //可以通过类implement方式实现AMapLocationListener接口，也可以通过创造接口类对象的方法实现
   //以下为后者的举例：
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

            Log.e(TAG, "onLocationChanged: lat  " + lat + " lnt" + lng);

        }
    };

    //设置当前定位中心
    public  void setCurrentLocation(double lat,double lng){


        CameraPosition cp=map.getCameraPosition();

        float zoom=firstLocation?15:cp.zoom;
       // CameraPosition cpNew = CameraPosition.fromLatLngZoom(new LatLng(lat-0.065,lng-0.060), zoom);
        CameraPosition cpNew = CameraPosition.fromLatLngZoom(new LatLng(lat,lng), zoom);
        CameraUpdate cu = CameraUpdateFactory.newCameraPosition(cpNew);
        map.moveCamera(cu);
        firstLocation=false;
    }


}
