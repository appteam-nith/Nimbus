package com.nith.appteam.nimbus.Activity;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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
import com.nith.appteam.nimbus.Model.EventRegisterResponse;
import com.nith.appteam.nimbus.Model.SingleWorkshopResponse;
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.ApiInterface;
import com.nith.appteam.nimbus.Utils.SharedPref;
import com.nith.appteam.nimbus.Utils.Util;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterEvent extends AppCompatActivity {
    private String name, imgUrl, description;
    private Boolean registerStatus;
    private Button registerButtton;

    private SharedPref sharedPref;

    private CoordinatorLayout coordinatorLayout;
    private CardView cardView1;
    private CardView cardView2;
    private Boolean showCompleteTiming = false;
    private Boolean showCompleteInfo = false;
    private TextView timings;
    private TextView describe;
    private TextView title;
    private TextView reg_status;
    private ImageView img;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ProgressBar progressBar;
    private LinearLayout linearLayout;
    private ProgressBar progressBar_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_event);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        sharedPref = new SharedPref(this);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.core_view);
        registerButtton = findViewById(R.id.register_button);
        cardView1 = (CardView) findViewById(R.id.cardTimings);
        cardView2 = (CardView) findViewById(R.id.desc_event);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        final LinearLayout time = (LinearLayout) findViewById(R.id.time_detail);
        final LinearLayout about = (LinearLayout) findViewById(R.id.event_description);
        timings = findViewById(R.id.date_time);
        describe = findViewById(R.id.describe);
        progressBar = (ProgressBar) findViewById(R.id.progress_single_workshop);
        img = (ImageView) findViewById(R.id.workshop_img);
        reg_status = findViewById(R.id.reg_status);
        linearLayout = findViewById(R.id.imp);
        progressBar_register = findViewById(R.id.er_progress);
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!showCompleteTiming) {
                    time.setVisibility(View.VISIBLE);
                    showCompleteTiming = true;
                } else {
                    time.setVisibility(View.GONE);
                    showCompleteTiming = false;
                }
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!showCompleteInfo) {
                    about.setVisibility(View.VISIBLE);
                    showCompleteInfo = true;
                } else {
                    about.setVisibility(View.GONE);
                    showCompleteInfo = false;
                }
            }
        });
        registerButtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sharedPref.getLoginStatus()) {
                    startActivity(new Intent(RegisterEvent.this, FirebaseLoginActivity.class));
                }
                if (!sharedPref.isDataFilled()) {
                    Log.d("gg", "hhh");
                    startActivity(new Intent(RegisterEvent.this, ProfileActivityEdit.class));
                }
                registerButtton.setVisibility(View.GONE);
                progressBar_register.setVisibility(View.VISIBLE);
                registerRetrofit();
            }
        });
        getDetails();
    }

    public void getDetails() {
        ApiInterface apiservice = Util.getRetrofitService();
        if (sharedPref.getUserId().isEmpty()) {
            progressBar.setVisibility(View.GONE);
            Snackbar.make(coordinatorLayout, "Please Login To See The Content", Snackbar.LENGTH_INDEFINITE).setAction("Login", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(RegisterEvent.this, FirebaseLoginActivity.class));
                }
            }).show();
        }
        Call<SingleWorkshopResponse> call = apiservice.getEventDetail(getIntent().getStringExtra("id"), sharedPref.getUserId());
        call.enqueue(new Callback<SingleWorkshopResponse>() {
            @Override
            public void onResponse(Call<SingleWorkshopResponse> call, Response<SingleWorkshopResponse> response) {
                if (response.isSuccess()) {
                    SingleWorkshopResponse model = response.body();
                    if (model != null) {
                        linearLayout.setVisibility(View.VISIBLE);
                        describe.setText(model.getDesc());
                        timings.setText(model.getEventDate());
                        collapsingToolbarLayout.setTitle(model.getName());
                        Glide.with(RegisterEvent.this).load(model.getImgUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into(img);
                        if (model.getRegStatus()) {
                            registerButtton.setVisibility(View.GONE);
                            reg_status.setVisibility(View.VISIBLE);
                            reg_status.setText("User Already registered");
                            progressBar.setVisibility(View.GONE);
                        } else {
                            registerButtton.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }
                    } else {
                        progressBar.setVisibility(View.GONE);
                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterEvent.this, "Error please try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SingleWorkshopResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(RegisterEvent.this, "Error please try again", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void registerRetrofit() {
        ApiInterface apiservice = Util.getRetrofitService();

        Call<EventRegisterResponse> call = apiservice.getEventRegisterResponse(getIntent().getStringExtra("id"), sharedPref.getUserId());

        call.enqueue(new Callback<EventRegisterResponse>() {
            @Override
            public void onResponse(Call<EventRegisterResponse> call, Response<EventRegisterResponse> response) {
                EventRegisterResponse model = response.body();
                if (model != null && response.isSuccess()) {


                    progressBar_register.setVisibility(View.GONE);
                    registerButtton.setVisibility(View.GONE);
                    reg_status.setVisibility(View.VISIBLE);
                    reg_status.setText(model.getMsg());
                    Snackbar.make(coordinatorLayout, model.getMsg(), Snackbar.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(RegisterEvent.this, "Success Status is not true!!", Toast.LENGTH_SHORT).show();
                }
                progressBar_register.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<EventRegisterResponse> call, Throwable t) {
                progressBar_register.setVisibility(View.GONE);
                Toast.makeText(RegisterEvent.this, "Some error occurred!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

