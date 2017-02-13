package com.nith.appteam.nimbus.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nith.appteam.nimbus.Model.SingleWorkshopResponse;
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.ApiInterface;
import com.nith.appteam.nimbus.Utils.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkshopDetail extends AppCompatActivity {

    private String workshop_id;
    private RatingBar ratingBar;
    private TextView rateMessage;
    private String ratedValue;
    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    private TextView detail_text;
    private ProgressBar progess_single_workshop;
    private String name,imgUrl,description;
    private Boolean registerStatus;
    private Button registerButtton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop_detail);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.user_name));

        dynamicToolbarColor();
        toolbarTextAppernce();

        registerButtton=(Button)findViewById(R.id.register_button);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        detail_text =(TextView) findViewById(R.id.detail_text);
        progess_single_workshop =(ProgressBar)findViewById(R.id.progress_single_workshop);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                ratedValue = String.valueOf(ratingBar.getRating());
                //rateMessage.setText("You have rated the Product : "+ ratedValue + "/5.");
            }
        });
        workshop_id=getIntent().getStringExtra("id");
        // retrofit.................
        progess_single_workshop.setVisibility(View.VISIBLE);
        if(workshop_id==null){
            Toast.makeText(this,"There is no Id for workshop",Toast.LENGTH_SHORT);
        }
        else {
            retrofit();
        }
    }

    private void dynamicToolbarColor() {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.w1);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {

            @Override
            public void onGenerated(Palette palette) {
                collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(getResources().getColor(R.color.colorPrimary)));
                collapsingToolbarLayout.setStatusBarScrimColor(palette.getMutedColor(getResources().getColor(R.color.colorPrimaryDark)));
            }
        });
    }

    private void toolbarTextAppernce() {
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
    }

    public void retrofit(){
        ApiInterface apiservice= Util.getRetrofitService();
        Call<SingleWorkshopResponse> call=apiservice.getSingleWorkshop("hello");

        call.enqueue(new Callback<SingleWorkshopResponse>() {
            @Override
            public void onResponse(Call<SingleWorkshopResponse> call, Response<SingleWorkshopResponse> response) {
                progess_single_workshop.setVisibility(View.GONE);

                SingleWorkshopResponse model=response.body();
                int status=response.code();

                if(model!=null && response.isSuccess()){
                    detail_text.setVisibility(View.VISIBLE);

                    name=model.getName();
                    imgUrl=model.getImgUrl();
                    description=model.getDesc();
                    registerStatus = model.getRegStatus();
                    detail_text.setText(description);
                    if(registerStatus==true){
                        registerButtton.setVisibility(View.GONE);
                    }
                    else{
                        registerButtton.setVisibility(View.VISIBLE);
                    }

                }else{
                    Toast.makeText(WorkshopDetail.this,"Some error occurred!!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SingleWorkshopResponse> call, Throwable t) {
                progess_single_workshop.setVisibility(View.GONE);
                Toast.makeText(WorkshopDetail.this,"Some error occurred!!",Toast.LENGTH_SHORT).show();
            }
        });
    }

}