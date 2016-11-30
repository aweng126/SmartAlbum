package com.example.kingwen.smartalbum.Utils;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;
import com.example.kingwen.smartalbum.Beans.Photo;
import com.example.kingwen.smartalbum.Constants.Constants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;


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


    /**
     * 得到两个拍照最多的点的经纬度
     * @return
     */
    public static ArrayList<String> getLongLati(){
        ArrayList<String> longs=new ArrayList<>();
        ArrayList<String> latis=new ArrayList<>();
        ArrayList<Integer> sizes=new ArrayList<>();

        Iterator iterator=mPhotos.iterator();
        while(iterator.hasNext()){
            Photo photo= (com.example.kingwen.smartalbum.Beans.Photo) iterator.next();

            if(photo.getLatitude()==null||photo.getLongitude()==null){
                continue;
            }

            if(longs.contains(photo.getLongitude())&&latis.contains(photo.getLatitude())){
                sizes.set(longs.indexOf(photo.getLongitude()),longs.indexOf(photo.getLongitude())+1);
            }else {


                longs.add(photo.getLongitude());
                latis.add(photo.getLatitude());
                sizes.add(1);
            }
        }

        /**
         * 得到图片最多的最大的两个点
         * 后期可以添加所有点
         */
        int max=0,mmax=0;

        for(int i=0;i<sizes.size();i++){
            if(sizes.get(i)>max&&sizes.get(i)>mmax){
                mmax=max;
                max=sizes.get(i);
            }else if(sizes.get(i)<max&&sizes.get(i)>mmax){
                mmax=sizes.get(i);
            }
        }

        /**
         * 得到返回产生照片最多的两个地点的经纬度
         */
        String maxlong = longs.get(sizes.indexOf(max));
        String  maxLati=latis.get(sizes.indexOf(max));

        String mmaxlong = longs.get(sizes.indexOf(mmax));
        String  mmaxLati=latis.get(sizes.indexOf(mmax));

        ArrayList<String > result=new ArrayList<>();
        result.add(maxlong);
        result.add(maxLati);
        result.add(mmaxlong);
        result.add(mmaxLati);

        return result;
    }


    public static ArrayList<Photo> getPointBySite(String lng,String lat){

        ArrayList<Photo> pointBySite=new ArrayList<>();
        Photo mPhoto;

        for (int i=0;i<mPhotos.size();i++){
            mPhoto=mPhotos.get(i);
        if(mPhoto.getLongitude()==null||mPhoto.getLatitude()==null){
            continue;
        }
            if(Math.abs(Integer.parseInt(mPhoto.getLatitude())-Integer.parseInt(lat))<20
                    && Math.abs(Integer.parseInt(mPhoto.getLongitude())-Integer.parseInt(lng))<20  ){
               // pointBySite.add(mPhoto.getData());
                pointBySite.add(mPhoto);
            }
        }

        return  pointBySite;

    }

    public static ArrayList<Photo> getPointByPerson(String person) {


        ArrayList<Photo> result=new ArrayList<>();

        int []qingwen={4,5,6,7,8,9,35,44,89,90};
        int []shuai={88};
        switch (person){
            case "qingwen":
                /*for(int i=0;i<mPhotos.size();i++){
                    Photo photo=mPhotos.get(i);
                    Log.e("qingwen",i+" "+photo.getData());
                }*/

                for (int i=0;i<qingwen.length;i++){
                    result.add(mPhotos.get(qingwen[i]));
                }

                return  result;


            case "shuai":
                for (int i=0;i<shuai.length;i++){
                    result.add(mPhotos.get(shuai[i]));
                }
                return  result;

            default:
                return null;
        }
    }
}
