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
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.news_item,null);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_news_image);
        TextView title = (TextView) convertView.findViewById(R.id.tv_news_title);
        TextView date = (TextView) convertView.findViewById(R.id.tv_news_date);
        TextView author = (TextView) convertView.findViewById(R.id.tv_news_author);

        NewsBean newsBean = newsBeanList.get(position);
        title.setText(newsBean.getTitle());
        date.setText(newsBean.getDate());
        author.setText(newsBean.getAuthor_name());
        String img_url = newsBean.getThumbnail_pic_s();
        HttpUtils.setPicBitmap(imageView,img_url);
        return convertView;
    }
}
