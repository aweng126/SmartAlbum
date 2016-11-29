package com.example.kingwen.smartalbum.MyApplication;

import android.app.Application;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

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
        cursor=getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);

        int counter=0;

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
                String date;

                if(offset==-1){

                    offset=data.indexOf("IMG");
                    date =data.substring(offset+3,offset+11);
                }else{
                    date =data.substring(data.indexOf("IMG_")+4,data.indexOf("IMG_")+12);
                }

                Photo mPhoto=new Photo();
                mPhoto.setId(id);
                mPhoto.setData(data);
                mPhoto.setLatitude(latitude);
                mPhoto.setLongitude(longitude);
                mPhoto.setTime(date);
                myPhotoes.add(mPhoto);

                Log.e("time", "data " + data + " latitude " + latitude + " longitude " + longitude + " id " + id + " date " + date);

            }
         }
        Log.e("myapplication",myPhotoes.size()+"");
    }

    /**
     * 得到所有的照片
     * @return
     */
    public static ArrayList<Photo> getMyAllPhotos(){
        return myPhotoes;
    }


}
