package com.example.cookdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.apigateway.ApiInvokeException;
import com.alibaba.apigateway.ApiRequest;
import com.alibaba.apigateway.ApiResponse;
import com.alibaba.apigateway.ApiResponseCallback;
import com.alibaba.apigateway.client.ApiGatewayClient;
import com.alibaba.apigateway.enums.HttpMethod;
import com.alibaba.apigateway.service.RpcService;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private String TAG = "guojian_CookDemo";
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String jsonData = (String) msg.obj;
            //Log.i(TAG,"data===="+jsonData);
            if(jsonData != null){
                try {
                    JSONObject dataJsonObject =  new JSONObject(jsonData);
                    String result = dataJsonObject.getString("result");
                    String list = new JSONObject(result).getString("list");
                    JSONObject lisJsonObject = new JSONObject(list);
                    String name = lisJsonObject.getString("name");
                    //String
                    //textView.setText(name);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
            System.out.print(jsonData);
            textView.setText(jsonData);

        }
    };

    private Button button;
    private static TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化API网关SDK
        initGatewaySdk();
        setContentView(R.layout.activity_main);


        button =(Button) findViewById(R.id.search);
        textView = (TextView) findViewById(R.id.text_show);
        if (button != null){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this,"搜索菜谱",Toast.LENGTH_LONG).show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            GatData1();
                        }
                    }).start();
                }
            });
        }
    }
    private void initGatewaySdk() {
        // 初始化API网关
        ApiGatewayClient.init(getApplicationContext(), false);
    }

    private void GatData1() {
        // 获取服务
        RpcService rpcService = ApiGatewayClient.getRpcService();
        final ApiRequest apiRequest = new ApiRequest();
        // 设置请求地址、Path及Method
        //apiRequest.setAddress("https://dm-51.data.aliyun.com");
        //apiRequest.setPath("/rest/160601/ocr/ocr_idcard.json");
        apiRequest.setAddress("http://jisusrecipe.market.alicloudapi.com");//http://jisusrecipe.market.alicloudapi.com/recipe/search
        apiRequest.setPath("/recipe/search");
        apiRequest.setMethod(HttpMethod.GET);
        apiRequest.addQuery("keyword", "红烧肉");
        apiRequest.addQuery("num", "5");
        // 按照文档设置二进制形式Body，支持设置Query参数、Header参数、Form形式Body
        //apiRequest.setStringBody("JSON格式参数");
        // 设置支持自签等形式的证书，如果服务端证书合法请勿设置该值，仅在开发测试或者非常规场景下设置。
        apiRequest.setTrustServerCertificate(true);
        // 设置超时
        apiRequest.setTimeout(10000);
        rpcService.call(apiRequest, new ApiResponseCallback() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                    // 处理apiResponse
                try {
                    // ApiResponse apiResponse = rpcService.call(apiRequest);
                    // 处理apiResponse，以下为常用方法
                    // RequestId
                    // apiResponse.getRequestId();
                    // Code
                    // apiResponse.getCode();
                    // ErrorMessage
                    String e= apiResponse.getErrorMessage();
                    // Response Content
                    String s = null;
                    s =apiResponse.getStringBody();
                    //Log.i(TAG,"data===="+s);
                    Message msg = new Message();
                    msg.obj = s;
                    handler.sendMessage(msg);
                    // Response Headers
                    // apiResponse.getHeaders();
                } catch (Exception e) {
                    // 处理异常
                }
            }
            @Override
            public void onException(ApiInvokeException e) {
                // 处理异常
                Toast.makeText(MainActivity.this,"请求数据失败...",Toast.LENGTH_LONG).show();

            }
        });
    }
    private void GatData2() {

        // 获取服务
        RpcService rpcService = ApiGatewayClient.getRpcService();
        ApiRequest apiRequest = new ApiRequest();
        // 设置请求地址、Path及Method
        apiRequest.setAddress("http://jisusrecipe.market.alicloudapi.com");//http://jisusrecipe.market.alicloudapi.com/recipe/search
        apiRequest.setPath("/recipe/search");
        apiRequest.setMethod(HttpMethod.GET);
        // 按照文档设置Query参数，支持设置Header参数、Form形式Body及二进制形式Body
        apiRequest.addQuery("keyword", "红烧肉");
        apiRequest.addQuery("num", "2");
        //apiRequest.addQuery("SignName", "demo");
        //apiRequest.addQuery("TemplateCode", "demo");
        // 设置超时时间及其他
        apiRequest.setTimeout(3000);
        try {
            ApiResponse apiResponse = rpcService.call(apiRequest);
            // 处理apiResponse，以下为常用方法
            // RequestId
            // apiResponse.getRequestId();
            // Code
            // apiResponse.getCode();
            // ErrorMessage
            String e= apiResponse.getErrorMessage();
            // Response Content
            String s = null;
            s =apiResponse.getStringBody();
            Log.i(TAG,"data===="+s);

            Message msg = new Message();
            msg.obj = s;
            handler.sendMessage(msg);
            // Response Headers
            // apiResponse.getHeaders();
        } catch (ApiInvokeException e) {
            // 处理异常
        }
    }
}
