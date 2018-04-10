package com.cc.carmanager.adapt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cc.carmanager.R;
import com.cc.carmanager.activity.CarSeriesConfigAcitivity;
import com.cc.carmanager.activity.CarSeriesPicActivity;
import com.cc.carmanager.activity.CarsDetailActivity;
import com.cc.carmanager.bean.CarPicBean;
import com.cc.carmanager.bean.CarPicSeriesBean;
import com.cc.carmanager.bean.CarSeriesBean;
import com.cc.carmanager.bean.CarsItemBean;
import com.cc.carmanager.fragment.cars.CarsIntroFragment;
import com.cc.carmanager.net.VolleyInstance;
import com.cc.carmanager.net.VolleyResult;
import com.cc.carmanager.util.NetUrlsSet;
import com.cc.carmanager.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.preview.ImagePreviewActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by chenc on 2017/12/30.
 */

public class CarsIntroListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private RecyclerView recyclerView;
    private ArrayList<CarSeriesBean.CarSerieBean> listItem;

    public CarsIntroListAdapter(Context context, ArrayList<CarSeriesBean.CarSerieBean> listItem, RecyclerView recyclerView) {
        mContext = context;
        this.recyclerView = recyclerView;
        this.listItem = listItem;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View hold = mLayoutInflater.inflate(R.layout.item_intro_car, parent, false);
        return new CarViewHolder(hold);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((CarViewHolder) holder).carName.setText(listItem.get(position).getSeriesName());
        ((CarViewHolder) holder).carPrice.setText(String.format(Locale.CHINA, "厂商指导价%.1f万", listItem.get(position).getPrice()));
        ((CarViewHolder) holder).carPic.setOnClickListener(new TextViewHolderListener(position));
        ((CarViewHolder) holder).carConfig.setOnClickListener(new TextViewHolderListener(position));
    }

    @Override
    public int getItemCount() {
        return listItem == null ? 0 : listItem.size();
    }

    public static class CarViewHolder extends RecyclerView.ViewHolder {
        TextView carName;
        TextView carPrice;
        TextView carPic;
        TextView carConfig;
        TextView tvLine;

        View v;

        CarViewHolder(View view) {
            super(view);
            v = view;
            carName = (TextView) view.findViewById(R.id.car_name);
            carPrice = (TextView) view.findViewById(R.id.car_price);
            carPic = (TextView) view.findViewById(R.id.car_pic);
            carConfig = (TextView) view.findViewById(R.id.car_config);
            tvLine = (TextView) view.findViewById(R.id.contact_line);
        }
    }

    class TextViewHolderListener implements View.OnClickListener {
        int position;
        TextViewHolderListener(int i) {
            position = i;
        }
        @Override
        public void onClick(View v) {
            Intent intent = null;
            Bundle bundle = null;
            switch (v.getId()){
                case R.id.car_config:
                    intent = new Intent(mContext, CarSeriesConfigAcitivity.class);
                    bundle = new Bundle();
                    bundle.putInt("carId", listItem.get(position).getCarId());
                    bundle.putInt("seriesId", listItem.get(position).getId());
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                    break;
                case R.id.car_pic:
                    intent = new Intent(mContext, CarSeriesPicActivity.class);
                    bundle = new Bundle();
                    bundle.putInt("carId", listItem.get(position).getCarId());
                    bundle.putInt("seriesId", listItem.get(position).getId());
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                    break;
            }
        }
    }
}
