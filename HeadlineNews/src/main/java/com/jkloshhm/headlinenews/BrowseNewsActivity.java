package com.jkloshhm.headlinenews;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Created by guojian on 10/13/16.
 */
public class BrowseNewsActivity extends Activity {
    private static WebView webView;

    View.OnClickListener backClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                finish();
            }
        }
    };
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private int text_LogoId;
    private View popupWindow_view = null;
    private String webView_url, titleString, authorString;
    private Button back;
    private Button more;
    private ProgressBar progressBar_web;
    private TextView progressbar_text, author;
    private WebSettings settings;
    private PopupWindow popupWindow, popupWindowTextSize;
    View.OnClickListener popTextSizeClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (popupWindow != null && popupWindow.isShowing()) {
                popupWindow.dismiss();
                popupWindow = null;
            }
            getPopupWindowTextSize();
            //popupWindowTextSize.showAsDropDown(linearLayout_main, 0, -120, Gravity.BOTTOM);
            popupWindowTextSize.showAtLocation(linearLayout_main, Gravity.BOTTOM, 0, -120);
        }
    };
    View.OnClickListener popClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getPopupWindow();
            popupWindow.showAsDropDown(linearLayout_more, 0, 0, Gravity.NO_GRAVITY);
            //popupWindow_view.showAtLocation(linearLayout_more, Gravity.TOP | Gravity.START, windowPos[0], windowPos[1]);

        }
    };
    private LinearLayout linearLayout, linearLayout_main, linearLayout_back, linearLayout_more;
    View decorView;
    float downX, downY;
    float screenWidth, screenHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_news);
        //获取只能被本应用程序读写的SharedPreference对象

        // 获得decorView
        decorView = getWindow().getDecorView();

        // 获得手机屏幕的宽度和高度，单位像素
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;


        preferences = getSharedPreferences("headlinenews", MODE_PRIVATE);
        editor = preferences.edit();

        linearLayout_main = (LinearLayout) findViewById(R.id.ll_webView_main);
        linearLayout = (LinearLayout) findViewById(R.id.ll_webView_title);
        linearLayout_back = (LinearLayout) findViewById(R.id.ll_back_home);
        linearLayout_more = (LinearLayout) findViewById(R.id.ll_webView_more);
        progressBar_web = (ProgressBar) findViewById(R.id.progressbar_web);
        progressbar_text = (TextView) findViewById(R.id.progressbar_tv_web);

        author = (TextView) findViewById(R.id.tv_web_author);
        text_LogoId = preferences.getInt("logoId", 2);
        initWebView(text_LogoId);
        author.setText(authorString);
        linearLayout_back.setOnClickListener(backClick);
        linearLayout_more.setOnClickListener(popClick);

    }

    private void initWebView(int logoId) {
        int a = 85 + (logoId - 1) * 15;
        webView = (WebView) findViewById(R.id.web_view);
        settings = webView.getSettings();
        settings.setSupportZoom(true);
        settings.setTextZoom(a);
        webView_url = getIntent().getStringExtra("url");
        titleString = getIntent().getStringExtra("title");
        authorString = getIntent().getStringExtra("author_name");
        webView.loadUrl(webView_url);
        webView.getSettings().setJavaScriptEnabled(false);
        //webView.getSettings().
        //webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //设置 缓存模式
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启 DOM storage API 功能
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        //加载过程中显示 正在加载..
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress >= 100) {
                    // 切换页面
                    progressBar_web.setVisibility(View.GONE);
                    progressbar_text.setVisibility(View.GONE);

                }
            }
        });
        webView.setVisibility(View.VISIBLE);
    }

    /**
     * 创建PopupWindow
     */
    protected void initPopupWindow() {
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        popupWindow_view = getLayoutInflater().inflate(R.layout.pop_window, null, false);
        popupWindow_view.setFocusable(true); // 这个很重要
        popupWindow_view.setFocusableInTouchMode(true);
        LinearLayout share = (LinearLayout) popupWindow_view.findViewById(R.id.bn_share);
        LinearLayout changTextSize = (LinearLayout) popupWindow_view.findViewById(R.id.bn_change_textSize);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
                String title2 = webView.getTitle();
                String webView_url2 = webView.getUrl();
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "【" + title2 + "】" + webView_url2 + "(分享来自jkloshhm的头条新闻)");
                shareIntent.setType("text/plain");
                startActivity(Intent.createChooser(shareIntent, "分享到"));
            }
        });
        changTextSize.setOnClickListener(popTextSizeClick);
        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        popupWindow = new PopupWindow(popupWindow_view, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.MATCH_PARENT, true);
        // 设置动画效果
        popupWindow.setFocusable(true);
        // 重写onKeyListener
        popupWindow_view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    popupWindow.dismiss();
                    popupWindow = null;
                    return true;
                }
                return false;
            }
        });
        popupWindow.setAnimationStyle(R.style.AnimationFade);
        // 点击其他地方消失
        popupWindow_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
                return false;
            }
        });
    }

    /***
     * 获取PopupWindow实例
     */
    private void getPopupWindow() {
        if (null != popupWindow) {
            popupWindow.dismiss();
        } else {
            initPopupWindow();
        }
    }

    private void getPopupWindowTextSize() {
        if (null != popupWindowTextSize) {
            popupWindowTextSize.dismiss();
        } else {
            changeTextSizePopupWindow();
        }
    }

    protected void changeTextSizePopupWindow() {
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        final View popupWindow_textSize = getLayoutInflater().inflate(R.layout.pop_window_textsize, null, false);
        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        popupWindow_textSize.setFocusable(true); // 这个很重要
        popupWindow_textSize.setFocusableInTouchMode(true);
        popupWindowTextSize = new PopupWindow(popupWindow_textSize, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT, true);
        // 设置动画效果
        popupWindowTextSize.setFocusable(true);
        // 重写onKeyListener
        popupWindow_textSize.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    popupWindowTextSize.dismiss();
                    popupWindowTextSize = null;
                    WindowManager.LayoutParams params = getWindow().getAttributes();
                    params.alpha = 1f;
                    getWindow().setAttributes(params);
                    return true;
                }
                return false;
            }
        });
        popupWindowTextSize.setAnimationStyle(R.style.AnimationFade_Settings);
        //设置actiivity透明度
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        // 点击其他地方消失
        popupWindow_textSize.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupWindowTextSize != null && popupWindowTextSize.isShowing()) {
                    popupWindowTextSize.dismiss();
                    popupWindowTextSize = null;
                    WindowManager.LayoutParams params = getWindow().getAttributes();
                    params.alpha = 1f;
                    getWindow().setAttributes(params);
                }
                return false;
            }
        });

        final RadioGroup radioGroup = (RadioGroup) popupWindow_textSize.findViewById(R.id.radio_group);
        final RadioButton radioButton70 = (RadioButton) radioGroup.findViewById(R.id.bn_textSize_70);
        final RadioButton radioButton90 = (RadioButton) radioGroup.findViewById(R.id.bn_textSize_90);
        final RadioButton radioButton110 = (RadioButton) radioGroup.findViewById(R.id.bn_textSize_110);

        int loId = preferences.getInt("logoId", 2);
        Log.i("guo_loId_RadioGroup", "loId===" + loId);
        if (loId == 1) {
            radioButton70.setChecked(true);
        } else if (loId == 2) {
            radioButton90.setChecked(true);
        } else if (loId == 3) {
            radioButton110.setChecked(true);
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (R.id.bn_textSize_70 == checkedId && popupWindowTextSize != null) {
                    editor.putInt("logoId", 1);
                    editor.commit();
                    initWebView(1);
                } else if (R.id.bn_textSize_90 == checkedId && popupWindowTextSize != null) {
                    editor.putInt("logoId", 2);
                    editor.commit();
                    initWebView(2);
                } else if (R.id.bn_textSize_110 == checkedId && popupWindowTextSize != null) {
                    editor.putInt("logoId", 3);
                    editor.commit();
                    initWebView(3);
                }
            }
        });
    }


}


