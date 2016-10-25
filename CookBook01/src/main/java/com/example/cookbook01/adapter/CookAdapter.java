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

import com.example.cookbook01.R;
import com.example.cookbook01.bean.CookBean;
import com.example.cookbook01.utils.HttpUtils;

import java.util.List;

public class CookAdapter extends BaseAdapter {
    private final int TYPE_COUNT = 2;
    private final int FIRST_TYPE = 0;
    private final int OTHERS_TYPE = 1;

    private int currentType;

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

    /***
     * item不同布局设置
     */

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }


    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return FIRST_TYPE;
        } else {
            return OTHERS_TYPE;
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CookBean cookBean = cookBeanList.get(position);
        View firstItemView = null;
        View othersItemView = null;
        //获取到当前位置所对应的Type
        currentType= getItemViewType(position);
        System.out.println("type="+currentType);

        if (currentType== FIRST_TYPE) {
            firstItemView = convertView;
            FirstViewHolder holderFirst = null;

            if (firstItemView == null) {
                firstItemView = LayoutInflater.from(context).inflate(R.layout.cook_item, null);
                //新建一个viewholder对象
                holderFirst = new FirstViewHolder();
                //将findviewbyID的结果赋值给holder对应的成员变量
                //holder.tvHolder = (TextView) convertView.findViewById(R.id.tv_item);
                holderFirst.cookTitleHolder = (TextView) firstItemView.findViewById(R.id.tv_item_cook_title);
                holderFirst.cookIngredientsHolder = (TextView) firstItemView.findViewById(R.id.tv_item_ingredients);
                holderFirst.cookBurdenHolder = (TextView) firstItemView.findViewById(R.id.tv_item_burden);
                holderFirst.imageViewHolder = (ImageView) firstItemView.findViewById(R.id.iv_item_cook);
                // 将holder与view进行绑定
                firstItemView.setTag(holderFirst);
            } else {

                holderFirst = (FirstViewHolder) firstItemView.getTag();
            }
            holderFirst.cookTitleHolder.setText(cookBean.getTitle());
            holderFirst.cookIngredientsHolder.setText(cookBean.getIngredients());
            holderFirst.cookBurdenHolder.setText("辅料: " + cookBean.getBurden());
            //imgUrl.setText(cookStepBean.getStepimg_url());
            String cook_pic_url = cookBean.getImg_url();
            HttpUtils.setPicBitmap(holderFirst.imageViewHolder, cook_pic_url);
            convertView=firstItemView;

        }else {
            othersItemView = convertView;
            SecondViewHolder holder = null;

            if (othersItemView == null) {
                othersItemView = LayoutInflater.from(context).inflate(R.layout.cook_item1, null);
                //新建一个viewholder对象
                holder = new SecondViewHolder();
                //将findviewbyID的结果赋值给holder对应的成员变量
                //holder.tvHolder = (TextView) convertView.findViewById(R.id.tv_item);
                holder.cookTitleHolder = (TextView) othersItemView.findViewById(R.id.tv_item_cook_title);
                holder.cookIngredientsHolder = (TextView) othersItemView.findViewById(R.id.tv_item_ingredients);
                holder.cookBurdenHolder = (TextView) othersItemView.findViewById(R.id.tv_item_burden);
                holder.imageViewHolder = (ImageView) othersItemView.findViewById(R.id.iv_item_cook);
                // 将holder与view进行绑定
                othersItemView.setTag(holder);
            } else {

                holder = (SecondViewHolder) othersItemView.getTag();
            }
            holder.cookTitleHolder.setText(cookBean.getTitle());
            holder.cookIngredientsHolder.setText(cookBean.getIngredients());
            holder.cookBurdenHolder.setText("辅料: " + cookBean.getBurden());
            //imgUrl.setText(cookStepBean.getStepimg_url());
            String cook_pic_url = cookBean.getImg_url();
            HttpUtils.setPicBitmap(holder.imageViewHolder, cook_pic_url);
            convertView = othersItemView;
        }

        return convertView;
    }


     class FirstViewHolder {
        private TextView cookTitleHolder;
        private TextView cookIngredientsHolder;
        private TextView cookBurdenHolder;
        private ImageView imageViewHolder;
    }

     class SecondViewHolder {
        private TextView cookTitleHolder;
        private TextView cookIngredientsHolder;
        private TextView cookBurdenHolder;
        private ImageView imageViewHolder;
    }
}
