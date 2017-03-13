package com.nith.appteam.nimbus.Activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nith.appteam.nimbus.Model.TeamListResponse;
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.Connection;
import com.nith.appteam.nimbus.Utils.ShadowTransformer;
import com.nith.appteam.nimbus.Adapter.TeamFragmentPagerAdapter;
import com.nith.appteam.nimbus.Model.TeamItem;
import com.nith.appteam.nimbus.Utils.Util;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TeamFragmentPagerAdapter adapter;
    private ProgressBar progressBar;
    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager= (ViewPager) findViewById(R.id.pager);
        progressBar= (ProgressBar) findViewById(R.id.progressbar);
        message= (TextView) findViewById(R.id.message_textView);
        getAllTeamList();
    }

    private void getAllTeamList(){
        progressBar.setVisibility(View.VISIBLE);
        if(new Connection(this).isInternet()){
            Call<TeamListResponse> call= Util.getRetrofitService().getAllTeam();
            call.enqueue(new Callback<TeamListResponse>() {
                @Override
                public void onResponse(Call<TeamListResponse> call, Response<TeamListResponse> response) {
                    if(response!=null&&response.isSuccess()){
                        ArrayList<TeamItem> list=response.body().getTeamList();
                        adapter=new TeamFragmentPagerAdapter(getSupportFragmentManager(),2f,list);
                        viewPager.setAdapter(adapter);
                        ShadowTransformer shadowTransformer=new ShadowTransformer(viewPager,adapter);
                        viewPager.setPageTransformer(false,shadowTransformer);
                        viewPager.setOffscreenPageLimit(3);
                        progressBar.setVisibility(View.GONE);
                        viewPager.setVisibility(View.VISIBLE);
                        message.setVisibility(View.GONE);
                    }
                    else {
                        message.setVisibility(View.VISIBLE);
                        message.setText("Please Check Your Internet Connection");
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<TeamListResponse> call, Throwable t) {
                    t.printStackTrace();
                    message.setVisibility(View.VISIBLE);
                    message.setText("Please Check Your Internet Connection");
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
        else{
            message.setVisibility(View.VISIBLE);
            message.setText("Please Check Your Internet Connection");
            progressBar.setVisibility(View.GONE);
        }
    }
}
