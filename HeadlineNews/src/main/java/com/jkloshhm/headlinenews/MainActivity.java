package com.jkloshhm.headlinenews;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ListView listViewNews;
    private ProgressBar progressBar;
    private NewsAdapter newsAdapter;
    private List<NewsBean> newsBeanList;
    ;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String data = (String) msg.obj;
            if (data != null) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONArray newsJsonArray = new JSONArray(data);
                    newsBeanList.clear();
                    for (int i = 0; i < newsJsonArray.length(); i++) {
                        JSONObject newsJsonArrayJSONObject = newsJsonArray.getJSONObject(i);
                        newsBeanList.add(new NewsBean(
                                newsJsonArrayJSONObject.getString("author_name"),
                                newsJsonArrayJSONObject.getString("date"),
                                newsJsonArrayJSONObject.getString("realtype"),
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
        Log.i("guojian____oncreate", "oncreate");
        newsBeanList = new ArrayList<NewsBean>();
        progressBar.setVisibility(View.VISIBLE);
        HttpUtils.getNewsJSON("", handler);
        newsAdapter = new NewsAdapter(this, newsBeanList);
        Log.i("guojian____newsBeanList", newsBeanList.toString());
        listViewNews.setAdapter(newsAdapter);
        listViewNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsBean newsBean = newsBeanList.get(position);
                Intent intent = new Intent(MainActivity.this,BrowseNewsActivity.class);
                //String author_name, String date, String real_type, String thumbnail_pic_s, String title, String url
                intent.putExtra("author_name",newsBean.getAuthor_name());
                intent.putExtra("date",newsBean.getDate());
                intent.putExtra("real_type",newsBean.getReal_type());
                intent.putExtra("title",newsBean.getTitle());
                intent.putExtra("url",newsBean.getUrl());
                startActivity(intent);
            }
        });
    }

}
