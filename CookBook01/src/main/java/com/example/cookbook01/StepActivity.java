package com.example.cookbook01;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cookbook01.adapter.StepAdapter;
import com.example.cookbook01.bean.StepBean;
import com.example.cookbook01.utils.HttpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guojian on 10/10/16.
 */
public class StepActivity extends Activity {

    static StepAdapter stepAdapter;
    private static List<StepBean> stepBeanList = new ArrayList<StepBean>();
    private TextView title, ingredients, burden;
    private ImageView titleImageView;
    private ListView listView;
    private LinearLayout linearLayout_back, linearLayout_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_step_list);
        title = (TextView) findViewById(R.id.tv_step_title);
        linearLayout_back = (LinearLayout) findViewById(R.id.ll_back_home);
        linearLayout_share = (LinearLayout) findViewById(R.id.ll_share);
        listView = (ListView) findViewById(R.id.lv_step);
        LayoutInflater mLayoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mLayoutInflater.inflate(R.layout.layout_step_header, null);
        ingredients = (TextView) view.findViewById(R.id.tv_step_ingredients);
        burden = (TextView) view.findViewById(R.id.tv_step_burden);
        titleImageView = (ImageView) view.findViewById(R.id.iv_step_title);
        listView.addHeaderView(view);
        final String titleString = getIntent().getStringExtra("title");
        final String ingredientsString = getIntent().getStringExtra("ingredients");
        final String burdenString = getIntent().getStringExtra("burden");
        final String imgUrlString = getIntent().getStringExtra("img_url");
        title.setText(titleString);
        ingredients.setText(ingredientsString);
        burden.setText(burdenString);
        HttpUtils.setPicBitmap(titleImageView, imgUrlString);

        stepBeanList = (List<StepBean>) getIntent().getSerializableExtra("stepBeanList");
        stepAdapter = new StepAdapter(this, stepBeanList);
        listView.setAdapter(stepAdapter);
        linearLayout_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        linearLayout_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StepBean stepBean;
                StringBuffer step = new StringBuffer();
                for (int i = 0; i < stepBeanList.size(); i++) {
                    stepBean = (stepBeanList.get(i));
                    step.append(stepBean.getStep_text() + "\n");
                    //Log.i("guojian","stepbean======="+stepBean.getStep_text());
                }
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "【" + titleString + "】\n主料：" + ingredientsString
                        + "；\n辅料：" + burdenString
                        + "；\n做法：" + step
                        + "\n(分享来自jkloshhm的cooking)");
                shareIntent.setType("text/plain");
                startActivity(Intent.createChooser(shareIntent, "分享到"));
            }
        });
        /*
        intent.putExtra("step_text", stepBean.getStep_text());
        intent.putExtra("step_img_url", stepBean.getStep_img_url());
        intent.putExtra("title", cookBean.getTitle());
        intent.putExtra("img_url", cookBean.getImg_url());
        intent.putExtra("ingredients", cookBean.getIngredients());
        intent.putExtra("burden", cookBean.getBurden());
        webView = (WebView) findViewById(R.id.webView);
        String pic_url = getIntent().getStringExtra("content_url");
        webView.loadUrl(pic_url);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);*/
    }
}
