package com.nith.appteam.nimbus.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.ApiInterface;
import com.nith.appteam.nimbus.Utils.Connection;
import com.nith.appteam.nimbus.Utils.Util;
import com.nith.appteam.nimbus.Model.WorkshopItem;
import com.nith.appteam.nimbus.Model.WorkshopListResponse;
import com.nith.appteam.nimbus.Adapter.WorkshopsAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Workshops extends AppCompatActivity {
    private ProgressBar bar;
    RecyclerView workshopsRv;
    WorkshopsAdapter workshopsAdapter;
    Toolbar workshopstb;
    ArrayList<WorkshopItem> workshop_item;
    private int i=0;
    private TextView errorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshops);
         workshopsRv = (RecyclerView) findViewById(R.id.workshops_view);
        workshopsAdapter = new WorkshopsAdapter(Workshops.this);
        workshopsRv.setAdapter(workshopsAdapter);
        bar=(ProgressBar)findViewById(R.id.progress);
        errorView= (TextView) findViewById(R.id.errorView);
        if(new Connection(this).isInternet()){
            retrofit();
        }
        else {
            errorView.setVisibility(View.VISIBLE);
            errorView.setText("Please Check Your Internet Connection");
            bar.setVisibility(View.GONE);
        }

        workshopstb = (Toolbar) findViewById(R.id.workshops_toolbar);
        workshopstb.setTitle("Special Events");
     setSupportActionBar(workshopstb);
     getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    public void retrofit(){

        ApiInterface apiservice= Util.getRetrofitService();
        Call<WorkshopListResponse> call=apiservice.getAllWorkshop();

        call.enqueue(new Callback<WorkshopListResponse>() {
            @Override
            public void onResponse(Call<WorkshopListResponse> call, Response<WorkshopListResponse> response) {
                bar.setVisibility(View.GONE);

                WorkshopListResponse model=response.body();


                if(model!=null && response.isSuccess()){
                    workshopsRv.setVisibility(View.VISIBLE);


                    ArrayList<WorkshopItem> workshop_item=model.getWorkshops();

                    if(workshop_item!=null){
                        Log.v("Size response ",workshop_item.size()+"");

                        if(workshop_item.size()==0){
                            findViewById(R.id.coming_soon).setVisibility(View.VISIBLE);
                        }else{
                            findViewById(R.id.coming_soon).setVisibility(View.GONE);
                        }

                        LinearLayoutManager lvmanager = new LinearLayoutManager(Workshops.this);
                        lvmanager.setOrientation(LinearLayoutManager.VERTICAL);
                        workshopsRv.setLayoutManager(lvmanager);

                        workshopsAdapter.refresh(workshop_item);
                    }
                    else{

                        Log.v("Size response ","dfdf");
                        errorView.setText("Sorry No Workshop Available Right Now");
                    }

                }else{
                    Log.v("Size response ","error");
                    errorView.setText("Error Occur");
                }
            }

            @Override
            public void onFailure(Call<WorkshopListResponse> call, Throwable t) {
                bar.setVisibility(View.GONE);
                errorView.setText("Error Occur");
                Log.v("Size response ","failure");
            }
        });
    }
}
