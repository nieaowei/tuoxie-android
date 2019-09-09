package com.naw.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.naw.login.ui.login.LoginActivity;
import com.naw.tuoxie_android.R;

public class RegisterActivity extends AppCompatActivity {
    //创建Register的xml对象


    //设置register中Button和EditText名字
    private Button register;
    private Button cancel;
    private EditText username;
    private EditText password;
    private EditText confirmpassword;

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
        confirmpassword = findViewById(R.id.confirmpassword_text);
        Init();
    }

    // 主界面
    public void Init(){
        //设置点击事件
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goHome();
                Destroy();
            }
        });
    }

    //对点击注册进行判断是否注册成功
    public void goHome(){
        //判断是否为空
        if(!(username.getFreezesText())){
            Toast.makeText(RegisterActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
        }
        //判断输入密码与再次输入密码是否相同
        else if(password.getFreezesText()==confirmpassword.getFreezesText()){
            //如果相同则显示成功，跳回登陆界面
            Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }else{
            //如果不相同，则失败，重新注册
            Toast.makeText(this,"两次密码输入不相同，请重新输入",Toast.LENGTH_SHORT).show();
        }
    }

    //销毁方法
    public void Destroy(){
        this.finish();
    }
}
