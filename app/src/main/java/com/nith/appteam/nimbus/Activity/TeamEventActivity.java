package com.nith.appteam.nimbus.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.nith.appteam.nimbus.Adapter.EventAdapter;
import com.nith.appteam.nimbus.Adapter.ProjectAdapter;
import com.nith.appteam.nimbus.Fragment.TeamFragment;
import com.nith.appteam.nimbus.Model.TeamEventList;
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.Connection;
import com.nith.appteam.nimbus.Utils.RecyclerItemClickListener;
import com.nith.appteam.nimbus.Utils.Util;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.resource;

public class TeamEventActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;
    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;
    private LinearLayout mTitleContainer;
    private TextView mTitle,teamDescription,headerTitle;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    private ImageView bannerImage;
    private ImageView logoView;
    private RecyclerView eventList;
    private EventAdapter adapter;
    private RecyclerView projectList;
    private ProjectAdapter projectAdapter;
    private ArrayList<TeamEventList.Event> eventArrayList=new ArrayList<>();
    private ArrayList<TeamEventList.Projects> projectArrayList=new ArrayList<>();
    public static final String ACTIVITY="activity", ID="id", WORKSHOP_NAME="wname", EVENT_NAME="ename";
    public static final String WORKSHOP="workshop", EVENT="event";

    private ProgressBar progressBar;
    private String id; // Team id for the Extracting team detail,event and projects

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.EventAct);
        setContentView(R.layout.activity_teamevent);



//        card.setVisibility(View.GONE);
//        eventlabel.setVisibility(View.GONE);
//        projectslabel.setVisibility(View.GONE);
        progressBar=(ProgressBar)findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        Intent i = getIntent();
        if (i != null) {
            if (i.hasExtra(TeamFragment.TEAM_ID)) {
                id = i.getStringExtra(TeamFragment.TEAM_ID);
                if(new Connection(this).isInternet()){
                    getTeamData(id);
                }

            }
        }
        mAppBarLayout= (AppBarLayout) findViewById(R.id.main_appbar);
        mToolbar= (Toolbar) findViewById(R.id.main_toolbar);
        mTitle= (TextView) findViewById(R.id.main_textview_title);
        mTitleContainer= (LinearLayout) findViewById(R.id.main_linearlayout_title);
        teamDescription= (TextView) findViewById(R.id.teamDescription);
        headerTitle= (TextView) findViewById(R.id.header_title_team);
        bannerImage= (ImageView) findViewById(R.id.main_imageview_placeholder);
       logoView= (ImageView) findViewById(R.id.teamlogo);
//        Glide.with(TeamEventActivity.this).load(R.drawable.nimbuslogo).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.placeholder).into(new ImageViewTarget<Bitmap>(logoView) {
//            @Override
//            protected void setResource(Bitmap resource) {
//                RoundedBitmapDrawable drawable= RoundedBitmapDrawableFactory.create(TeamEventActivity.this.getResources(),resource);
//                drawable.setCircular(true);
//                logoView.setImageDrawable(drawable);
//            }
//        });


        eventList= (RecyclerView) findViewById(R.id.eventList);
        eventList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true));
        adapter=new EventAdapter(this);
        eventList.setAdapter(adapter);
        eventList.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i=new Intent(TeamEventActivity.this,WorkshopDetail.class);
                i.putExtra(ACTIVITY,EVENT);
                i.putExtra(EVENT_NAME,eventArrayList.get(position).getName());
                i.putExtra(ID,eventArrayList.get(position).getId());
                startActivity(i);
            }
        }));

    projectList=(RecyclerView)findViewById(R.id.projectList);
projectList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true));
        projectAdapter=new ProjectAdapter(this);
        projectList.setAdapter(projectAdapter);

        projectList.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
              //  Intent i=new Intent(TeamEventActivity.this,WorkshopDetail.class);
//                i.putExtra(ACTIVITY,EVENT);
//                i.putExtra(EVENT_NAME,eventArrayList.get(position).getName());
//                i.putExtra(ID,eventArrayList.get(position).getId());
//                startActivity(i);
            }
        }));

        mAppBarLayout.addOnOffsetChangedListener(this);
        startAlphaAnimation(mTitle, 0, View.INVISIBLE);


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

private void getTeamData(String  id){
    Call<TeamEventList> call=Util.getRetrofitService().getTeamEvents(id);
    call.enqueue(new Callback<TeamEventList>() {
        @Override
        public void onResponse(Call<TeamEventList> call, Response<TeamEventList> response) {
            TeamEventList t=response.body();
            if(response.isSuccess()&&t!=null){
                eventArrayList.addAll(t.getEvents());
                projectArrayList.addAll(t.getProjects());

                adapter.refresh(eventArrayList);
                projectAdapter.refresh(projectArrayList);
                mTitle.setText(t.getName());
                headerTitle.setText(t.getName());
                teamDescription.setText(t.getDesc());
                Log.d("g",t.getLogo());
                Glide.with(TeamEventActivity.this).load(t.getBanner()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.nimbuslogo).into(bannerImage);
                Glide.with(TeamEventActivity.this).load(t.getLogo()).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.placeholder).into(new ImageViewTarget<Bitmap>(logoView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable drawable= RoundedBitmapDrawableFactory.create(TeamEventActivity.this.getResources(),resource);
                        drawable.setCircular(true);
                        logoView.setImageDrawable(drawable);
                    }
                });
                CardView card=(CardView)findViewById(R.id.card_desc);
                TextView eventlabel=(TextView)findViewById(R.id.eventslabel);
                TextView projectslabel=(TextView)findViewById(R.id.eventslabel);


                if(projectArrayList.size()!=0)
                    projectslabel.setVisibility(View.VISIBLE);
                card.setVisibility(View.VISIBLE);
                eventlabel.setVisibility(View.VISIBLE);
            }


            progressBar.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onFailure(Call<TeamEventList> call, Throwable t) {
          t.printStackTrace();
        }
    });
}




}
