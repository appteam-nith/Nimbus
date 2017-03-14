package com.nith.appteam.nimbus.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.nith.appteam.nimbus.R;

public class Splash extends AppCompatActivity {
    private ImageView image_splash;
    private static  final int TIME_SPLASH=1500;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        image_splash = (ImageView) findViewById(R.id.image_splash);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
              startActivity(new Intent(Splash.this,AppIntro.class));
               finish();
            }
        },TIME_SPLASH);

    }

}
