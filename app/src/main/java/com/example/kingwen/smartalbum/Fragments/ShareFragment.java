package com.example.kingwen.smartalbum.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kingwen.smartalbum.R;

/**
 * Created by kingwen on 2016/11/27.
 *
 *   分享界面
 */
public class ShareFragment extends Fragment{

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_share,container,false);
        //定义view




        return view;
    }

}
