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
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.cc.carmanager.R;
import com.cc.carmanager.activity.CarsDetailActivity;
import com.cc.carmanager.adapt.holder.CommonViewHolder;
import com.cc.carmanager.bean.BrandsItemBean;
import com.cc.carmanager.bean.CarsBrandBean;
import com.cc.carmanager.vollley.MySingleton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by chenc on 2017/10/26.
 */
public class CarsBrandAdapter extends RecyclerView.Adapter<CommonViewHolder>  implements SectionIndexer {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_HOT = 1;

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private RecyclerView recyclerView;
    private CarsBrandBean datas;
    private List<BrandsItemBean> listItem;


    int defaultImage = R.drawable.load_fail;
    int failImage = R.drawable.load_fail;
    private int[] defaultImages = new int[]{defaultImage};

    public CarsBrandAdapter(Context context, RecyclerView recyclerView) {
        mContext = context;
        this.recyclerView = recyclerView;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setDatas(CarsBrandBean datas) {
        this.datas = datas;
        listItem = new ArrayList<>();
        for (CarsBrandBean.CarBrandBean bean : datas.getBrandlist()){
            for (CarsBrandBean.CarBean car : bean.getList()){
                BrandsItemBean item = new BrandsItemBean();
                item.setName(car.getName());
                item.setImgurl(car.getImgurl());
                item.setFirstChar(bean.getLetter().charAt(0));
                listItem.add(item);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HOT;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            CommonViewHolder itemVH = CommonViewHolder.getViewHolder(parent, R.layout.item_carsbrand);
            return itemVH;
        } else if (viewType == TYPE_HOT) {
            CommonViewHolder hotVH = CommonViewHolder.getViewHolder(parent, R.layout.item_carsbrand_hot);
            return hotVH;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case TYPE_HOT:
                holder.setGridView(R.id.hot_cars, new
                        HotCityAdapter((holder).getItemView().getContext(), listItem.subList(0,10)), new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                    }
                });
                break;
            case TYPE_ITEM:
                holder.setText(R.id.contact_title,listItem.get(position).getName());
                holder.setNetworkImageView(R.id.iv_left_image,listItem.get(position).getImgurl(), defaultImage);
                holder.setItemClick(new TextViewHolderListener(position));

                if(position < 2 || (listItem.get(position - 1).getFirstChar() !=
                        listItem.get(position).getFirstChar())) {
                    holder.setText(R.id.contact_catalog, String.valueOf(listItem.get(position).getFirstChar()));

                    holder.getView(R.id.contact_catalog).setVisibility(View.VISIBLE);
                    holder.getView(R.id.contact_line).setVisibility(View.VISIBLE);
                }else {
                    holder.getView(R.id.contact_catalog).setVisibility(View.GONE);
                    holder.getView(R.id.contact_line).setVisibility(View.GONE);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return listItem == null ? 0 : listItem.size();
    }

    class HotCityAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater inflater;
        private List<BrandsItemBean> hotCitys;

        public HotCityAdapter(Context context, List<BrandsItemBean> hotCitys) {
            this.context = context;
            inflater = LayoutInflater.from(this.context);
            this.hotCitys = hotCitys;
        }

        @Override
        public int getCount() {
            return hotCitys.size();
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
            convertView = inflater.inflate(R.layout.item_car_hot, null);
            TextView city = (TextView) convertView.findViewById(R.id.contact_title);
            city.setText(hotCitys.get(position).getName());
            NetworkImageView networkImageView = (NetworkImageView) convertView.findViewById(R.id.iv_left_image);
            networkImageView.setImageUrl(hotCitys.get(position).getImgurl(),
                    MySingleton.getInstance().getImageLoader());
            return convertView;
        }
    }

    class TextViewHolderListener implements View.OnClickListener {
        int position;
        TextViewHolderListener(int i) {
            position = i;
        }
        @Override
        public void onClick(View v) {
            Log.d("RVA", "TextViewHolderListener :" + listItem.get(position) + "");
            Intent i = new Intent(mContext, CarsDetailActivity.class);
            mContext.startActivity(i);
        }
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int section) {
        for (int i = 0; i < listItem.size(); i++) {
            char firstChar = listItem.get(i).getFirstChar();
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int i) {
        return 0;
    }
}
