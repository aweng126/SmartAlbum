package com.example.kingwen.smartalbum.Fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.kingwen.smartalbum.Activities.ShowPhotoByPerson;
import com.example.kingwen.smartalbum.R;

/**
 * Created by kingwen on 2016/11/27.
 * 个人显示界面
 */
public class PersonFragment extends Fragment{

    private Context mContext;
    private Activity mActivity;
    private ImageView qingwen;
    private ImageView shuai;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext=getActivity();
        mActivity=getActivity();


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_person,container,false);
        //定义view
        qingwen= (ImageView) view.findViewById(R.id.qingwen);
        shuai= (ImageView) view.findViewById(R.id.shuai);

        Glide.with(getActivity())
                .load(R.drawable.qingwen)
                .override(150,150)
                .into(qingwen);

        Glide.with(getActivity())
                .load(R.drawable.shuai)
                .override(150,150)
                .into(shuai);

        qingwen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(getActivity(), ShowPhotoByPerson.class);
                intent.putExtra("person","qingwen");
                startActivity(intent);

            }
        });

        shuai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(getActivity(), ShowPhotoByPerson.class);
                intent.putExtra("person","shuai");
                startActivity(intent);
            }
        });

        return view;
    }
}
