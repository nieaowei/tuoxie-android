package com.naw.tuoxie_android;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainDataSource {
    final String url = "http://47.100.62.192:8080/getLastGPS";
    final String url1 = "http://47.100.62.192:8080/getLastFall";
    public void getLastGPSInfo(final String username,final Handler mainHandler){
        try {
            new Thread(){
                @Override
                public void run() {
                    OkHttpClient client = new OkHttpClient.Builder()
                            .connectTimeout(300, TimeUnit.MILLISECONDS)
                            .readTimeout(300,TimeUnit.MILLISECONDS)
                            .writeTimeout(300,TimeUnit.MILLISECONDS)
                            .build();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("username",username)
                            .build();
                    Request request = new Request.Builder()
                            .url(url)
                            .addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                            .header("User-Agent", "OkHttp Example")
                            .post(requestBody)
                            .build();

                    final Call call = client.newCall(request);
                    //第五步发起请求
                    call.enqueue(
                            new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    Message message=new Message();
                                    Bundle bundle=new Bundle();
                                    bundle.putInt("status",400);
                                    message.setData(bundle);
                                    mainHandler.sendMessage(message);
                                    Log.d("gps_info",e.getMessage()+" onfalid");
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    Message message=new Message();
                                    Bundle bundle=new Bundle();

                                    if(response.code()==200){
                                        String data = response.body().string();
                                        Log.d("gps_info",data);
                                        try {
                                            JSONObject jsonObject=new JSONObject(data);
                                            Log.d("gps_info",jsonObject.toString());
                                            Iterator<String> keys = jsonObject.keys();
                                            while (keys.hasNext()){
                                                String key = keys.next();
                                                if(jsonObject.opt(key) instanceof String){
                                                    bundle.putString(key,jsonObject.optString(key));
                                                }else if(jsonObject.opt(key)instanceof Integer){
                                                    bundle.putInt(key,jsonObject.optInt(key));
                                                }else {
                                                    bundle.putString(key,jsonObject.optString(key));
                                                }
                                            }
                                            bundle.putString("username",username);
                                            Log.d("gps_info",bundle.toString());
                                            Log.d("gps_info","登录成功，用户为："+username);

                                        }catch (Exception e){
                                            Log.d("gps_info",e.getMessage()+" onres");
                                        }
                                    }else {
                                        bundle.putInt("status",400);
                                    }
                                    message.setData(bundle);
                                    mainHandler.sendMessage(message);
                                }
                            }
                    );
                }
            }.start();
        }catch (Exception e){
            Log.d("gps_info",e.getMessage()+" aaa");
            Message message=new Message();
            Bundle bundle=new Bundle();
            bundle.putInt("status",400);
            message.setData(bundle);
            mainHandler.sendMessage(message);
        }
    }

    public void getLastFallInfo(final String username,final Handler mainHandler,final int i){
        try {
            new Thread(){
                @Override
                public void run() {
                    OkHttpClient client = new OkHttpClient.Builder()
                            .connectTimeout(300, TimeUnit.MILLISECONDS)
                            .readTimeout(300,TimeUnit.MILLISECONDS)
                            .writeTimeout(300,TimeUnit.MILLISECONDS)
                            .build();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("username",username)
                            .build();
                    Request request = new Request.Builder()
                            .url(url1)
                            .addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                            .header("User-Agent", "OkHttp Example")
                            .post(requestBody)
                            .build();

                    final Call call = client.newCall(request);
                    //第五步发起请求
                    call.enqueue(
                            new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    Message message=new Message();
                                    Bundle bundle=new Bundle();
                                    bundle.putInt("status",400);
                                    message.setData(bundle);
                                    mainHandler.sendMessage(message);
                                    Log.d("fall_info",e.getMessage()+" onfalid");
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    Message message=new Message();
                                    Bundle bundle=new Bundle();

                                    if(response.code()==200){
                                        String data = response.body().string();
                                        Log.d("fall_info",data);
                                        try {
                                            JSONObject jsonObject=new JSONObject(data);
                                            Log.d("fall_info",jsonObject.toString());
                                            Iterator<String> keys = jsonObject.keys();
                                            while (keys.hasNext()){
                                                String key = keys.next();
                                                if(jsonObject.opt(key) instanceof String){
                                                    bundle.putString(key,jsonObject.optString(key));
                                                }else if(jsonObject.opt(key)instanceof Integer){
                                                    bundle.putInt(key,jsonObject.optInt(key));
                                                }else {
                                                    bundle.putString(key,jsonObject.optString(key));
                                                }
                                            }
                                            bundle.putString("username",username);
                                            Log.d("fall_info",bundle.toString());
                                            if(i==1){
                                                bundle.putInt("status",300);
                                            }

                                        }catch (Exception e){
                                            Log.d("fall_info",e.getMessage()+" onres");
                                        }
                                    }else {
                                        bundle.putInt("status",400);
                                    }
                                    message.setData(bundle);
                                    mainHandler.sendMessage(message);
                                }
                            }
                    );
                }
            }.start();
        }catch (Exception e){
            Log.d("fall_info",e.getMessage()+" aaa");
            Message message=new Message();
            Bundle bundle=new Bundle();
            bundle.putInt("status",400);
            message.setData(bundle);
            mainHandler.sendMessage(message);
        }
    }
}

