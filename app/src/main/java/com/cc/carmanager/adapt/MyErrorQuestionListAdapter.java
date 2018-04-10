package com.cc.carmanager.adapt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cc.carmanager.R;
import com.cc.carmanager.bean.QuestionListBean;
import com.cc.carmanager.bean.QuestionResultListBean;

import java.util.List;
import java.util.Map;

/**
 * Created by chenc on 2018/4/8.
 */

public class MyErrorQuestionListAdapter extends BaseAdapter {
    private ListView listView;
    List<QuestionListBean.QuestionItemBean> dataItems;
    private LayoutInflater mInflater;
    private Context context;

    public MyErrorQuestionListAdapter(Context context, List<QuestionListBean.QuestionItemBean> dataItems,ListView listView) {
        // this.context = context;
        this.listView = listView;
        this.context=context;
        this.dataItems = dataItems;
        this.mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_search_text, null);

            viewHolder.title = (TextView) convertView.findViewById(R.id.text);
            viewHolder.title.setText(dataItems.get(position).getQuestionContent());
        }

        convertView.setTag(viewHolder);
        return convertView;
    }

    public int getCount() {
        return dataItems.size();
    }

    @Override
    public Object getItem(int i) {
        return dataItems.get(i);
    }

    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        TextView title;
    }
}
