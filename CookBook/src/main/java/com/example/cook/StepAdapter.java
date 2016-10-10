package com.example.cook;

/**
 * Created by guojian on 10/9/16.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class StepAdapter extends BaseAdapter {

    private Context context;
    private List<CookStepBean> cookStepBeanList;
    public StepAdapter(Context context, List<CookStepBean> cookStepBeanList) {
        this.context = context;
        this.cookStepBeanList = cookStepBeanList;
    }

    @Override
    public int getCount() {
        return cookStepBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return cookStepBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.step_item, null);
        }

        TextView stepP = (TextView) convertView.findViewById(R.id.step_item);
        ImageView imgUrl = (ImageView) convertView.findViewById(R.id.img_item);
        //ImageView ivPic = (ImageView) convertView.findViewById(R.id.ivPic);

        CookStepBean cookStepBean = cookStepBeanList.get(position);
        stepP.setText(cookStepBean.getStepp());
        //imgUrl.setText(cookStepBean.getStepimg_url());
        String pic_url = cookStepBean.getStepimg_url();
        HttpUtils.setPicBitmap(imgUrl, pic_url);
        return convertView;
    }
}
