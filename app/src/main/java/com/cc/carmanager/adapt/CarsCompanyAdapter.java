package com.cc.carmanager.adapt;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cc.carmanager.R;
import com.cc.carmanager.activity.CarsDetailActivity;
import com.cc.carmanager.bean.CarsCompanyBean;
import com.cc.carmanager.util.Utils;

import java.util.ArrayList;

/**
 * Created by chenc on 2017/12/30.
 */

public class CarsCompanyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private RecyclerView recyclerView;
    private ArrayList<CarsCompanyBean.CarCompanyBean> listItem;

    public CarsCompanyAdapter(Context context, ArrayList<CarsCompanyBean.CarCompanyBean> listItem, RecyclerView recyclerView) {
        mContext = context;
        this.recyclerView = recyclerView;
        this.listItem = listItem;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View hold = mLayoutInflater.inflate(R.layout.item_seller, parent, false);
        return new CarCompanyViewHolder(hold);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((CarCompanyViewHolder) holder).company_name.setText(listItem.get(position).getSalesName());
        ((CarCompanyViewHolder) holder).company_place.setText(listItem.get(position).getLocation());
        ((CarCompanyViewHolder) holder).call_phone.setOnClickListener(new TextViewHolderListener(position));
        ((CarCompanyViewHolder) holder).nav_place.setOnClickListener(new TextViewHolderListener(position));
    }

    @Override
    public int getItemCount() {
        return listItem == null ? 0 : listItem.size();
    }

    public static class CarCompanyViewHolder extends RecyclerView.ViewHolder {
        TextView company_name;
        TextView company_place;
        TextView call_phone;
        TextView nav_place;
        TextView tvLine;

        View v;

        CarCompanyViewHolder(View view) {
            super(view);
            v = view;
            company_name = view.findViewById(R.id.company_name);
            company_place = view.findViewById(R.id.company_place);
            call_phone = view.findViewById(R.id.call_phone);
            nav_place = view.findViewById(R.id.nav_place);
            tvLine = view.findViewById(R.id.contact_line);
        }
    }

    class TextViewHolderListener implements View.OnClickListener {
        int position;
        TextViewHolderListener(int i) {
            position = i;
        }
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()){
                case R.id.call_phone:
                    intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + "1111111111");
                    intent.setData(data);
                    mContext.startActivity(intent);
                    break;
                case R.id.nav_place:
                    if (Utils.isBaiduMapAvailable(mContext)) {//传入指定应用包名
                        intent = new Intent();
                        intent.setData(Uri.parse("baidumap://map/geocoder?src=openApiDemo&address=" + listItem.get(position).getLocation()));//baidumap://map/geocoder?location=39.98871,116.43234
                        mContext.startActivity(intent); //启动调用
                    }
                    break;
            }
        }
    }
}
