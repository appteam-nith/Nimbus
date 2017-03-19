package com.nith.appteam.nimbus.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.nith.appteam.nimbus.Model.EventRegisterResponse;
import com.nith.appteam.nimbus.Model.SingleWorkshopResponse;
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.ApiInterface;
import com.nith.appteam.nimbus.Utils.SharedPref;
import com.nith.appteam.nimbus.Utils.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkshopDetail extends AppCompatActivity {

    public static final String ACTIVITY="activity", ID="id", WORKSHOP_NAME="wname", EVENT_NAME="ename";
    public static final String WORKSHOP="workshop", EVENT="event";
    private Boolean successStatus;
    private String workshop_id, event_id, student_id, which_activity;
    private String workshop_name,event_name,msg,id;
    private RatingBar ratingBar;
    private TextView rating_text,reg_msg;
    private String ratedValue;
    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    private TextView detail_text;
    private ProgressBar progess_single_workshop;
    private String name,imgUrl,description;
    private Boolean registerStatus;
    private FloatingActionButton registerButtton;
    private LinearLayout details;
    private SharedPref sharedPref;
    private ImageView img_view;
    private CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop_detail);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        sharedPref = new SharedPref(this);
        coordinatorLayout= (CoordinatorLayout) findViewById(R.id.core_view);
        registerButtton=(FloatingActionButton) findViewById(R.id.register_button);
        reg_msg=(TextView)findViewById(R.id.reg_msg);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        rating_text=(TextView) findViewById(R.id.rating_text);
        detail_text =(TextView) findViewById(R.id.detail_text);
        details=(LinearLayout)findViewById(R.id.details);
        img_view = (ImageView) findViewById(R.id.workshop_img);

        progess_single_workshop =(ProgressBar)findViewById(R.id.progress_single_workshop);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                ratedValue = String.valueOf(ratingBar.getRating());
                //rateMessage.setText("You have rated the Product : "+ ratedValue + "/5.");
            }
        });

        registerButtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!sharedPref.getFirstTimeRollregister())
                if(!sharedPref.getNitianStatus()&&sharedPref.getUserRollno().isEmpty()){

                    if(sharedPref.getUserRollno().isEmpty()){
                        AlertDialog d=Util.promptRollNo(WorkshopDetail.this);
                        d.show();
                    }
                    else{
                        registerRetrofit();
                    }
                }
                else
                registerRetrofit();
                else{
                    sharedPref.setFirstRollRegister(true);
                    registerRetrofit();
                }
            }
        });

        student_id=sharedPref.getUserId();
        which_activity=getIntent().getStringExtra(ACTIVITY);

        if(sharedPref.getUserId().isEmpty()){
            progess_single_workshop.setVisibility(View.GONE);
            Snackbar.make(coordinatorLayout,"Please Login To See The Content",Snackbar.LENGTH_INDEFINITE).setAction("Login", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(WorkshopDetail.this,LoginActivity.class));
                }
            }).show();
        }
else {
            progess_single_workshop.setVisibility(View.VISIBLE);
            if (which_activity.equals(WORKSHOP)) {
                workshop_id = getIntent().getStringExtra(ID);
                id = workshop_id;
                workshop_name = getIntent().getStringExtra(WORKSHOP_NAME);
                collapsingToolbarLayout.setTitle(workshop_name);
                if (workshop_id == null) {
                    Toast.makeText(this, "There is no Id for workshop", Toast.LENGTH_SHORT);
                } else {
                    workshopRetrofit();
                }
            } else if (which_activity.equals(EVENT)) {
                event_id = getIntent().getStringExtra(ID);
                id = event_id;
                event_name = getIntent().getStringExtra(EVENT_NAME);
                collapsingToolbarLayout.setTitle(event_name);
                if (event_id == null) {
                    Toast.makeText(this, "There is no Id for event", Toast.LENGTH_SHORT);
                } else {
                    eventRetrofit();
                }
            }
        }

        toolbarTextAppernce();
        dynamicToolbarColor();

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

    public void workshopRetrofit(){
        ApiInterface apiservice= Util.getRetrofitService();
        Call<SingleWorkshopResponse> call=apiservice.getSingleWorkshop(workshop_id);

        call.enqueue(new Callback<SingleWorkshopResponse>() {
            @Override
            public void onResponse(Call<SingleWorkshopResponse> call, Response<SingleWorkshopResponse> response) {
                progess_single_workshop.setVisibility(View.GONE);
                details.setVisibility(View.VISIBLE);
                SingleWorkshopResponse model=response.body();
                int status=response.code();
                Log.d("reponse : ",""+response.isSuccess()+" model : "+model);
                if(model!=null && response.isSuccess()){
                    successStatus=model.getSuccessStatus();
                    if(successStatus==true){
                        name=model.getName();
                        imgUrl=model.getImgUrl();
                        description=model.getDesc();
                        registerStatus = model.getRegStatus();
                        if(!(imgUrl==null)){
                            Glide.with(WorkshopDetail.this).load(imgUrl).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.w1).into(new ImageViewTarget<Bitmap>(img_view) {
                                @Override
                                protected void setResource(Bitmap resource) {
                                    RoundedBitmapDrawable drawable= RoundedBitmapDrawableFactory.create(WorkshopDetail.this.getResources(),resource);
                                    //drawable.setCircular(true);
                                    img_view.setImageDrawable(drawable);
                                }
                            });
                        }
                        detail_text.setText(description);
                        if(registerStatus==true){
                            registerButtton.setVisibility(View.GONE);
                            reg_msg.setVisibility(View.VISIBLE);
                            reg_msg.setText("Already Registered");

                        }else{
                            registerButtton.setVisibility(View.VISIBLE);
                        }
                    }else{
                        Toast.makeText(WorkshopDetail.this,"Success Status is not true!!",Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(WorkshopDetail.this,"response model is null",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SingleWorkshopResponse> call, Throwable t) {
                progess_single_workshop.setVisibility(View.GONE);
                Toast.makeText(WorkshopDetail.this,"Some error occurred!!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void eventRetrofit(){
        ApiInterface apiservice= Util.getRetrofitService();
        Call<SingleWorkshopResponse> call=apiservice.getEventDetail(id,student_id);

        call.enqueue(new Callback<SingleWorkshopResponse>() {
            @Override
            public void onResponse(Call<SingleWorkshopResponse> call, Response<SingleWorkshopResponse> response) {
                progess_single_workshop.setVisibility(View.GONE);
                rating_text.setVisibility(View.GONE);
                ratingBar.setVisibility(View.GONE);
                SingleWorkshopResponse model=response.body();
                int status=response.code();
                Log.d("reponse : ",""+response.isSuccess()+" model : "+model);
                if(model!=null && response.isSuccess()){
                    successStatus=model.getSuccessStatus();
                    if(successStatus==true){
                        details.setVisibility(View.VISIBLE);
                        name=model.getName();
                        imgUrl=model.getImgUrl();
                        description=model.getDesc();
                        Log.d("h",description);
                        registerStatus = model.getRegStatus();
                        if(!(imgUrl==null)){
                            Glide.with(WorkshopDetail.this).load(imgUrl).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.w1).into(new ImageViewTarget<Bitmap>(img_view) {
                                @Override
                                protected void setResource(Bitmap resource) {
                                    RoundedBitmapDrawable drawable= RoundedBitmapDrawableFactory.create(WorkshopDetail.this.getResources(),resource);
                                    //drawable.setCircular(true);
                                    img_view.setImageDrawable(drawable);
                                }
                            });
                        }
                        detail_text.setText(description.replaceAll("(\\\\n\\\\n)|(\\\\n)","\n"));
                        if(registerStatus==true){
                            registerButtton.setVisibility(View.GONE);
                            reg_msg.setVisibility(View.VISIBLE);
                            reg_msg.setText("Already Registered");

                        }else{
                            registerButtton.setVisibility(View.VISIBLE);
                        }
                    }else{
                        Toast.makeText(WorkshopDetail.this,"Success Status is not true!!",Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(WorkshopDetail.this,"response model is null",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SingleWorkshopResponse> call, Throwable t) {
                progess_single_workshop.setVisibility(View.GONE);
                Toast.makeText(WorkshopDetail.this,"Some error occurred!!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void registerRetrofit(){
        ApiInterface apiservice= Util.getRetrofitService();
          Log.d("id",student_id);
        Call<EventRegisterResponse> call=apiservice.getEventRegisterResponse(id,student_id);

        call.enqueue(new Callback<EventRegisterResponse>() {
            @Override
            public void onResponse(Call<EventRegisterResponse> call, Response<EventRegisterResponse> response) {
                EventRegisterResponse model = response.body();
                if (model != null && response.isSuccess()) {
                    successStatus = model.getSuccessStatus();

                    msg = model.getMsg();
                    registerButtton.setVisibility(View.GONE);
                    reg_msg.setVisibility(View.VISIBLE);
                    reg_msg.setText(msg);
                    Snackbar.make(coordinatorLayout,msg,Snackbar.LENGTH_INDEFINITE).show();

                } else {
                    Toast.makeText(WorkshopDetail.this, "Success Status is not true!!", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<EventRegisterResponse> call, Throwable t) {
                progess_single_workshop.setVisibility(View.GONE);
                Toast.makeText(WorkshopDetail.this,"Some error occurred!!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}