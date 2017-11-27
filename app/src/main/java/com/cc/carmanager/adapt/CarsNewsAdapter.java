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
import com.cc.carmanager.bean.CarsNewsBean;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenc on 2017/10/23.
 */
public class CarsNewsAdapter extends RecyclerView.Adapter<CommonViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_BANNER = 1;

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private RecyclerView recyclerView;
    private CarsNewsBean datas;

    int defaultImage = R.drawable.load_fail;
    int failImage = R.drawable.load_fail;
    private int[] defaultImages = new int[]{defaultImage};

    private static String TAG = "CarsNews";

    public CarsNewsAdapter(Context context, RecyclerView recyclerView) {
        mContext = context;
        this.recyclerView = recyclerView;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setDatas(CarsNewsBean datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_BANNER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            CommonViewHolder newsVH = CommonViewHolder.getViewHolder(parent, R.layout.item_news);
            return newsVH;
        } else if (viewType == TYPE_BANNER) {
            CommonViewHolder headerVH = CommonViewHolder.getViewHolder(parent, R.layout.item_banner);
            return headerVH;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case TYPE_BANNER:
                Log.i(TAG, "onBindViewHolder BannerViewHolder");

                List<String> imgUrls = new ArrayList<>();
                for (int i = 0; i < datas.getFocusimg().size(); i++) {
                    imgUrls.add(datas.getFocusimg().get(i).getImgurl());
                }
                holder.setBanner(R.id.item_recyclerview_header_banner, BannerConfig.RIGHT, 3000, BannerConfig.CIRCLE_INDICATOR, imgUrls);
                break;
            case TYPE_ITEM:
                holder.setText(R.id.list_item_news_title,datas.getNewslist().get(position).getTitle());
                holder.setText(R.id.list_item_news_subtitle,datas.getNewslist().get(position).getTime());
                holder.setNetworkImageView(R.id.iv_left_image,datas.getNewslist().get(position).getSmallpic(), defaultImage);
                holder.setItemClick(new TextViewHolderListener(position));
                break;
            default:
                break;
        }
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
