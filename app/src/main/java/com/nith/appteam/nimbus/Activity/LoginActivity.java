package com.nith.appteam.nimbus.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.SharedPref;

public class LoginActivity extends AppCompatActivity {

    private TextView tvSkip;
    private SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPref = new SharedPref(this);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        tvSkip = (TextView)findViewById(R.id.tvSkip);

        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPref.setSkipStatus(true);
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                finish();
            }
        });

    }
}
