package com.nith.appteam.nimbus.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.nith.appteam.nimbus.Adapter.SponsorAdapter;
import com.nith.appteam.nimbus.Model.SponsorResponse;
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SponsorActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
     private SponsorAdapter adapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView= (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new SponsorAdapter(this);
        recyclerView.setAdapter(adapter);
        progressBar= (ProgressBar) findViewById(R.id.progress);
        getData();
    }

    private void getData(){
        Call<SponsorResponse> call= Util.getRetrofitService().getSponsorList();

        call.enqueue(new Callback<SponsorResponse>() {
            @Override
            public void onResponse(Call<SponsorResponse> call, Response<SponsorResponse> response) {
                SponsorResponse sponsorResponse=response.body();

                if(sponsorResponse!=null&&response.isSuccess()){
                    adapter.refresh(sponsorResponse.getSponsors());
                    recyclerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                    if(sponsorResponse.getSponsors().size()==0){
                        findViewById(R.id.coming_soon).setVisibility(View.VISIBLE);
                    }else{
                        findViewById(R.id.coming_soon).setVisibility(View.GONE);
                    }

                }
                else {
                    progressBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<SponsorResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

}
