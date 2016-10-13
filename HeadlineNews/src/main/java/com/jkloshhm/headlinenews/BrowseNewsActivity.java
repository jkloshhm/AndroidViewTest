package com.jkloshhm.headlinenews;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by guojian on 10/13/16.
 */
public class BrowseNewsActivity extends Activity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_browse_news);
        webView = (WebView) findViewById(R.id.web_view);
        String webView_url = getIntent().getStringExtra("url");
        webView.loadUrl(webView_url);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

    }

}
