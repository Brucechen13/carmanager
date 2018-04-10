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
public class CarsNewsBigAdapter extends CarsNewsBaseAdapter {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_BANNER = 1;

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private RecyclerView recyclerView;

    int defaultImage = R.drawable.load_fail;
    int failImage = R.drawable.load_fail;
    private int[] defaultImages = new int[]{defaultImage};

    private static String TAG = "CarsNews";

    public CarsNewsBigAdapter(Context context, RecyclerView recyclerView) {
        mContext = context;
        this.recyclerView = recyclerView;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (viewType == TYPE_ITEM) {
//        } else if (viewType == TYPE_BANNER) {
//            CommonViewHolder headerVH = CommonViewHolder.getViewHolder(parent, R.layout.item_banner);
//            return headerVH;
//        }
//        return null;
        CommonViewHolder newsVH = CommonViewHolder.getViewHolder(parent, R.layout.item_index_news);
        return newsVH;
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        switch (getItemViewType(position)){
//            case TYPE_BANNER:
//                Log.i(TAG, "onBindViewHolder BannerViewHolder");
//
//                List<String> imgUrls = new ArrayList<>();
//                for (int i = 0; i < datas.getFocusimg().size(); i++) {
//                    imgUrls.add(datas.getFocusimg().get(i).getImgurl());
//                }
//                holder.setBanner(R.id.item_recyclerview_header_banner, BannerConfig.RIGHT, 3000, BannerConfig.CIRCLE_INDICATOR, imgUrls);
//                break;
            case TYPE_ITEM:
                holder.setText(R.id.index_news_title,datas.get(position).getTitle());
                holder.setText(R.id.index_news_time,datas.get(position).getPublishTime().getDateTime());
                holder.setNetworkImageView(R.id.index_news_image,datas.get(position).getIconSrc(), defaultImage);
                holder.setItemClick(new TextViewHolderListener(position));
                holder.setImgVisble(R.id.is_hot, datas.get(position).getIsHot() == 1?View.VISIBLE:View.GONE);
                holder.setImgVisble(R.id.is_recom, datas.get(position).getIsRecommend() == 1?View.VISIBLE:View.GONE);
                holder.setImgVisble(R.id.is_reup, datas.get(position).getIsUp() == 1?View.VISIBLE:View.GONE);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
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
            Log.d("RVA", "TextViewHolderListener :" + datas.get(position));
            Intent i = new Intent(mContext, NewsDisplayActivity.class);
            i.putExtra("news_id", datas.get(position).getId());
            mContext.startActivity(i);
        }
    }
}
