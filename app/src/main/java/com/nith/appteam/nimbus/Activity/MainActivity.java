package com.nith.appteam.nimbus.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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

        Log.v("Checking UserId:",""+sharedPref.getUserId());


    }


    public void openProfile(View view){

        startActivity(new Intent(this,ProfileActivity.class));

    }

    public void openTeam(View view){
        startActivity(new Intent(this,TeamActivity.class));
    }


}
