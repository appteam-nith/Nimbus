package com.nith.appteam.nimbus.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.nith.appteam.nimbus.R;

public class WallIntroActivity extends AppCompatActivity {


    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall_intro);

        toolbar = (Toolbar) findViewById(R.id.toolbar_wall);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void viewFeed(View view){

        startActivity(new Intent(WallIntroActivity.this,NewsFeedActivity.class));

    }

    public void uploadFeed(View v){

        startActivity(new Intent(WallIntroActivity.this, UploadNewsFeedActivity.class));

    }





}
