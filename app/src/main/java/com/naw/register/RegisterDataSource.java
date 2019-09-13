package com.naw.register;

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

public class RegisterDataSource {

    public boolean registerService(final String username, final String password,final String phone,final Handler registerhandler){
        try{
            new Thread(){
                @Override
                public void run() {
                    Log.d("register_info",username);

                    OkHttpClient client=new OkHttpClient.Builder()
                            .connectTimeout(300, TimeUnit.MILLISECONDS)
                            .readTimeout(300,TimeUnit.MILLISECONDS)
                            .writeTimeout(300,TimeUnit.MILLISECONDS)
                            .build();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("username",username)
                            .add("password",password)
                            .add("phone",phone)
                            .build();
                    Request request = new Request.Builder()
                            .url("http://47.100.62.192:8080/register")
                            .addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                            .header("User-Agent", "OkHttp Example")
                            .post(requestBody)
                            .build();
                    final Call call = client.newCall(request);
                    //发起请求
                    call.enqueue(
                            new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    Message message=new Message();
                                    Bundle bundle=new Bundle();
                                    bundle.putInt("status",400);
                                    message.setData(bundle);
                                    registerhandler.sendMessage(message);
                                    Log.d("register_info",e.getMessage());
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    Message message=new Message();
                                    Bundle bundle=new Bundle();
                                    if (response.code()==200){
                                        String data=response.body().string();
                                        Log.d("register_info",data);
                                        try {
                                            JSONObject jsonObject=new JSONObject(data);
                                            Log.d("resgister_info",jsonObject.toString());
                                            Iterator<String> keys=jsonObject.keys();
                                            while(keys.hasNext()){
                                                String key=keys.next();
                                                if (jsonObject.opt(key)instanceof String){
                                                    bundle.putString(key,jsonObject.optString(key));
                                                }else if (jsonObject.opt(key) instanceof Integer){
                                                    bundle.putInt(key,jsonObject.optInt(key));
                                                }
                                            }
                                            bundle.putString("username",username);
                                            Log.d("register_info",bundle.toString());
                                            Log.d("register_info","注册成功，用户为："+username);
                                        } catch (Exception e) {
                                            Log.d("register_info",e.getMessage());
                                        }
                                    }else {
                                        bundle.putInt("status",400);
                                    }
                                    message.setData(bundle);
                                    registerhandler.sendMessage(message);
                                }
                            }
                    );

                }
            }.start();
        } catch (Exception e) {
            Log.d("debug111",e.getMessage());
            Message message=new Message();
            Bundle bundle=new Bundle();
            bundle.putInt("status",400);
            message.setData(bundle);
            registerhandler.sendMessage(message);
            return false;
        }
        return false;
    }

}