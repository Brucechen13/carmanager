package com.cc.carmanager.adapt;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.cc.carmanager.R;
import com.cc.carmanager.activity.CarsDetailListActivity;
import com.cc.carmanager.adapt.holder.CommonViewHolder;
import com.cc.carmanager.vollley.MySingleton;
import com.cc.carmanager.widget.MyGridView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by chenc on 2017/11/18.
 */

public class CarsFilterAdapter extends RecyclerView.Adapter<CommonViewHolder> {
    public enum CarsFilterType {
        PriceFilter, BranFilter, ConfigFilter
    }

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private List<String> listItem;
    private Map<String, List<String>> mapItem;
    private List<String> titles;
    private CarsFilterType curFilterType;

    public CarsFilterAdapter(Context context, List<String> listItem) {
        mContext = context;
        this.listItem = listItem;
        mLayoutInflater = LayoutInflater.from(context);
        curFilterType = CarsFilterType.PriceFilter;
    }

    public CarsFilterAdapter(Context context, Map<String, List<String>> mapItem, CarsFilterType type) {
        mContext = context;
        this.mapItem = mapItem;
        mLayoutInflater = LayoutInflater.from(context);
        curFilterType = type;
        titles = new ArrayList<>(mapItem.keySet());
        Collections.sort(titles);
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (curFilterType == CarsFilterType.PriceFilter) {
            return CommonViewHolder.getViewHolder(parent, R.layout.item_filter_price);
        } else if (curFilterType == CarsFilterType.BranFilter) {
            return CommonViewHolder.getViewHolder(parent, R.layout.item_filter_brand);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final CommonViewHolder holder, int position) {
        switch (curFilterType) {
            case PriceFilter:
                holder.setText(R.id.price_text, listItem.get(position));
                holder.setItemClick(new TextViewHolderListener(position));
                break;
            case BranFilter:
                holder.setText(R.id.filter_brand, titles.get(position));
                if (mapItem.get(titles.get(position)).size() == 1) {
                    holder.setItemClick(new TextViewHolderListener(position));
                    holder.getView(R.id.class_arrow).setVisibility(View.GONE);
                } else {
                    final BrandAdapter mydapter = new BrandAdapter(holder.getItemView().getContext(), mapItem.get(titles.get(position)));
                    holder.setGridView(R.id.grid, mydapter, new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            mydapter.setSeclection(position);
                            mydapter.notifyDataSetChanged();
                        }
                    });
                    holder.getView(R.id.grid).setVisibility(View.GONE);
                    holder.setItemClick(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            View v = holder.getView(R.id.grid);
                            if (v.getVisibility() == View.GONE) {
                                v.setVisibility(View.VISIBLE);
                            } else {
                                v.setVisibility(View.GONE);
                            }
                        }
                    });
                }
                break;
            case ConfigFilter:
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (curFilterType == CarsFilterType.PriceFilter) {
            return listItem == null ? 0 : listItem.size();
        } else {
            return titles == null ? 0 : titles.size();
        }
    }

    class TextViewHolderListener implements View.OnClickListener {
        int position;

        TextViewHolderListener(int i) {
            position = i;
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(mContext, CarsDetailListActivity.class);
            mContext.startActivity(i);
        }
    }

    class BrandAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater inflater;
        private List<String> titles;

        private List<Integer> savedSelect = new ArrayList<>();

        //标识选择的Item
        public void setSeclection(int position) {
            if (savedSelect.contains(position)) {
                savedSelect.remove((Integer) position);
            } else {
                savedSelect.add(position);
            }
        }

        public BrandAdapter(Context context, List<String> titles) {
            this.context = context;
            inflater = LayoutInflater.from(this.context);
            this.titles = titles;
        }

        @Override
        public int getCount() {
            return titles.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = inflater.inflate(R.layout.item_filter_select, null);
            TextView tv = (TextView) convertView.findViewById(R.id.title);
            tv.setText(titles.get(position));
            if (savedSelect.contains(position)) {
                tv.setBackgroundResource(R.drawable.bg_shape_green);
            } else {
                tv.setBackgroundResource(R.drawable.bg_shape_gray);
            }
            return convertView;
        }
    }
}
