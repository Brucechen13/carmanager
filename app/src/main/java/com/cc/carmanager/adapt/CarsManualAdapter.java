package com.cc.carmanager.adapt;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.cc.carmanager.R;
import com.cc.carmanager.activity.CarsDetailListActivity;
import com.cc.carmanager.adapt.holder.CommonViewHolder;
import com.cc.carmanager.bean.BrandsItemBean;
import com.cc.carmanager.bean.CarsBrandBean;
import com.cc.carmanager.vollley.MySingleton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenc on 2017/10/26.
 */
public class CarsManualAdapter extends CarsNewsBaseAdapter{

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private RecyclerView recyclerView;
    private int type = 0;//0 维修手册 1 基础知识

    public CarsManualAdapter(Context context, RecyclerView recyclerView, int type) {
        mContext = context;
        this.recyclerView = recyclerView;
        mLayoutInflater = LayoutInflater.from(context);
        this.type = type;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommonViewHolder itemVH = CommonViewHolder.getViewHolder(parent, R.layout.item_manual);
        return itemVH;
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        holder.setText(R.id.contact_title, datas.get(position).getTitle());
        if(type==0){
            holder.setImg(R.id.iv_left_image, R.mipmap.icon_manual1);
        }else{
            holder.setImg(R.id.iv_left_image, R.mipmap.icon_manual0);
        }
        holder.setItemClick(new TextViewHolderListener(position));
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }


    class TextViewHolderListener implements View.OnClickListener {
        int position;
        TextViewHolderListener(int i) {
            position = i;
        }
        @Override
        public void onClick(View v) {
            Intent i = new Intent(mContext, CarsDetailListActivity.class);
            mContext.startActivity(i);
        }
    }
}
