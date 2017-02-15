package com.nith.appteam.nimbus.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.SharedPref;


public class MainActivity extends AppCompatActivity {
 private SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref=new SharedPref(this);
        if(!sharedPref.getSkipStatus()){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
        setContentView(R.layout.activity_main);

    }
}
