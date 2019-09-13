package com.naw.register;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.naw.login.ui.login.LoginActivity;
import com.naw.register.RegisterDataSource;
import com.naw.tuoxie_android.R;

import java.util.Timer;
import java.util.TimerTask;

public class RegisterActivity extends AppCompatActivity {
    //创建Register的xml对象


    //设置register中Button和EditText名字
    private Button register;
    private Button cancel;
    private EditText username;
    private EditText password;
    private EditText confirmpassword;
    private EditText phone;
    private RegisterDataSource registerDataSource;
    private Handler registerhandler;
    private ProgressBar loadingProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //给声明的Button和EditText赋值
        register = findViewById(R.id.register_bt);
        cancel = findViewById(R.id.cancel_bt);
        username = findViewById(R.id.username_text);
        password = findViewById(R.id.password_text);
        phone = findViewById(R.id.phone_text);
        confirmpassword = findViewById(R.id.confirmpassword_text);
        loadingProgressBar = findViewById(R.id.loading);
        //phone= findViewById(R.id.);
        Init();
        registerhandler =new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.getData().getInt("status")){
                    case 200:{
                        final Intent intent=new Intent(RegisterActivity.this, LoginActivity.class);
                        final Bundle bundle=new Bundle();
                        bundle.putString("msg","注册成功");
                        intent.putExtras(bundle);

                        startActivity(intent);

                        break;
                    }
                    case 400:{
                        Log.d("register_info","handler 400");
                        Toast.makeText(RegisterActivity.this,"注册失败!"+msg.getData().getString("msg"),Toast.LENGTH_SHORT).show();
                        break;
                    }
                    default:{
                        Toast.makeText(RegisterActivity.this,"注册失败!"+msg.getData().getString("msg"),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
    }

    // 主界面
    public void Init(){
        //设置点击事件
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goHome();
//                Destroy();
            }
        });
    }

    //对点击注册进行判断是否注册成功
    public void goHome(){
        Log.d("debug111",username.getText().toString());
        if (username.getText().toString().equals("")){
            Toast.makeText(RegisterActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
        }else if (password.getText().toString().equals("")){
            Toast.makeText(RegisterActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
        }else if (confirmpassword.getText().toString().equals("")){
            Toast.makeText(RegisterActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
        }else if (!(password.getText().toString().equals(confirmpassword.getText().toString()))){
            Toast.makeText(RegisterActivity.this,"两次密码输入不一致",Toast.LENGTH_SHORT).show();
        } else{
            //else if (phone.getText().toString().equals("")){ Toast.makeText(RegisterActivity.this,"手机号码不能为空",Toast.LENGTH_SHORT).show(); }else{
            new RegisterDataSource().registerService(username.getText().toString(),password.getText().toString(),phone.getText().toString(),registerhandler);
        }
        Log.d("debug111","finish");

    }

    //销毁方法
    public void Destroy(){
        this.finish();
    }
}