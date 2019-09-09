package com.naw.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;



import com.naw.tuoxie_android.R;

public class LoginActivity extends Activity {
    private Button login_bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_bt=(Button)findViewById(R.id.login_bt);
        init();
    }

    public void init(){

    }
}
