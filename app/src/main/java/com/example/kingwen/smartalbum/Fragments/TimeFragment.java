package com.example.kingwen.smartalbum.Fragments;



import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.kingwen.smartalbum.Adapters.OrderTimeAdapter;
import com.example.kingwen.smartalbum.Beans.Photo;
import com.example.kingwen.smartalbum.MyApplication.MyApplication;
import com.example.kingwen.smartalbum.R;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by kingwen on 2016/11/27.
 */
public class TimeFragment extends Fragment{

    private MyApplication myApplication;

    private ArrayList<Photo> mPhotos;

    /**
     * 用于显示我们图片的view
     */
    private RecyclerView rv_photo_time;

    /**
     *
     * recycleview 布局管理器
     */
   private  RecyclerView.LayoutManager  mLayoutManager;


    /**
     *
     * Adapter设置
     */

    private RecyclerView.Adapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApplication= (MyApplication) getActivity().getApplication();


        initData();
       // mPhotos= DateHelper.getData(Constants.FORMAT_TIME);

        Log.e("timefragment",mPhotos.toString());

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_time,container,false);
        //定义view

        rv_photo_time= (RecyclerView) view.findViewById(R.id.rv_time);

        /**
         * 设置适配器
         */

        //设置固定大小
        rv_photo_time.setHasFixedSize(true);
      /*  mLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        rv_photo_time.setLayoutManager(mLayoutManager);*/

        rv_photo_time.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mAdapter=new OrderTimeAdapter(getActivity(),mPhotos);
        rv_photo_time.setAdapter(mAdapter);

       // Log.e("timeFragent","create");

        return view;
    }


    public void initData(){


        mPhotos=new ArrayList<>();
        Cursor cursor=getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);

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

}
