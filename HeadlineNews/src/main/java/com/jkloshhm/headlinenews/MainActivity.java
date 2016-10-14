package com.jkloshhm.headlinenews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    String s = "";
    private ListView listViewNews;
    private ProgressBar progressBar;
    private NewsAdapter newsAdapter;
    private List<NewsBean> newsBeanList;
    //private LinearLayout linearLayout;
    private TextView tv_progressbar;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String data = (String) msg.obj;
            Log.i("guojian_handler_h", data);
            if (data != null) {
                progressBar.setVisibility(View.GONE);
                tv_progressbar.setVisibility(View.GONE);
                //linearLayout.setVisibility(View.VISIBLE);
                try {
                    JSONArray newsJsonArray = new JSONArray(data);
                    newsBeanList.clear();
                    for (int i = 0; i < newsJsonArray.length(); i++) {
                        JSONObject newsJsonArrayJSONObject = newsJsonArray.getJSONObject(i);
                        newsBeanList.add(new NewsBean(
                                newsJsonArrayJSONObject.getString("author_name"),
                                newsJsonArrayJSONObject.getString("date"),
                                //newsJsonArrayJSONObject.getString("realtype"),
                                newsJsonArrayJSONObject.getString("thumbnail_pic_s"),
                                newsJsonArrayJSONObject.getString("title"),
                                newsJsonArrayJSONObject.getString("url")));
                    }
                    Log.i("guojian_newsBeanList_h", newsBeanList.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        listViewNews = (ListView) findViewById(R.id.lv_news);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        tv_progressbar = (TextView) findViewById(R.id.tv_progressbar);
        //linearLayout = (LinearLayout) findViewById(R.id.ll_progressbar) ;
        Log.i("guojian____oncreate", "oncreate");
        newsBeanList = new ArrayList<NewsBean>();
        //linearLayout.setVisibility(View.VISIBLE);

        progressBar.setVisibility(View.VISIBLE);
        tv_progressbar.setVisibility(View.VISIBLE);
        //type类型: top(头条，默认),shehui(社会),guonei(国内),
        // guoji(国际),yule(娱乐), tiyu(体育),
        //junshi(军事),keji(科技),caijing(财经),shishang(时尚)


        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpUtils.getNewsJSON(s, handler);
                //progressBar.setVisibility(View.GONE);
            }
        }).start();

        newsAdapter = new NewsAdapter(this, newsBeanList);
        Log.i("guojian____newsBeanList", newsBeanList.toString());
        listViewNews.setAdapter(newsAdapter);

        listViewNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsBean newsBean = newsBeanList.get(position);
                Intent intent = new Intent(MainActivity.this, BrowseNewsActivity.class);
                //String author_name, String date, String real_type, String thumbnail_pic_s, String title, String url
                intent.putExtra("url", newsBean.getUrl());
                startActivity(intent);
            }
        });
    }

}
