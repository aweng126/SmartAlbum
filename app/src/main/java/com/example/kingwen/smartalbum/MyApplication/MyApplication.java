package com.example.kingwen.smartalbum.MyApplication;

import android.app.Application;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.example.kingwen.smartalbum.Beans.Photo;

import java.util.ArrayList;

/**
 * Created by kingwen on 2016/11/27.
 */
public class MyApplication extends Application {

    private static ArrayList<Photo> myPhotoes=new ArrayList<>();
    private Cursor cursor;

    @Override
    public void onCreate() {
        super.onCreate();

        //百度地图初始化
        SDKInitializer.initialize(getApplicationContext());

    }

    /**
     * 得到所有的照片
     * @return
     */
    public static ArrayList<Photo> getMyAllPhotos(){
        return myPhotoes;
    }


}
