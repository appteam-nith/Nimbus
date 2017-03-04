package com.nith.appteam.nimbus.Activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.nith.appteam.nimbus.Adapter.GalleryAdapter;
import com.nith.appteam.nimbus.Model.Gallery;
import com.nith.appteam.nimbus.Model.GalleryResponse;
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.Connection;
import com.nith.appteam.nimbus.Utils.RotateDownPageTransformer;
import com.nith.appteam.nimbus.Utils.Util;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GalleryActivity extends AppCompatActivity {
private ViewPager pager;
private GalleryAdapter adapter;
private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_gallery);
        pager= (ViewPager) findViewById(R.id.galleryViewPager);
        progressBar= (ProgressBar) findViewById(R.id.progressBar);
        adapter=new GalleryAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setPageTransformer(true,new RotateDownPageTransformer());
        if(new Connection(this).isInternet()){
            getGalleryData(0);
        }


    }

    private void getGalleryData(int id){
        Call<GalleryResponse> call= Util.getRetrofitService().getGalleryResponse(id);
        call.enqueue(new Callback<GalleryResponse>() {
            @Override
            public void onResponse(Call<GalleryResponse> call, Response<GalleryResponse> response) {
                GalleryResponse b=response.body();
                if(response.isSuccess()&&b!=null){
                    ArrayList<Gallery> list=b.getGalleryArrayList();
                    adapter.refresh(list);
                    progressBar.setVisibility(View.GONE);
                    pager.setVisibility(View.VISIBLE);

                }
                else{
                    progressBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<GalleryResponse> call, Throwable t) {
                t.printStackTrace();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
