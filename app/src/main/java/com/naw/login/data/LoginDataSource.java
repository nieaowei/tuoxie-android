package com.naw.login.data;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.naw.login.data.model.LoggedInUser;
import com.naw.login.ui.login.LoginActivity;
import com.naw.tuoxie_android.MainActivity;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Timeout;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private Handler mHandler = new Handler();

    public Result<LoggedInUser> login(String username, String password,final Handler loginhandler) {

        try {
            // TODO: handle loggedInUser authentication
            loginService(username,password,loginhandler);

            return new Result.Success<>(new LoggedInUser("1","1"));
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }


    private boolean loginService(final String username, final String password, final Handler loginhandler){
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
                            .add("password",password)
                            .build();
                    Request request = new Request.Builder()
                            .url("http://192.168.1.3:8080/login")
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
                                    loginhandler.sendMessage(message);
                                    Log.d("debug11123",e.getMessage());
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    Message message=new Message();
                                    Bundle bundle=new Bundle();
                                    bundle.putInt("status",response.code());
                                    bundle.putString("username",username);
                                    message.setData(bundle);
                                    loginhandler.sendMessage(message);
                                    Log.d("debug111",String.valueOf(response.code()));
                                }
                            }
                    );
                }
            }.start();
        }catch (Exception e){
            Log.d("debug111",e.getMessage());
            return false;
        }
        return false;
    }

}
