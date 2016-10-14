package com.jkloshhm.headlinenews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by guojian on 10/12/16.
 */
public class NewsAdapter extends BaseAdapter {
    ViewHolder holder;
    private Context context;
    private List<NewsBean> newsBeanList;

    public NewsAdapter(Context context, List<NewsBean> newsBeanList) {
        this.context = context;
        this.newsBeanList = newsBeanList;
    }

    @Override
    public int getCount() {
        return newsBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.news_item, null);
            //新建一个viewholder对象
            holder = new ViewHolder();
            //将findviewbyID的结果赋值给holder对应的成员变量
            //holder.tvHolder = (TextView) convertView.findViewById(R.id.tv_item);
            holder.imageViewHolder = (ImageView) convertView.findViewById(R.id.iv_news_image);
            holder.titleHolder = (TextView) convertView.findViewById(R.id.tv_news_title);
            holder.dateHolder = (TextView) convertView.findViewById(R.id.tv_news_date);
            holder.authorHolder = (TextView) convertView.findViewById(R.id.tv_news_author);
            // 将holder与view进行绑定
            convertView.setTag(holder);
        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        NewsBean newsBean = newsBeanList.get(position);
        holder.titleHolder.setText(newsBean.getTitle());
        holder.dateHolder.setText(newsBean.getDate());
        holder.authorHolder.setText(newsBean.getAuthor_name());
        String img_url = newsBean.getThumbnail_pic_s();
        HttpUtils.setPicBitmap(holder.imageViewHolder, img_url);

        return convertView;
    }

    private static class ViewHolder {
        private TextView titleHolder;
        private TextView dateHolder;
        private TextView authorHolder;
        private ImageView imageViewHolder;
    }
}
