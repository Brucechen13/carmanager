package com.cc.carmanager.adapt;

import android.support.v7.widget.RecyclerView;

import com.cc.carmanager.adapt.holder.CommonViewHolder;
import com.cc.carmanager.bean.CarsNewsBean;
import com.cc.carmanager.bean.NewsItemBean;

import java.util.List;

/**
 * Created by chenc on 2017/12/12.
 */

public abstract class CarsNewsBaseAdapter  extends RecyclerView.Adapter<CommonViewHolder> {
    protected List<NewsItemBean> datas;

    public void setDatas(List<NewsItemBean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }
}
