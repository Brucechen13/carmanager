package com.cc.carmanager.adapt;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.cc.carmanager.R;
import com.cc.carmanager.activity.CarsUserDetailActivity;
import com.cc.carmanager.bean.CarsItemBean;
import com.cc.carmanager.vollley.MySingleton;

import java.util.ArrayList;

/**
 * 用车指南适配器
 * Created by chenc on 2017/11/19.
 */
public class CarsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private RecyclerView recyclerView;
    private ArrayList<CarsItemBean> listItem;
    int defaultImage = R.drawable.load_fail;
    int failImage = R.drawable.load_fail;
    private int[] defaultImages = new int[]{defaultImage};

    public CarsListAdapter(Context context, ArrayList<CarsItemBean> listItem, RecyclerView recyclerView) {
        mContext = context;
        this.recyclerView = recyclerView;
        this.listItem = listItem;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View hold = mLayoutInflater.inflate(R.layout.item_cars_detail, parent, false);
        return new CarViewHolder(hold);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CarViewHolder) {
            ((CarViewHolder) holder).carTitle.setText(listItem.get(position).getCarName());

            if(position < 2 || !(listItem.get(position - 1).getBrandName().equals(listItem.get(position).getBrandName()))){
                ((CarViewHolder) holder).tvLetter.setText(listItem.get(position).getBrandName());
                ((CarViewHolder) holder).tvLetter.setVisibility(View.VISIBLE);
                ((CarViewHolder) holder).tvLine.setVisibility(View.VISIBLE);
            }else{
                ((CarViewHolder) holder).tvLetter.setVisibility(View.GONE);
                ((CarViewHolder) holder).tvLine.setVisibility(View.GONE);
            }
            NetworkImageView tempImage = ((CarViewHolder) holder).mImageView;
            setNetworkImageView(tempImage, listItem.get(position).getImageUrl());
            ((CarViewHolder) holder).v.setOnClickListener(new TextViewHolderListener(position));
        }
    }

    @Override
    public int getItemCount() {
        return listItem == null ? 0 : listItem.size();
    }

    public static class CarViewHolder extends RecyclerView.ViewHolder {
        NetworkImageView mImageView;
        TextView carTitle;

        TextView tvLetter;
        TextView tvLine;

        View v;

        CarViewHolder(View view) {
            super(view);
            v = view;
            mImageView = (NetworkImageView) view.findViewById(R.id.iv_left_image);
            carTitle = (TextView) view.findViewById(R.id.car_name);
            tvLetter = (TextView) view.findViewById(R.id.car_type);
            tvLine = (TextView) view.findViewById(R.id.contact_line);
        }
    }

    private void setNetworkImageView(NetworkImageView networkImageView, String url) {
        networkImageView.setDefaultImageResId(defaultImage);
        networkImageView.setErrorImageResId(defaultImage);
        networkImageView.setImageUrl(url,
                MySingleton.getInstance().getImageLoader());
    }


    class TextViewHolderListener implements View.OnClickListener {
        int position;
        TextViewHolderListener(int i) {
            position = i;
        }
        @Override
        public void onClick(View v) {
            Intent i = new Intent(mContext, CarsUserDetailActivity.class);
            mContext.startActivity(i);
        }
    }
}