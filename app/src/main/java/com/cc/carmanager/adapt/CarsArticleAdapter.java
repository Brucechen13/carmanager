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
import com.cc.carmanager.adapt.holder.CommonViewHolder;
import com.cc.carmanager.bean.ArticleItemBean;
import com.cc.carmanager.vollley.MySingleton;

import java.util.ArrayList;

/**
 * Created by chenc on 2017/11/4.
 */
public class CarsArticleAdapter extends RecyclerView.Adapter<CommonViewHolder> {

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
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommonViewHolder newsVH = CommonViewHolder.getViewHolder(parent, R.layout.item_index_other);
        return newsVH;
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        holder.setText(R.id.list_item_news_title,listItem.get(position).getTitle());
        holder.setNetworkImageView(R.id.iv_left_image,listItem.get(position).getPicUrl(), defaultImage);
        holder.setItemClick(new TextViewHolderListener(position));
    }

    @Override
    public int getItemCount() {
        return listItem == null ? 0 : listItem.size();
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
