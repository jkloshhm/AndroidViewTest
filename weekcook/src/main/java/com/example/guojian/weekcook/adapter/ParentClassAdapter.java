package com.example.guojian.weekcook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.guojian.weekcook.R;
import com.example.guojian.weekcook.bean.ParentClassBean;

import java.util.List;


/**
 * Created by guojian on 11/4/16.
 */
public class ParentClassAdapter extends BaseAdapter {
    private Context context;
    private List<ParentClassBean> parentClassBeenList;
    private int selectItem = 0;

    public ParentClassAdapter(Context context, List<ParentClassBean> parentClassBeenList) {
        this.context = context;
        this.parentClassBeenList = parentClassBeenList;
    }

    @Override
    public int getCount() {
        return parentClassBeenList.size();
    }

    @Override
    public Object getItem(int position) {
        return parentClassBeenList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null /*&& parentClassBeenList.size() != 0*/) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.parent_class_adapter_item, null);
            holder.textView = (TextView) convertView.findViewById(R.id.tv_parent_item);
            holder.linearLayout = (LinearLayout) convertView.findViewById(R.id.ll_parent_class);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ParentClassBean parentClassBean = parentClassBeenList.get(position);
        holder.textView.setText(parentClassBean.getParentClassName());

        if (selectItem == position) {
            holder.textView.setPressed(true);
            holder.textView.setSelected(true);
            //holder.linearLayout.setBackgroundColor(Color.GREEN);
            holder.linearLayout.setBackgroundResource(R.color.green);
        } else {
            holder.textView.setSelected(false);
            holder.textView.setPressed(false);
            holder.linearLayout.setBackgroundResource(R.color.gray1);
        }
        return convertView;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    class ViewHolder {
        private TextView textView;
        private LinearLayout linearLayout;
    }


}
