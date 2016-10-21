package com.example.cookbook01.adapter;

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

import com.example.cookbook01.utils.HttpUtils;
import com.example.cookbook01.R;
import com.example.cookbook01.bean.StepBean;

import java.util.List;

public class StepAdapter extends BaseAdapter {

    private Context context;
    private List<StepBean> stepBeanList;

    public StepAdapter(Context context, List<StepBean> stepBeanList) {
        this.context = context;
        this.stepBeanList = stepBeanList;
    }

    @Override
    public int getCount() {
        return stepBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return stepBeanList.get(position);
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
        TextView stepSteps = (TextView) convertView.findViewById(R.id.tv_item_step_steps);
        ImageView stepImgUrl = (ImageView) convertView.findViewById(R.id.iv_item_step_img);
        StepBean stepBean = stepBeanList.get(position);
        stepSteps.setText(stepBean.getStep_text());

        String cook_pic_url = stepBean.getStep_img_url();
        HttpUtils.setPicBitmap(stepImgUrl, cook_pic_url);
        return convertView;
    }
}
