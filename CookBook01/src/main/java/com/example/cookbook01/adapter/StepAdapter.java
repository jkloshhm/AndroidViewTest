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
import com.example.cookbook01.bean.StepBean;
import com.example.cookbook01.utils.HttpUtils;

import java.util.List;

public class StepAdapter extends BaseAdapter {

    private final int STEP_TYPE_COUNT = 2;
    private final int ONE_TYPE = 0;
    private final int TWO_TYPE = 1;

    private int curType;


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
    public int getViewTypeCount() {
        return STEP_TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return ONE_TYPE;
        } else {
            return TWO_TYPE;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        curType = getItemViewType(position);
        View viewOne = null;
        View viewTwo = null;

        if (curType == ONE_TYPE) {
            viewOne = convertView;
            ViewHolderOne viewHolderOne = null;
            if (viewOne == null) {
                viewHolderOne = new ViewHolderOne();
                viewOne = LayoutInflater.from(context).inflate(R.layout.step_item, null);
                viewHolderOne.stepStepsHolder = (TextView) viewOne.findViewById(R.id.tv_item_step_steps);
                viewHolderOne.step_numberHolder = (TextView) viewOne.findViewById(R.id.step_number);
                viewHolderOne.stepImgUrlViewHolder = (ImageView) viewOne.findViewById(R.id.iv_item_step_img);
                viewOne.setTag(viewHolderOne);
            } else {
                viewHolderOne = (ViewHolderOne) viewOne.getTag();
            }
            StepBean stepBean = stepBeanList.get(position);
            viewHolderOne.stepStepsHolder.setText(stepBean.getStep_text());
            viewHolderOne.step_numberHolder.setText("" + (position + 1));
            String cook_pic_url = stepBean.getStep_img_url();
            HttpUtils.setPicBitmap(viewHolderOne.stepImgUrlViewHolder, cook_pic_url);
            convertView = viewOne;
        } else {
            viewTwo = convertView;
            StepViewHolderTwo holderTwo = null;
            if (viewTwo == null) {
                holderTwo = new StepViewHolderTwo();
                viewTwo = LayoutInflater.from(context).inflate(R.layout.step_item1, null);
                holderTwo.stepStepsHolder = (TextView) viewTwo.findViewById(R.id.tv_item_step_steps);
                holderTwo.step_numberHolder = (TextView) viewTwo.findViewById(R.id.step_number);
                holderTwo.stepImgUrlViewHolder = (ImageView) viewTwo.findViewById(R.id.iv_item_step_img);
                viewTwo.setTag(holderTwo);
            } else {
                holderTwo = (StepViewHolderTwo) viewTwo.getTag();
            }
            StepBean stepBean = stepBeanList.get(position);
            holderTwo.stepStepsHolder.setText(stepBean.getStep_text());
            holderTwo.step_numberHolder.setText("" + (position + 1));
            String cook_pic_url = stepBean.getStep_img_url();
            HttpUtils.setPicBitmap(holderTwo.stepImgUrlViewHolder, cook_pic_url);
            convertView = viewTwo;
        }
        return convertView;
    }

    class ViewHolderOne {
        private TextView stepStepsHolder;
        private TextView step_numberHolder;
        private ImageView stepImgUrlViewHolder;
    }

    class StepViewHolderTwo {
        private TextView stepStepsHolder;
        private TextView step_numberHolder;
        private ImageView stepImgUrlViewHolder;
    }

}

