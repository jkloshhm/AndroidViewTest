package com.example.cookdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.alibaba.apigateway.ApiInvokeException;
import com.alibaba.apigateway.ApiRequest;
import com.alibaba.apigateway.ApiResponse;
import com.alibaba.apigateway.ApiResponseCallback;
import com.alibaba.apigateway.client.ApiGatewayClient;
import com.alibaba.apigateway.enums.HttpMethod;
import com.alibaba.apigateway.service.RpcService;

/**
 * Created by guojian on 11/2/16.
 */
public class GetJsonUtils {

    public static void GetDataBySearchName(final Handler handler, String name) {
        RpcService rpcService = ApiGatewayClient.getRpcService();
        final ApiRequest apiRequest = new ApiRequest();
        apiRequest.setAddress("http://jisusrecipe.market.alicloudapi.com");//http://jisusrecipe.market.alicloudapi.com/recipe/search
        apiRequest.setPath("/recipe/search");
        apiRequest.setMethod(HttpMethod.GET);
        apiRequest.addQuery("keyword", name);
        apiRequest.addQuery("num", "2");
        apiRequest.setTrustServerCertificate(true);
        apiRequest.setTimeout(10000);
        rpcService.call(apiRequest, new ApiResponseCallback() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                try {
                    String errorMessage = apiResponse.getErrorMessage();
                    String stringBody = apiResponse.getStringBody();
                    Bundle bundle = new Bundle();
                    bundle.putString("classType","GetDataBySearchName");
                    bundle.putString("errorMessage", errorMessage);
                    bundle.putString("stringBody", stringBody);
                    Message msg = new Message();
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onException(ApiInvokeException e) {
                e.printStackTrace();
            }
        });
    }
    public static void GetDataClass(final Handler handler) {
        RpcService rpcService = ApiGatewayClient.getRpcService();
        final ApiRequest apiRequest = new ApiRequest();
        apiRequest.setAddress("http://jisusrecipe.market.alicloudapi.com");//http://jisusrecipe.market.alicloudapi.com/recipe/search
        apiRequest.setPath("/recipe/class");
        apiRequest.setMethod(HttpMethod.GET);
        apiRequest.setTrustServerCertificate(true);
        apiRequest.setTimeout(10000);
        rpcService.call(apiRequest, new ApiResponseCallback() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                try {
                    String errorMessage = apiResponse.getErrorMessage();
                    String stringBody = apiResponse.getStringBody();
                    Bundle bundle = new Bundle();
                    bundle.putString("classType","GetDataClass");
                    bundle.putString("errorMessage", errorMessage);
                    bundle.putString("stringBody", stringBody);
                    Message msg = new Message();
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onException(ApiInvokeException e) {
                e.printStackTrace();
            }
        });
    }
    public static void GetDataByClassId(final Handler handler, String classId) {
        RpcService rpcService = ApiGatewayClient.getRpcService();
        final ApiRequest apiRequest = new ApiRequest();
        apiRequest.setAddress("http://jisusrecipe.market.alicloudapi.com");//http://jisusrecipe.market.alicloudapi.com/recipe/search
        apiRequest.setPath("/recipe/byclass");
        apiRequest.setMethod(HttpMethod.GET);
        apiRequest.addQuery("classid", classId);
        apiRequest.addQuery("num", "30");
        apiRequest.addQuery("start", "0");
        apiRequest.setTrustServerCertificate(true);
        apiRequest.setTimeout(10000);
        rpcService.call(apiRequest, new ApiResponseCallback() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                try {
                    String errorMessage = apiResponse.getErrorMessage();
                    String stringBody = apiResponse.getStringBody();
                    Bundle bundle = new Bundle();
                    bundle.putString("errorMessage", errorMessage);
                    bundle.putString("stringBody", stringBody);
                    bundle.putString("classType","GetDataByClassId");
                    Message msg = new Message();
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onException(ApiInvokeException e) {
                e.printStackTrace();
            }
        });
    }
}
