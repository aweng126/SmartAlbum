package com.example.kingwen.smartalbum.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.kingwen.smartalbum.Adapters.OrderTimeAdapter;
import com.example.kingwen.smartalbum.Beans.Photo;
import com.example.kingwen.smartalbum.R;
import com.example.kingwen.smartalbum.Utils.DataHelper;
import java.util.ArrayList;

/**
 * Created by kingwen on 2016/11/27.
 */
public class TimeFragment extends Fragment{

    /**
     * 用于展示的listview
     */
    private ArrayList<Photo> mPhotos;

    /**
     * 用于显示我们图片的view
     */
    private RecyclerView rv_photo_time;

    /**
     * recycleview 布局管理器
     */
   private  RecyclerView.LayoutManager  mLayoutManager;

    /**
     * Adapter设置
     */
    private RecyclerView.Adapter mAdapter;

    /**
     * 数据助手
     */
    private DataHelper mDataHelper=null;

    /**
     * 上下文对象
     */
    private Context context;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context=getActivity();

        mDataHelper=DataHelper.getDataHelperInstance(context);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_time,container,false);

        //定义view
        rv_photo_time= (RecyclerView) view.findViewById(R.id.rv_time);
        mPhotos= mDataHelper.getPhotos();

        //设置固定大小
        rv_photo_time.setHasFixedSize(true);

        /*
         瀑布流对象
        mLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        rv_photo_time.setLayoutManager(mLayoutManager);
        */

        /**
         * 给Recycleview显示表格布局
         */
        rv_photo_time.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mAdapter=new OrderTimeAdapter(getActivity(),mPhotos);
        rv_photo_time.setAdapter(mAdapter);

        return view;
    }


}
