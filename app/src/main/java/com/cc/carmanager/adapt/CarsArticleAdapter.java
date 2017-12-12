package com.cc.carmanager.adapt;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.cc.carmanager.R;
import com.cc.carmanager.bean.ArticleItemBean;
import com.cc.carmanager.vollley.MySingleton;

import java.util.ArrayList;

/**
 * Created by chenc on 2017/11/4.
 */
public class CarsArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private RecyclerView recyclerView;
    private ArrayList<ArticleItemBean> listItem;
    int defaultImage = R.drawable.load_fail;
    int failImage = R.drawable.load_fail;
    private int[] defaultImages = new int[]{defaultImage};
    final String TAG = getClass().getSimpleName();

    public CarsArticleAdapter(Context context, ArrayList<ArticleItemBean> listItem, RecyclerView recyclerView) {
        mContext = context;
        this.recyclerView = recyclerView;
        this.listItem = listItem;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View hold = mLayoutInflater.inflate(R.layout.item_index_other, parent, false);
        return new TextViewHolder(hold);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TextViewHolder) {
            ((TextViewHolder) holder).mTitle.setText(listItem.get(position).getTitle());
            ((TextViewHolder) holder).mSubTitle.setText(""+listItem.get(position).getFileSize()+" KB");

            NetworkImageView tempImage = ((TextViewHolder) holder).mImageView;
            setNetworkImageView(tempImage, listItem.get(position).getPicUrl());
            Log.i(TAG, "onBindViewHolder TextViewHolder");
            ((TextViewHolder) holder).v.setOnClickListener(new TextViewHolderListener(position));
        }
    }

    @Override
    public int getItemCount() {
        return listItem == null ? 0 : listItem.size() + 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class TextViewHolder extends RecyclerView.ViewHolder {
        NetworkImageView mImageView;
        TextView mTitle;
        TextView mSubTitle;
        View v;

        TextViewHolder(View view) {
            super(view);
            v = view;
            mImageView = (NetworkImageView) view.findViewById(R.id.iv_left_image);
            mTitle = (TextView) view.findViewById(R.id.list_item_news_title);
            mSubTitle = (TextView) view.findViewById(R.id.list_item_news_subtitle);
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
            Log.d("RVA", "TextViewHolderListener :" + listItem.get(position) + "");
//            String jsonLink = NeteaseURLParse.webURLToMobileJSONLink2(listItem.get(position).getPostid());
//            if (TextUtils.isEmpty(jsonLink))
//                jsonLink = NeteaseURLParse.webURLToMobileJSONLink(listItem.get(position).getUrl());
//            Log.i("RVA", jsonLink);
//
//            Intent i = new Intent(mContext, NewsDisplayActivity.class);
////            i.putExtra("NEWS_LINK", jsonLink);
//            mContext.startActivity(i);

//            //直接打开系统浏览器，来查看点击的新闻
//            Uri uri = Uri.parse(JsonLink);
//            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//            mContext.startActivity(intent);
        }
    }
}
