package com.naw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.naw.login.LoginActivity;
import com.naw.tuoxie_android.R;

import javax.security.auth.Destroyable;

public class register extends AppCompatActivity {

    //设置register中Button名字
    private Button register;
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //给声明的Button赋值
        register = findViewById(R.id.register_bt);
        cancel = findViewById(R.id.cancel_bt);
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
        //判断输入密码与再次输入密码是否相同
        if(R.id.password_text==R.id.confirmpassword_text){
            //如果相同则显示成功，跳回登陆界面
            Toast.makeText(register.this,"注册成功",Toast.LENGTH_SHORT).show();
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
