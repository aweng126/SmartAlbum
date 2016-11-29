package com.example.kingwen.smartalbum.Utils;

import android.app.Application;
import android.util.Log;

import com.example.kingwen.smartalbum.Beans.Photo;
import com.example.kingwen.smartalbum.Constants.Constants;
import com.example.kingwen.smartalbum.MyApplication.MyApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kingwen on 2016/11/28.
 */
public class DateHelper  {
    private Application mApplication ;

    //得到所有的表
    private static  ArrayList<Photo> mPhotos=MyApplication.getMyAllPhotos();

    //通过时间来进行分类
    private static Map<String,ArrayList<Photo>> mTimePhotoList=new HashMap<>() ;

    //针对不同的参数进行获取
    public static ArrayList<Photo> getData(int dateFormat){

        switch (dateFormat){
            case Constants.FORMAT_TIME:
                //时间

                while(mPhotos.iterator().hasNext()){

                }
               // mTimePhotoList.put("",)

                Log.e("datehelper",mPhotos.toString());
                return mPhotos;


            case Constants.FORMAT_SITE:
                //地点


                return mPhotos;

            case Constants.FORMAT_PERSON:
                //人物

                return mPhotos;
            case Constants.FORMAT_SHARE:

                //分享
                return mPhotos;

            default:
                return null;

        }
    }
}
