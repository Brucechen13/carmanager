package com.cc.carmanager.adapt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.cc.carmanager.R;
import com.cc.carmanager.activity.CarsDetailActivity;
import com.cc.carmanager.activity.CarsUserDetailActivity;
import com.cc.carmanager.bean.BrandListBean;
import com.cc.carmanager.bean.CarsItemBean;
import com.cc.carmanager.vollley.MySingleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 品牌某一类车所有车型适配器
 * Created by chenc on 2017/11/19.
 */
public class CarsBrandListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private RecyclerView recyclerView;
    private List<BrandListBean.BrandCarBean> listItem;
    int defaultImage = R.drawable.load_fail;
    int failImage = R.drawable.load_fail;
    private int[] defaultImages = new int[]{defaultImage};

    public CarsBrandListAdapter(Context context, List<BrandListBean.BrandCarBean> listItem, RecyclerView recyclerView) {
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
            //((CarViewHolder) holder).carName.setText(listItem.get(position).getCarName());
            ((CarViewHolder) holder).brandName.setText(listItem.get(position).getCarName());
            if(listItem.get(position).getMaxPrice() != 0){
                ((CarViewHolder) holder).carPrice.setText(String.format(Locale.CHINA,"厂商指导价：%.1f万~%.1f万", listItem.get(position).getMinPrice(), listItem.get(position).getMaxPrice()));
            }

            if(position < 2 || !(listItem.get(position - 1).getCarName().equals(listItem.get(position).getCarName()))){
                ((CarViewHolder) holder).tvLine.setVisibility(View.VISIBLE);
            }else{
                ((CarViewHolder) holder).tvLine.setVisibility(View.GONE);
            }
            NetworkImageView tempImage = ((CarViewHolder) holder).mImageView;
            setNetworkImageView(tempImage, listItem.get(position).getIconSrc());
            ((CarViewHolder) holder).v.setOnClickListener(new TextViewHolderListener(position));
        }
    }

    @Override
    public int getItemCount() {
        return listItem == null ? 0 : listItem.size();
    }

    public static class CarViewHolder extends RecyclerView.ViewHolder {
        NetworkImageView mImageView;
        TextView brandName;
        TextView carName;
        TextView carPrice;
        TextView carNums;
        TextView tvLine;

        View v;

        CarViewHolder(View view) {
            super(view);
            v = view;
            mImageView = (NetworkImageView) view.findViewById(R.id.iv_left_image);
            brandName = (TextView) view.findViewById(R.id.brand_name);
            carName = (TextView) view.findViewById(R.id.car_name);
            carPrice = (TextView) view.findViewById(R.id.car_price);
            carNums = (TextView) view.findViewById(R.id.car_num);
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
            Intent i = new Intent(mContext, CarsDetailActivity.class);
            i.putExtra("carId", listItem.get(position).getId());
            mContext.startActivity(i);
        }
    }
}