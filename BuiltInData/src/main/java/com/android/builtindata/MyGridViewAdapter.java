package com.android.builtindata;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by guojian on 2/17/17.
 */

public class MyGridViewAdapter extends BaseAdapter{
    private Context mContext;
    private int lastPosition;//定义一个标记为最后选择的位置
    private String[] str = null;

    public void setData(String[] str, int lastPos) {
        this.str = str;
        this.lastPosition = lastPos;
    }

    public void setSeclection(int position) {
        lastPosition = position;
    }

    public MyGridViewAdapter(Context mContext) {
        super();
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return str.length;
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
    public View getView(int position, View mView, ViewGroup arg2) {
        MyGridViewAdapter.ViewHolder holder = null;
        if (mView == null) {
            holder = new MyGridViewAdapter.ViewHolder();
            mView = LayoutInflater.from(mContext).inflate(R.layout.layout_item_gridview, null);
            holder.text = (TextView) mView.findViewById(R.id.idGridviewTextview);
            holder.check = (ImageView) mView.findViewById(R.id.idGridviewCheck);
            mView.setTag(holder);
        } else {
            holder = (MyGridViewAdapter.ViewHolder) mView.getTag();
        }
        holder.text.setText(str[position] + "");
        if (lastPosition == position) {//最后选择的位置
            holder.check.setBackgroundResource(R.drawable.storage_checkbox_checked);
        } else {
            holder.check.setBackgroundResource(R.drawable.storage_checkbox_unchecked);
        }
        return mView;
    }

    class ViewHolder {
        private TextView text;
        private ImageView check;
    }
}
