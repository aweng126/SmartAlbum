package com.example.kingwen.smartalbum.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kingwen.smartalbum.Beans.Photo;
import com.example.kingwen.smartalbum.R;

import java.util.List;

/**
 * Created by kingwen on 2016/11/29.
 */
public class OrderTimeAdapter extends RecyclerView.Adapter<MyViewHloder> implements View.OnClickListener{

    private LayoutInflater mInflater;

    private Context mContext;

    private List<Photo> mDatas;

    private OnRecyclerViewItemClickListener mOnItemClickListener;


    /*
     * 定义我们的接口，用于实现我们的单击和长按监听事件
     */
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , int data);
     //   void onItemLongClick(View view , int data);
    }

    /*
      * 构造方法
      * */
    public OrderTimeAdapter(Context context,List<Photo> datas){
        this.mContext=context;
        this.mDatas=datas;
        Log.d("recycleview构造方法", datas.size() + "");
        mInflater=LayoutInflater.from(context);
        mOnItemClickListener=null;
    }


    public void setOnItemClickListener(OnRecyclerViewItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


    @Override
    public MyViewHloder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=mInflater.inflate(R.layout.pic_item_layout,parent,false);

        MyViewHloder myViewHloder=new MyViewHloder(view);

        view.setOnClickListener(this);

        return myViewHloder;
    }

    @Override
    public void onBindViewHolder(MyViewHloder holder, int position) {

        holder.itemView.setTag(position);

        Glide.with(mContext)
                .load(mDatas.get(position).getData())
                   .skipMemoryCache(true)
                     .diskCacheStrategy(DiskCacheStrategy.NONE)
                           .crossFade()
                             //设置图片大小
                             .override(150, 150)
                             .into(holder.iv);

        holder.tv.setText(mDatas.get(position).getTime());
    }

    @Override
    public int getItemCount() {

        return mDatas.size();
    }

    /**
     * 为每个视图添加监听事件,并产生回调
     * @param v
     */
    @Override
    public void onClick(View v) {

        if(mOnItemClickListener!=null){
            mOnItemClickListener.onItemClick(v, (Integer) v.getTag());
        }

    }
}

class MyViewHloder extends RecyclerView.ViewHolder {

    ImageView iv;

    TextView tv;

    public MyViewHloder(View itemView) {
        super(itemView);
        iv= (ImageView) itemView.findViewById(R.id.iv_pic_item);
        tv= (TextView) itemView.findViewById(R.id.tv_pic_item);
    }
}