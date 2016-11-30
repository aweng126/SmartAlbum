package com.example.kingwen.smartalbum.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.kingwen.smartalbum.Activities.MainActivity;
import com.example.kingwen.smartalbum.Activities.ShowPhotoBySite;
import com.example.kingwen.smartalbum.R;
import com.example.kingwen.smartalbum.Utils.DataHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kingwen on 2016/11/27.
 */
public class SiteFragment extends Fragment {

    /**
     * Map view
     */
    private MapView mapView = null;

    /**
     * 百度Map实例
     */
    private BaiduMap baiduMap = null;

    /**
     * 得到要定位的两个点
     */
    private ArrayList<String> points = new ArrayList<>();



    private String provider;

    private LocationManager locationManager;

    private Location location;


    private boolean isFirstLocate = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_site, container, false);
        mapView = (MapView) view.findViewById(R.id.bdmap);
        baiduMap = mapView.getMap();

        //显示当前位置：小圆点
        baiduMap.setMyLocationEnabled(true);

        locationManager= (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);


        //获取所有的位置控制器
        List<String> providerlist=locationManager.getProviders(true);
        if(providerlist.contains(LocationManager.GPS_PROVIDER)){
            provider=LocationManager.GPS_PROVIDER;
        }else if(providerlist.contains(LocationManager.NETWORK_PROVIDER)) {
            provider=LocationManager.NETWORK_PROVIDER;
        }else {
            Toast.makeText(getActivity(),"没有位置提供器可以用于使用",Toast.LENGTH_SHORT).show();
            return null;
        }

        Location location=locationManager.getLastKnownLocation(provider);
        if(location!=null){
            navigateTo(location);
        }


        locationManager.requestLocationUpdates(provider,1000,1,locationListener);

        //初始化数据
        points= DataHelper.getLongLati();

        return view;

    }



    private void navigateTo(Location location) {

        Log.e("sitefragment",location.toString());
        //如果是第一次创建，就获取位置信息并且将地图移到当前位置
        //为防止地图被反复移动，所以就只在第一次创建时执行
        if (isFirstLocate) {
            //LatLng对象主要用来存放经纬度
            //zoomTo是用来设置百度地图的缩放级别，范围为3~19，数值越大越精确
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(16f);
            baiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }


        MyLocationData.Builder locationBuilder=new MyLocationData.Builder();

        locationBuilder.latitude(location.getLatitude());
        locationBuilder.longitude(location.getLongitude());

        MyLocationData locationData=locationBuilder.build();
        baiduMap.setMyLocationData(locationData);


    }


    private void initListener() {

        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                Toast.makeText(getActivity(),latLng.toString(),Toast.LENGTH_SHORT).show();
                //根据坐标点判断相册,然后将相册的地址
                Intent intent=new Intent(getActivity(),ShowPhotoBySite.class);

                if((Math.abs(latLng.latitude-Double.parseDouble(points.get(1)))<=10)
                        &&(Math.abs(latLng.longitude-Double.parseDouble(points.get(0)))<10))
                      {
                    //可以传递数据，传递我们当前点的经纬度
                    intent.putExtra("lng",points.get(0));
                    intent.putExtra("lat",points.get(1));
                } else if((Math.abs(latLng.latitude-Double.parseDouble(points.get(3)))<=10)
                        &&(Math.abs(latLng.longitude-Double.parseDouble(points.get(2)))<10)){
                    //可以传递数据，传递我们当前点的经纬度
                    intent.putExtra("lng",points.get(2));
                    intent.putExtra("lat",points.get(3));
                }
                startActivity(intent);
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });

    }

    private void LocationPoint() {

        //定义Maker坐标点 第一个点
        LatLng point1 = new LatLng(Double.parseDouble(points.get(0)), Double.parseDouble(points.get(1)));
        //构建Marker图标
        BitmapDescriptor bitmap1 = BitmapDescriptorFactory
                .fromResource(R.drawable.loading);


        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option1 = new MarkerOptions()
                .position(point1)
                .icon(bitmap1);
        //在地图上添加Marker，并显示
        baiduMap.addOverlay(option1);

        Log.e("siteFragment","LocationPoint");


        //定义Maker坐标点 第一个点
        LatLng point2 = new LatLng(Double.parseDouble(points.get(2)), Double.parseDouble(points.get(3)));
        //构建Marker图标
        BitmapDescriptor bitmap2 = BitmapDescriptorFactory
                .fromResource(R.drawable.loading);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option2 = new MarkerOptions()
                .position(point2)
                .icon(bitmap2);
        //在地图上添加Marker，并显示
        baiduMap.addOverlay(option2);


    }

    LocationListener locationListener =new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            Log.e("locationListener","onLocationChanded");


            //更新设备位置
            if(locationManager!=null)
                navigateTo(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
            Log.e("locationListener","onStateChanded");
        }

        @Override
        public void onProviderEnabled(String s) {
            Log.e("locationListener","onProviderChanded");
        }

        @Override
        public void onProviderDisabled(String s) {
            Log.e("locationListener","onproviderDisabled");
        }
    };

    @Override
    public void onResume() {

        super.onResume();
        locationManager.requestLocationUpdates(provider,1000,1,locationListener);

    }

    @Override
    public void onPause() {

        super.onPause();
        locationManager.removeUpdates(locationListener);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        baiduMap.setMyLocationEnabled(false);
        mapView.onDestroy();
        if(locationManager!=null){
            locationManager.removeUpdates(locationListener);
        }

    }
}
