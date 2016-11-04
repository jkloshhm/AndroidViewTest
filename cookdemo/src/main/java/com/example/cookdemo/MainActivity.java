package com.example.cookdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.apigateway.client.ApiGatewayClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static TextView textView;
    private static ParentClassBean parentClassBean;
    private static ChildrenClassBean childrenClassBean;
    private static List<ParentClassBean> parentClassBeenList;
    private static List<ChildrenClassBean> childrenClassBeenList;
    private String TAG = "guojian_CookDemo";
    private Button mButtonSearch, mButtonClass;
    private ListView mListViewParent, mListViewChildren;
    private ParentClassAdapter parentClassAdapter;
    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle jsonBundle = msg.getData();
            String classType = jsonBundle.getString("classType");
            String jsonErrorMessage = jsonBundle.getString("errorMessage");
            String jsonData = jsonBundle.getString("stringBody");
            Log.i(TAG, "--------->>jsonData====" + jsonData);
            Log.i(TAG, "--------->>jsonErrorMessage====" + jsonErrorMessage);
            if (jsonData != null && jsonErrorMessage == null) {
                if (classType != null && classType.equals("GetDataBySearchName")) {//按名称搜索菜谱
                    try {
                        JSONObject dataJsonObject = new JSONObject(jsonData);
                        String result = dataJsonObject.getString("result");
                        String list = new JSONObject(result).getString("list");
                        JSONArray listJsonArray = new JSONArray(list);
                        //for (int i = 0; i < listJsonArray.length();i++);
                        JSONObject lisJsonObject = listJsonArray.getJSONObject(0);
                        String name = lisJsonObject.getString("name");
                        //textView.setText(jsonData);
                        //textView.setText(name);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (classType != null && classType.equals("GetDataClass")) {//分类名称
                    try {
                        JSONObject dataJsonObject = new JSONObject(jsonData);
                        String result = dataJsonObject.getString("result");
                        /*String list = new JSONObject(result).getString("list");*/
                        JSONArray resultJsonArray = new JSONArray(result);
                        StringBuffer s = new StringBuffer();
                        //parentClassBeenList = new ArrayList<ParentClassBean>();
                        //parentClassBeenList.clear();
                        for (int i = 0; i < resultJsonArray.length(); i++) {
                            JSONObject resultJsonObject = resultJsonArray.getJSONObject(i);
                            String classId_parent = resultJsonObject.getString("classid");
                            String className_parent = resultJsonObject.getString("name");
                            String parentId_parent = resultJsonObject.getString("parentid");
                            JSONArray list_parent = resultJsonObject.getJSONArray("list");

                            childrenClassBeenList = new ArrayList<>();
                            for (int j = 0; j < list_parent.length(); j++) {
                                JSONObject list_children = list_parent.getJSONObject(j);
                                childrenClassBean = new ChildrenClassBean(
                                        list_children.getString("classid"),
                                        list_children.getString("name"),
                                        list_children.getString("parentid"));
                                childrenClassBeenList.add(childrenClassBean);
                                Log.i(TAG, "name=========" + list_children.getString("name"));
                            }
                            // public ParentClassBean(List<ChildrenClassBean> childrenClassBeen,
                            // String parentClassId, String parentClassName, String parentParentId)
                            parentClassBean = new ParentClassBean(
                                    childrenClassBeenList,
                                    classId_parent,
                                    className_parent,
                                    parentId_parent);
                            parentClassBeenList.add(parentClassBean);
                            s.append(classId_parent + "-" +
                                    className_parent + "-" +
                                    parentId_parent + "\n");

                        }
                        Log.i(TAG, "S=" + s);
                        //JSONObject lisJsonObject = listJsonArray.getJSONObject(0);
                        //String name = lisJsonObject.getString("name");
                        //String
                        //textView.setText(s);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (classType != null && classType.equals("GetDataByClassId")) {//分类名称ID
                    try {
                        /*JSONObject dataJsonObject = new JSONObject(jsonData);
                        String result = dataJsonObject.getString("result");
                        String list = new JSONObject(result).getString("list");
                        JSONArray listJsonArray = new JSONArray(list);
                        //for (int i = 0; i < listJsonArray.length();i++);
                        JSONObject lisJsonObject = listJsonArray.getJSONObject(0);
                        String name = lisJsonObject.getString("name");*/
                        //String
                        textView.setText(jsonData);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
            mListViewParent.setAdapter(parentClassAdapter);
            //System.out.print(jsonData);
            //textView.setText(jsonData);
        }
    };
    private ChildrenClassAdapter childrenClassAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化API网关SDK
        initGatewaySdk();
        setContentView(R.layout.activity_main);
        mListViewParent = (ListView) findViewById(R.id.lv_parent_class);
        mListViewChildren = (ListView) findViewById(R.id.lv_children_class);
        mButtonSearch = (Button) findViewById(R.id.search);
        //textView = (TextView) findViewById(R.id.text_show);
        if (mButtonSearch != null) {
            mButtonSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "搜索菜谱", Toast.LENGTH_LONG).show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            GetJsonUtils.GetDataClass(handler);
                        }
                    }).start();
                }
            });
        }
        parentClassBeenList = new ArrayList<>();
        parentClassAdapter = new ParentClassAdapter(this, parentClassBeenList);
        //mListViewChildren.setAdapter(parentClassAdapter);
        mListViewParent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ParentClassBean parentClassBean1 = parentClassBeenList.get(position);
                childrenClassBeenList = parentClassBean1.getChildrenClassBeen();
                childrenClassAdapter = new ChildrenClassAdapter(childrenClassBeenList,
                        MainActivity.this);
                mListViewChildren.setAdapter(childrenClassAdapter);
            }
        });

        mListViewChildren.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChildrenClassBean childrenClassBean1 = childrenClassBeenList.get(position);
                String classId = childrenClassBean1.getChildrenClassId();
                Intent intent = new Intent();

            }
        });

    }

    /*public class  MyParentItemClickListener extends AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        }
    } */
    private void initGatewaySdk() {
        // 初始化API网关
        ApiGatewayClient.init(getApplicationContext(), false);
    }

}
