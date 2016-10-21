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
import com.example.cookbook01.bean.CookBean;

import java.util.List;

public class CookAdapter extends BaseAdapter {

    private Context context;
    private List<CookBean> cookBeanList;

    public CookAdapter(Context context, List<CookBean> cookBeanList) {
        this.context = context;
        this.cookBeanList = cookBeanList;
    }

    @Override
    public int getCount() {
        return cookBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return cookBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.cook_item, null);
        }

        TextView cookTitle = (TextView) convertView.findViewById(R.id.tv_item_cook_title);
        TextView cookIngredients = (TextView) convertView.findViewById(R.id.tv_item_ingredients);
        TextView cookBurden = (TextView) convertView.findViewById(R.id.tv_item_burden);
        ImageView cookImgUrl = (ImageView) convertView.findViewById(R.id.iv_item_cook);
        //ImageView ivPic = (ImageView) convertView.findViewById(R.id.ivPic);

        CookBean cookBean = cookBeanList.get(position);
        cookTitle.setText(cookBean.getTitle());
        cookIngredients.setText(cookBean.getIngredients());
        cookBurden.setText(cookBean.getBurden());
        //imgUrl.setText(cookStepBean.getStepimg_url());
        String cook_pic_url = cookBean.getImg_url();
        HttpUtils.setPicBitmap(cookImgUrl, cook_pic_url);
        return convertView;
    }
}
