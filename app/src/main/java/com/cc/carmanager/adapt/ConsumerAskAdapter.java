package com.cc.carmanager.adapt;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cc.carmanager.R;
import com.cc.carmanager.adapt.holder.CommonViewHolder;
import com.cc.carmanager.bean.ConsumerAskListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenc on 2018/4/8.
 */

public class ConsumerAskAdapter extends RecyclerView.Adapter<CommonViewHolder>  {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private View view;
    private List<ConsumerAskListBean.ConsumerAskItemBean> datas;

    public ConsumerAskAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        datas = new ArrayList<>();
    }

    public void setDatas(List<ConsumerAskListBean.ConsumerAskItemBean> datas){
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        CommonViewHolder newsVH = CommonViewHolder.getViewHolder(parent, R.layout.consumerask_item);
        return newsVH;
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        holder.setText(R.id.chat_send_time,datas.get(position).getAddTime().getFullTime());
        holder.setText(R.id.chat_send_content,datas.get(position).getContent());
        holder.setText(R.id.chat_receive_time,datas.get(position).getReplayTime().getFullTime());
        holder.setText(R.id.chat_receive_content,datas.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
