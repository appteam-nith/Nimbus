package com.nith.appteam.nimbus.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nith.appteam.nimbus.R;


public class SlidingImageDetailActivity extends AppCompatActivity {
    private ImageView galleryImage;
    private ProgressBar progressBar;
    private String urlImage;
    private TextView noneText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getSupportActionBar()!=null;

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));

        setContentView(R.layout.activity_sliding_image_detail);
        galleryImage = (ImageView)findViewById(R.id.galleryImage);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        noneText = (TextView)findViewById(R.id.none_text);
        if(getIntent().getStringExtra(Intent.EXTRA_TEXT)!=null){
            urlImage = getIntent().getStringExtra(Intent.EXTRA_TEXT);
            Log.d(getClass().getSimpleName(),urlImage);
            Glide.with(this).load(urlImage).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    progressBar.setVisibility(View.GONE);
                    noneText.setVisibility(View.VISIBLE);
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    progressBar.setVisibility(View.GONE);
                    return false;
                }
            }).diskCacheStrategy(DiskCacheStrategy.ALL).into(galleryImage);
        }

        if(getIntent().getExtras().getInt(Intent.EXTRA_TEXT)==0){
            progressBar.setVisibility(View.GONE);
            noneText.setVisibility(View.GONE);
            galleryImage.setImageResource(R.drawable.logo1);
        }

        if(getIntent().getExtras().getInt(Intent.EXTRA_TEXT)==1){
            progressBar.setVisibility(View.GONE);
            noneText.setVisibility(View.GONE);
            galleryImage.setImageResource(R.drawable.slideimage2);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
