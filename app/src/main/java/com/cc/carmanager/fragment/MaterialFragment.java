package com.cc.carmanager.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cc.carmanager.R;
import com.cc.carmanager.activity.IndexArticleActivity;
import com.cc.carmanager.adapt.CarsArticleAdapter;
import com.cc.carmanager.bean.ArticleItemBean;
import com.cc.carmanager.view.ImageTextView;
import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;

/**
 * Created by zhouwei on 17/4/23.
 */

public class MaterialFragment extends LazyFragment{
    private ArrayList<ArticleItemBean> mOneNewsItemList = new ArrayList<>();
    private CarsArticleAdapter normalRecyclerViewAdapter;

    private RecyclerView mRecyclerView;
    private Handler handler = new Handler();
    private boolean isLoading;
    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);

        setContentView(R.layout.material_fragment_layout);
        mRecyclerView = (RecyclerView)findViewById(R.id.rv_recycler_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);//这里用线性显示 类似于listview
        normalRecyclerViewAdapter = new CarsArticleAdapter(getActivity(), mOneNewsItemList, mRecyclerView);
        mRecyclerView.setAdapter(normalRecyclerViewAdapter);

        initButtons();
    }

    private void initButtons(){
        ImageTextView mKnowledge = (ImageTextView) findViewById(R.id.menu_knowledge_id);
        ImageTextView mManual = (ImageTextView) findViewById(R.id.menu_manual_id);
        ImageTextView mArticle = (ImageTextView) findViewById(R.id.menu_article_id);
        initButton(mKnowledge, "知识大全",R.mipmap.icon_know, IndexArticleActivity.class);
        initButton(mManual, "手册查询",R.mipmap.icon_book, IndexArticleActivity.class);
        initButton(mArticle, "技术文章",R.mipmap.icon_techarticle, IndexArticleActivity.class);
    }
    private void initButton(ImageTextView view, final String title, int imageUrl, final Class jump_class) {
        view.setText(title);
        view.setImg(imageUrl);
        view.setClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), jump_class);
                Bundle bundle = new Bundle();
                bundle.putString("title", title);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResumeLazy() {
        super.onResumeLazy();
        getIndexNews();
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
    }


    private void getIndexNews() {
        mOneNewsItemList.clear();
        for(int i = 0; i < 10;i ++) {
            ArticleItemBean bean = new ArticleItemBean();
            bean.setIsCost(false);
            bean.setFileSize(100);
            bean.setPicUrl("http://assrt.net/images/logo_sub.jpg");
            bean.setTitle("你好啊啊的期望多群无多");
            bean.setUrl("http://assrt.net/images/logo_sub.jpg");
            mOneNewsItemList.add(bean);
        }
        normalRecyclerViewAdapter.notifyDataSetChanged();
//        MySingleton.getInstance(getActivity().getApplicationContext()).getRequestQueue().add(
//                RequestSingletonFactory.getInstance().getGETStringRequest(getActivity(), URLs.getUrl(tabName), new Response.Listener() {
//                    @Override
//                    public void onResponse(Object response) {
//                        JSONObject obj;
//                        try {
//                            mOneNewsItemList.clear();
//                            obj = new JSONObject(response.toString());
//                            JSONArray itemArray = obj.getJSONArray(URLs.getUrlTag(tabName));
//                            ArrayList<OneNewsItemBean> newsList = new Gson().fromJson(itemArray.toString(), Global.NewsItemType);
//                            mOneNewsItemList.addAll(newsList);
//                            normalRecyclerViewAdapter.notifyDataSetChanged();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }));
    }

    //初始化加载更多数据
    private void getMoreData() {
        for (int i = 0; i < 10; i++) {
            ArticleItemBean bean = new ArticleItemBean();
            bean.setIsCost(false);
            bean.setPicUrl("http://assrt.net/images/logo_sub.jpg");
            bean.setTitle("你好啊啊的期望多群无多");
            bean.setUrl("http://assrt.net/images/logo_sub.jpg");
            mOneNewsItemList.add(bean);
        }
        normalRecyclerViewAdapter.notifyDataSetChanged();
        normalRecyclerViewAdapter.notifyItemRemoved(normalRecyclerViewAdapter.getItemCount());
    }

    private void getRefreshData() {
        for (int i = 0; i < 2; i++) {
            ArticleItemBean bean = new ArticleItemBean();
            bean.setIsCost(false);
            bean.setPicUrl("http://assrt.net/images/logo_sub.jpg");
            bean.setTitle("你好啊啊的期望多群无多");
            bean.setUrl("http://assrt.net/images/logo_sub.jpg");
            mOneNewsItemList.add(0, bean);
        }
        normalRecyclerViewAdapter.notifyDataSetChanged();
        normalRecyclerViewAdapter.notifyItemRemoved(normalRecyclerViewAdapter.getItemCount());
    }
}