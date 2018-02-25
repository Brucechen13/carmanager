package com.cc.carmanager.adapt;

import android.support.v7.widget.RecyclerView;

import com.cc.carmanager.adapt.holder.CommonViewHolder;
import com.cc.carmanager.bean.CarsNewsBean;

/**
 * Created by chenc on 2017/12/12.
 */

public abstract class CarsNewsBaseAdapter  extends RecyclerView.Adapter<CommonViewHolder> {
    protected CarsNewsBean datas;

    public void setDatas(CarsNewsBean datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }
}
