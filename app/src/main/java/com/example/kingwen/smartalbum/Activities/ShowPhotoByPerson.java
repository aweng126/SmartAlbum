package com.example.kingwen.smartalbum.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.kingwen.smartalbum.Adapters.OrderTimeAdapter;
import com.example.kingwen.smartalbum.Beans.Photo;
import com.example.kingwen.smartalbum.R;
import com.example.kingwen.smartalbum.Utils.DataHelper;

import java.util.ArrayList;

/**
 * Created by kingwen on 2016/11/30.
 */
public class ShowPhotoByPerson extends Activity {
    /**
     * 对应图片的地址
     */
    private ArrayList<Photo> mDatas;

    private RecyclerView rv_show;

    /**
     *
     * recycleview 布局管理器
     */
    private  RecyclerView.LayoutManager  mLayoutManager;

    /**
     *
     * Adapter设置
     */

    private RecyclerView.Adapter adapter;


    private DataHelper  mDataHelper;

    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showphoto_layout);

        context=ShowPhotoByPerson.this;
        mDataHelper=DataHelper.getDataHelperInstance(context);


        rv_show= (RecyclerView)findViewById(R.id.rv_showphoto);


        Intent intent=getIntent();
        String person=intent.getStringExtra("person");
        mDatas= mDataHelper.getPointByPerson(person);

        /**
         * 瀑布流效果
         */
        /*RecyclerView.LayoutManager manager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);*/

        /**
         * 线性布局效果
         */
        //rv.setLayoutManager(new LinearLayoutManager(this));

        /**
         * 表格布局效果
         */
        rv_show.setLayoutManager(new GridLayoutManager(this, 2));

        adapter=new OrderTimeAdapter(ShowPhotoByPerson.this,mDatas);
        rv_show.setAdapter(adapter);



    }

}
