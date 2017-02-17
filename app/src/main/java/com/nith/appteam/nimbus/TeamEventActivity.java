package com.nith.appteam.nimbus;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.materialleanback.MaterialLeanBack;
import com.nith.appteam.nimbus.Fragment.TeamFragment;
import com.nith.appteam.nimbus.Model.TeamEventList;
import com.nith.appteam.nimbus.Utils.Connection;
import com.nith.appteam.nimbus.Utils.Util;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamEventActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;
    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;
    private LinearLayout mTitleContainer;
    private TextView mTitle;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    private MaterialLeanBack materialLeanBack;
    private ArrayList<Bitmap> images;
    private String id; // Team id for the Extracting team detail,event and projects


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.EventAct);
        setContentView(R.layout.activity_teamevent);
        bindActivity();
        Intent i = getIntent();
        if (i != null) {
            if (i.hasExtra(TeamFragment.TEAM_ID)) {
                id = i.getStringExtra(TeamFragment.TEAM_ID);
            }
        }

        getRespose(id);

        mAppBarLayout.addOnOffsetChangedListener(this);
        startAlphaAnimation(mTitle, 0, View.INVISIBLE);
        addImages();
        materialLeanBack.setCustomizer(new MaterialLeanBack.Customizer() {
            @Override
            public void customizeTitle(TextView textView) {
                textView.setTypeface(null, Typeface.BOLD);
            }
        });

        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialLeanBack.smoothScrollTo(5, 6);
            }
        });
        materialLeanBack.setAdapter(new MaterialLeanBack.Adapter<EventViewHolder>() {
            @Override
            public int getLineCount() {
                return 3;
            }

            @Override
            public int getCellsCount(int line) {
                return 10;
            }

            @Override
            public EventViewHolder onCreateViewHolder(ViewGroup viewGroup, int line) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_event, viewGroup, false);
                return new EventViewHolder(view);
            }

            @Override
            public void onBindViewHolder(EventViewHolder viewHolder, int i) {
                viewHolder.textView.setText("Pic" + i);
                viewHolder.imageView.setImageBitmap(images.get(i % 6));
            }

            @Override
            public String getTitleForRow(int row) {
                return "Event " + row;
            }

            //region customView
            @Override
            public RecyclerView.ViewHolder getCustomViewForRow(ViewGroup viewgroup, int row) {
                if (row == 0) {
                    View view = LayoutInflater.from(viewgroup.getContext()).inflate(R.layout.view_teamdetails, viewgroup, false);
                    return new RecyclerView.ViewHolder(view) {
                    };
                } else
                    return null;
            }

            @Override
            public boolean isCustomView(int row) {
                return row == 0;
            }

            @Override
            public boolean hasRowTitle(int row) {
                return row != 6;
            }


            //
            @Override
            public void onBindCustomView(RecyclerView.ViewHolder viewHolder, int row) {
                super.onBindCustomView(viewHolder, row);
            }

            //endregion

        });
// TODO: 9/2/17 set on click listener to the event activity
        materialLeanBack.setOnItemClickListener(new MaterialLeanBack.OnItemClickListener() {
            @Override
            public void onTitleClicked(int row, String text) {
                Toast.makeText(getApplicationContext(), "onTitleClicked " + row + " " + text, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemClicked(int row, int column) {
                Toast.makeText(getApplicationContext(), "onItemClicked " + row + " " + column, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void bindActivity() {
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mTitle = (TextView) findViewById(R.id.main_textview_title);
        mTitleContainer = (LinearLayout) findViewById(R.id.main_linearlayout_title);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.main_appbar);
        materialLeanBack = (MaterialLeanBack) findViewById(R.id.materialLeanBack);
    }

    private void addImages() {
        images = new ArrayList<>();
        images.add(BitmapFactory.decodeResource(getResources(), R.drawable.forest_2));
        images.add(BitmapFactory.decodeResource(getResources(), R.drawable.forest_1));
        images.add(BitmapFactory.decodeResource(getResources(), R.drawable.forest_3));
        images.add(BitmapFactory.decodeResource(getResources(), R.drawable.forest_4));
        images.add(BitmapFactory.decodeResource(getResources(), R.drawable.forest_5));
        images.add(BitmapFactory.decodeResource(getResources(), R.drawable.forest_6));
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;


        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if (!mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;

                mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
                mToolbar.setBackgroundColor(Color.alpha(0));
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }




    void getRespose(String id) {
        // TODO: 14/2/17   add progress bar to the act

        // progressBar.setVisibility(View.VISIBLE);
        if (new Connection(this).isInternet()) {
            Call<TeamEventList> call = Util.getRetrofitService().getTeamEvents(id);
           call.enqueue(new Callback<TeamEventList>() {
               @Override
               public void onResponse(Call<TeamEventList> call, Response<TeamEventList> response) {

                   if(!response.isSuccess()){Log.v("TESTTEAMEVENTRESPONSE","NOT Working!");return;}
                   TeamEventList teamEvent=response.body();

                   if(!teamEvent.isSucess()){Log.v("TESTTEAMEVENTRESPONSE","NOT Working!");return;}

                 



               }

               @Override
               public void onFailure(Call<TeamEventList> call, Throwable t) {
                   Toast.makeText(getApplicationContext(), "Please check your network connection and internet permission", Toast.LENGTH_SHORT).show();
                   //progressBar.setVisibility(View.GONE);
               }
           });

        } else {
//            message.setVisibility(View.VISIBLE);
//            message.setText("Please Check Your Internet Connection");
//            progressBar.setVisibility(View.GONE);
        }

    }
}
