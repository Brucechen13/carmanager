package com.cc.carmanager.adapt;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cc.carmanager.R;
import com.cc.carmanager.activity.NewsDisplayActivity;
import com.cc.carmanager.adapt.holder.CommonViewHolder;

/**
 * Created by chenc on 2017/10/23.
 */
public class CarsNewsRecommandAdapter extends CarsNewsBaseAdapter {

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private RecyclerView recyclerView;

    int defaultImage = R.drawable.load_fail;
    int failImage = R.drawable.load_fail;
    private int[] defaultImages = new int[]{defaultImage};

    public CarsNewsRecommandAdapter(Context context, RecyclerView recyclerView) {
        mContext = context;
        this.recyclerView = recyclerView;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommonViewHolder newsVH = CommonViewHolder.getViewHolder(parent, R.layout.item_index_recommand);
        return newsVH;
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        holder.setText(R.id.list_item_news_title,datas.getNewslist().get(position).getTitle());
        holder.setText(R.id.list_item_news_subtitle,datas.getNewslist().get(position).getTime() + "2017-10-3");
        holder.setNetworkImageView(R.id.iv_left_image,datas.getNewslist().get(position).getSmallpic(), defaultImage);
        holder.setItemClick(new TextViewHolderListener(position));
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.getNewslist().size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    class TextViewHolderListener implements View.OnClickListener {
        int position;

        TextViewHolderListener(int i) {
            position = i;
        }

        @Override
        public void onClick(View v) {
            Log.d("RVA", "TextViewHolderListener :" + datas.getNewslist().get(position) + "");
            Intent i = new Intent(mContext, NewsDisplayActivity.class);
//            i.putExtra("NEWS_LINK", jsonLink);
            mContext.startActivity(i);
        }
    }
}
