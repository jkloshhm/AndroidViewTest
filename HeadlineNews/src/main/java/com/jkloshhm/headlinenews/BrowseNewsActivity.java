package com.jkloshhm.headlinenews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by guojian on 10/13/16.
 */
public class BrowseNewsActivity extends Activity {

    String webView_url, title;
    private WebView webView;
    private Button back;
    private Button share;
    private ProgressBar progressBar_web;
    private TextView progressbar_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_browse_news);
        progressBar_web = (ProgressBar) findViewById(R.id.progressbar_web);
        progressbar_text = (TextView) findViewById(R.id.progressbar_tv_web);
        back = (Button) findViewById(R.id.bn_back);
        share = (Button) findViewById(R.id.bn_share);
        webView = (WebView) findViewById(R.id.web_view);
        webView_url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        webView.loadUrl(webView_url);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        progressBar_web.setVisibility(View.GONE);
        progressbar_text.setVisibility(View.GONE);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    finish();
                }
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView_url = getIntent().getStringExtra("url");
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://familyfirst0.blog.163.com"));
                //shareIntent.setAction(Intent.ACTION_SENDTO,Uri.parse("http://familyfirst0.blog.163.com"));
                //shareIntent.putExtra(Intent.EXTRA_STREAM, webView_url);
                //shareIntent.putExtra(Intent.EXTRA_SUBJECT, title);
                shareIntent.putExtra(Intent.EXTRA_STREAM, webView_url);
                shareIntent.setType("text/plain");
                //设置分享列表的标题，并且每次都显示分享列表
                startActivity(Intent.createChooser(shareIntent, "分享到"));
            }
        });

    }


}
