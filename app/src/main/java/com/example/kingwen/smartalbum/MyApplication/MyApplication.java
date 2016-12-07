package com.example.kingwen.smartalbum.MyApplication;

import android.app.Application;


import com.example.kingwen.smartalbum.Beans.Photo;

import java.util.ArrayList;

/**
 * Created by kingwen on 2016/11/27.
 */
public class MyApplication extends Application {

    private static ArrayList<Photo> myPhotoes=new ArrayList<>();



    @Override
    public void onCreate() {
        super.onCreate();


    }




    /**
     * 得到所有的照片
     * @return
     */
    public static ArrayList<Photo> getMyAllPhotos(){
        return myPhotoes;
    }


}
