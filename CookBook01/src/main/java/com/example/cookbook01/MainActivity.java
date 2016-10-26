package com.example.cookbook01;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cookbook01.adapter.CookAdapter;
import com.example.cookbook01.bean.CookBean;
import com.example.cookbook01.bean.StepBean;
import com.example.cookbook01.utils.ConnectionUtil;
import com.example.cookbook01.utils.HttpUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {


    public static final String APPKEY = "450725a76d063fd1262f95e22939964b";//配置您申请的KEY
    private static ProgressBar progressBar;
    private static ListView listViewCook;
    private static CookAdapter adapter;
    private static StepBean stepBean;
    private static List<CookBean> cookBeanList = new ArrayList<CookBean>();
    private static List<StepBean> stepBeanList = null;
    private static FrameLayout frameLayoutImage;
    static final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            progressBar.setVisibility(View.GONE);
            listViewCook.setVisibility(View.VISIBLE);
            frameLayoutImage.setVisibility(View.GONE);
            String jsonData = (String) msg.obj;
            try {
                JSONArray cookJsonArray = new JSONArray(jsonData);
                cookBeanList.clear();
                for (int i = 0; i < cookJsonArray.length(); i++) {
                    JSONObject cookJson = cookJsonArray.getJSONObject(i);
                    String albums = cookJson.getString("albums");
                    JSONArray albumsJsonArray = new JSONArray(albums);
                    String steps = cookJson.getString("steps");
                    JSONArray stepJsonArray = new JSONArray(steps);
                    stepBeanList = new ArrayList<>();
                    for (int k = 0; k < stepJsonArray.length(); k++) {
                        Log.i("guojian111_stepBeanList", "" + stepBeanList);
                        JSONObject stepJsonObject = stepJsonArray.getJSONObject(k);
                        stepBeanList.add(new StepBean(stepJsonObject.getString("img"), stepJsonObject.getString("step")));
                    }
                    cookBeanList.add(new CookBean(
                            cookJson.getString("title"),
                            albumsJsonArray.getString(0),
                            cookJson.getString("ingredients"),
                            cookJson.getString("burden"),
                            stepBeanList));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private LinearLayout linearLayout_search;
    private EditText name;
    private Button show;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    //1.获取菜谱大全的JSON数据
    public static void getCookJSON(String name, Context context) {
        //String title = null, steps = null, albumsJsonArrayString = null;
        String result = null;
        String url = "http://apis.juhe.cn/cook/query.php";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("menu", name);//需要查询的菜谱名
        params.put("key", APPKEY);//应用APPKEY(应用详细页查询)
        params.put("dtype", "");//返回数据的格式,xml或json，默认json
        params.put("pn", "");//数据返回起始下标
        params.put("rn", "");//数据返回条数，最大30
        params.put("albums", "");//albums字段类型，1字符串，默认数组
        try {

            result = HttpUtils.net(url, params, "GET", context);
            Log.i("result", result);
            JSONObject object = new JSONObject(result);
            if (object.getInt("error_code") == 0) {
                System.out.println(object.get("result"));
                result = object.get("result").toString();
                JSONObject dataJsonObject = new JSONObject(result);
                String data = dataJsonObject.getString("data");
                Message msg = new Message();
                msg.obj = data;
                handler.sendMessage(msg);
            } else {
                System.out.println(object.get("error_code") + ":" + object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        /** //设置状态栏透明
         if (Build.VERSION.SDK_INT >= 21) {
         View decorView = getWindow().getDecorView();
         int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
         decorView.setSystemUiVisibility(option);
         getWindow().setStatusBarColor(Color.TRANSPARENT);
         }*/
        linearLayout_search = (LinearLayout) findViewById(R.id.ll_search);
        frameLayoutImage = (FrameLayout) findViewById(R.id.fl_img_layout);
        //show = (Button) findViewById(R.id.btn_show);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        listViewCook = (ListView) findViewById(R.id.list_view_cook);
        name = (EditText) findViewById(R.id.name);
        name.setHintTextColor(getResources().getColor(R.color.gray));
        if(! ConnectionUtil.isConnectingToInternet(MainActivity.this)){
            ConnectionUtil.setNetworkMethod(MainActivity.this);
            Toast.makeText(MainActivity.this,
                    "亲，网络连了么？", Toast.LENGTH_LONG)
                    .show();
        }

        linearLayout_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                if (TextUtils.isEmpty(name.getText().toString().trim())) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "请输入正确的菜名", Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    //getCookJSON(name.getText().toString());
                    if(ConnectionUtil.isConnectingToInternet(MainActivity.this)){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                getCookJSON(name.getText().toString(),MainActivity.this);
                            }
                        }).start();

                    }else {
                        progressBar.setVisibility(View.GONE);
                        ConnectionUtil.setNetworkMethod(MainActivity.this);
                        //Toast.makeText(MainActivity.this, "亲，网络连了么？", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
        adapter = new CookAdapter(this, cookBeanList);
        listViewCook.setAdapter(adapter);
        listViewCook.setOnScrollListener(new PauseOnScrollListener(imageLoader,true,true));
        listViewCook.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return false;
            }
        });
        listViewCook.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CookBean cookBean = cookBeanList.get(position);
        Intent intent = new Intent(this, StepActivity.class);
        intent.putExtra("title", cookBean.getTitle());
        intent.putExtra("img_url", cookBean.getImg_url());
        intent.putExtra("ingredients", cookBean.getIngredients());
        intent.putExtra("burden", cookBean.getBurden());
        stepBeanList = cookBean.getStepBeanList();
        Bundle b = new Bundle();
        b.putSerializable("stepBeanList", (Serializable) stepBeanList);
        intent.putExtras(b);
        startActivity(intent);
    }
}



