package com.example.cookdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by guojian on 11/4/16.
 */
public class ParentClassAdapter extends BaseAdapter {
    private Context context;
    private List<ParentClassBean> parentClassBeenList;

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
        if (convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_parent_class_list_item, null);
            holder.textView = (TextView) convertView.findViewById(R.id.tv_parent_item);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        ParentClassBean parentClassBean = parentClassBeenList.get(position);
        holder.textView.setText( parentClassBean.getParentClassName());
        return convertView;
    }

    class ViewHolder{
        private TextView textView;
    }


}
