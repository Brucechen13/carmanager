package com.cc.carmanager.adapt;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cc.carmanager.R;
import com.cc.carmanager.activity.CarsDetailActivity;
import com.cc.carmanager.bean.NewsCommentsBean;

import java.util.List;

/**
 * Created by chenc on 2018/1/2.
 */

public class CarsNewsCommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private RecyclerView recyclerView;
    private List<NewsCommentsBean> listItem;

    public CarsNewsCommentsAdapter(Context context, List<NewsCommentsBean> listItem, RecyclerView recyclerView) {
        mContext = context;
        this.recyclerView = recyclerView;
        this.listItem = listItem;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View hold = mLayoutInflater.inflate(R.layout.item_news_comment, parent, false);
        return new CarsListAdapter.CarViewHolder(hold);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return listItem == null ? 0 : listItem.size();
    }

    public static class CarViewHolder extends RecyclerView.ViewHolder {
        TextView carTitle;

        TextView tvLetter;
        TextView tvLine;

        View v;

        CarViewHolder(View view) {
            super(view);
            v = view;
            carTitle = (TextView) view.findViewById(R.id.car_name);
            tvLetter = (TextView) view.findViewById(R.id.car_type);
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
            Intent i = new Intent(mContext, CarsDetailActivity.class);
            mContext.startActivity(i);
        }
    }}
