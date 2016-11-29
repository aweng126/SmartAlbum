package com.example.kingwen.smartalbum.Utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.example.kingwen.smartalbum.Beans.Photo;
import com.example.kingwen.smartalbum.Constants.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kingwen on 2016/11/28.
 */
public class DataHelper {

    private static DataHelper mDataHelper;

    public static ArrayList<Photo> mPhotos=new ArrayList<>();



    public static ArrayList<Photo> getPhotos(Context context){

        initData(context);

        return  mPhotos;
    }

    public DataHelper() {

    }

    private static void initData(Context context) {

        mPhotos=new ArrayList<>();
        Cursor cursor=context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);

        Log.e("myapplication","create");

        while (cursor.moveToNext()){

            String id=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media._ID));

            String data=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

            //纬度
            String latitude=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.LATITUDE));
            //经度
            String longitude=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.LONGITUDE));

            if(data.contains("Camera")){

                int offset=data.indexOf("IMG_");
                String time;

                if(offset==-1){

                    offset=data.indexOf("IMG");
                    time =data.substring(offset+3,offset+11);
                }else{
                    time =data.substring(data.indexOf("IMG_")+4,data.indexOf("IMG_")+12);
                }

                /**
                 * 设置页面显示格式
                 */
                String string1=time.substring(0, 4);
                String string2=time.substring(4, 6);
                String string3=time.substring(6,8);
                time=string1+"-"+string2+"-"+string3;

                /**
                 * bean保存
                 */
                Photo mPhoto=new Photo();
                mPhoto.setId(id);
                mPhoto.setData(data);
                mPhoto.setLatitude(latitude);
                mPhoto.setLongitude(longitude);
                mPhoto.setTime(time);
                mPhotos.add(mPhoto);


                Collections.reverse(mPhotos);
                // Log.e("time", "data " + data + " latitude " + latitude + " longitude " + longitude + " id " + id + " date " + time);
            }
        }


    }






/*    //通过时间来进行分类
    private static Map<String,ArrayList<Photo>> mTimePhotoList=new HashMap<>() ;*/

    //针对不同的参数进行获取
    public static ArrayList<Photo> getData(int dateFormat){

        switch (dateFormat){
            case Constants.FORMAT_TIME:


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
