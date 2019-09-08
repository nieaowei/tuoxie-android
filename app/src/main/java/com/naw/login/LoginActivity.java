package com.naw.login;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.naw.tuoxie_android.R;

public class LoginActivity extends Activity {
    private Button login_bt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_bt=(Button)findViewById(R.id.login_bt);
    }

    protected void init(){

    }
}
