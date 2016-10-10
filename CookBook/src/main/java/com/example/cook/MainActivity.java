package com.example.cook;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {
    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    //配置您申请的KEY
    public static final String APPKEY = "450725a76d063fd1262f95e22939964b";
    public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) " +
            "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
    static TextView showCook, zhuLiao, fuLiao, jianJie, stepOne;
    static String nameString = null, stepImgString = null, stepString = null, zhuLiaoString = null, fuliaoString = null, jianjieString = null;
    static ImageView imageViewCook, imgStep;
    static Bitmap bitmapCook;//bitmapStepImg;
    private static ProgressBar progressBar;
    private static ScrollView scrollView;

    static final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                //s = getRequest1(name.getText().toString());
                showCook.setText(nameString);
                zhuLiao.setText(zhuLiaoString);
                fuLiao.setText(fuliaoString);
               // jianJie.setText(jianjieString);
                imageViewCook.setImageBitmap(bitmapCook);
                //stepOne.setText(stepBean.getStepp());
                //imgStep.setImageBitmap(stepBean.getStepBitmapImg());
                //listView.setAdapter(simpleAdapter);
                progressBar.setVisibility(View.GONE);
                //scrollView.setVisibility(View.VISIBLE);
                linearlayout.setVisibility(View.VISIBLE);
                listViewStep.setVisibility(View.VISIBLE);
                //adapter.notifyDataSetChanged();
            }
        }
    };
    EditText name;
    Button show;
    private static LinearLayout linearlayout;
    private static ListView listViewStep;
    private static StepAdapter adapter;
    private static List<CookStepBean> cookStepBeanList = new ArrayList<CookStepBean>();;

    //1.菜谱大全
    public static String getRequest1(String name) {
        String title = null, steps = null, albumsJsonArrayString = null;
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
            result = net(url, params, "GET");
            Log.i("result", result);
            JSONObject object = new JSONObject(result);
            if (object.getInt("error_code") == 0) {
                System.out.println(object.get("result"));
                result = object.get("result").toString();
                JSONObject dataJsonObject = new JSONObject(result);
                String data = dataJsonObject.getString("data");
                JSONArray cookJsonArray = new JSONArray(data);
                //for (int i = 0; i < cookJsonArray.length(); i++) {
                    JSONObject cookJson = cookJsonArray.getJSONObject(0);
                    title = cookJson.getString("title");
                    zhuLiaoString = cookJson.getString("ingredients");//ingredients,burden,imtro
                    fuliaoString = cookJson.getString("burden");
                    jianjieString = cookJson.getString("imtro");
                    nameString = title;
                    String albums = cookJson.getString("albums");
                    JSONArray albumsJsonArray = new JSONArray(albums);
                    for (int j = 0; j < albumsJsonArray.length(); j++) {
                        albumsJsonArrayString = albumsJsonArray.getString(0);
                        //albums = albumsJsonObject.toString();
                    }
                    bitmapCook = showCookImgstep(albumsJsonArray.getString(0));
                    steps = cookJson.getString("steps");
                    JSONArray stepJsonArray = new JSONArray(steps);
                    Log.i("guojian_stepJsonArray", "" + stepJsonArray);
                    cookStepBeanList.clear();
                    for (int k = 0; k < stepJsonArray.length(); k++) {
                        JSONObject stepJsonObject = stepJsonArray.getJSONObject(k);
                        //String step_url = stepJsonObject.getString("step");
                        //String img_url = stepJsonObject.getString("img");
                        cookStepBeanList.add(new CookStepBean(stepJsonObject.getString("step"),stepJsonObject.getString("img")));
                    }

                handler.sendEmptyMessage(0x123);
                return title;

            } else {
                System.out.println(object.get("error_code") + ":" + object.get("reason"));
                return object.get("error_code") + ":" + object.get("reason");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap showCookImgstep(String url) {
        Bitmap bitmap = null;
        try {
            URL imgurl = new URL(url);
            InputStream is = imgurl.openStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return 网络请求字符串
     * @throws Exception
     */
    public static String net(String strUrl, Map params, String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if (method == null || method.equals("GET")) {
                strUrl = strUrl + "?" + urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if (method == null || method.equals("GET")) {
                conn.setRequestMethod("GET");
            } else {
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params != null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    out.writeBytes(urlencode(params));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }

    //将map型转为请求参数型
    public static String urlencode(Map<String, Object> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show = (Button) findViewById(R.id.btn_show);
        showCook = (TextView) findViewById(R.id.tx_show_cook);
        name = (EditText) findViewById(R.id.name);
        zhuLiao = (TextView) findViewById(R.id.tx_zhuliao);
        fuLiao = (TextView) findViewById(R.id.tx_fuliao);
        //jianJie = (TextView) findViewById(R.id.tx_jianjie);
        imageViewCook = (ImageView) findViewById(R.id.img) ;
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        linearlayout = (LinearLayout) findViewById(R.id.linearlayout);
        listViewStep = (ListView) findViewById(R.id.list_view);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                if (TextUtils.isEmpty(name.getText().toString().trim())) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "请输入正确的菜名", Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            getRequest1(name.getText().toString());
                        }
                    }).start();
                }
            }
        });

        adapter = new StepAdapter(this, cookStepBeanList);
        listViewStep.setAdapter(adapter);
        linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        listViewStep.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return false;
            }
        });

    }
}



