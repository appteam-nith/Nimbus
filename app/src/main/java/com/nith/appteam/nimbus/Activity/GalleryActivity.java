package com.nith.appteam.nimbus.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nith.appteam.nimbus.Adapter.GalleryAdapter;
import com.nith.appteam.nimbus.CustomView.GalleryView;
import com.nith.appteam.nimbus.Model.Gallery;
import com.nith.appteam.nimbus.Model.GalleryResponse;
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.Connection;
import com.nith.appteam.nimbus.Utils.RecyclerItemClickListener;
import com.nith.appteam.nimbus.Utils.Util;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GalleryActivity extends AppCompatActivity {
private RecyclerView recyclerView;
private GalleryAdapter adapter;
private ProgressBar progressBar;
private TextView errorText;
private ArrayList<Gallery> list=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Gallery");
        recyclerView= (RecyclerView) findViewById(R.id.list);
        progressBar= (ProgressBar) findViewById(R.id.progress);
        errorText= (TextView) findViewById(R.id.errorText);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new GalleryAdapter(this);
        recyclerView.setAdapter(adapter);
        if(new Connection(this).isInternet()){
           getData();
            }
        else{
            errorText.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }

    }

    private void getData(){
        Call<GalleryResponse> call= Util.getRetrofitService().getGalleryAll();
        call.enqueue(new Callback<GalleryResponse>() {
            @Override
            public void onResponse(Call<GalleryResponse> call, Response<GalleryResponse> response) {
                GalleryResponse galleryResponse=response.body();
                if(galleryResponse!=null&&response.isSuccess()){
                    recyclerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    list.addAll(galleryResponse.getGalleryList());
                    adapter.refresh(list);
                }
                else{
                    progressBar.setVisibility(View.GONE);
                    errorText.setVisibility(View.VISIBLE);
                    errorText.setText("Unable To load the Gallery. Please Try Again later ");
                }
            }

            @Override
            public void onFailure(Call<GalleryResponse> call, Throwable t) {
               t.printStackTrace();
                progressBar.setVisibility(View.GONE);
                errorText.setVisibility(View.VISIBLE);
                errorText.setText("Unable To load the Gallery. Please Try Again later ");
            }
        });
    }
}
