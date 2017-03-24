package com.nith.appteam.nimbus.Activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.bumptech.glide.request.target.ImageViewTarget;
import com.nith.appteam.nimbus.Adapter.CoreTeamEventAdapter;
import com.nith.appteam.nimbus.Adapter.EventAdapter;
import com.nith.appteam.nimbus.Fragment.TeamFragment;
import com.nith.appteam.nimbus.Model.CoreTeam;
import com.nith.appteam.nimbus.Model.CoreTeamEvents;
import com.nith.appteam.nimbus.Model.CoreTeamResponse;
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.Connection;
import com.nith.appteam.nimbus.Utils.Util;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created Nimbus by akatsuki on 22/3/17.
 */

public class CoreTeamEventActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {


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
    private CoreTeamEventAdapter adapter;
    private ArrayList<CoreTeamEvents.Event> eventArrayList=new ArrayList<>();
    public static final String ACTIVITY="activity", ID="id", WORKSHOP_NAME="wname", EVENT_NAME="ename";
    public static final String WORKSHOP="workshop", EVENT="event";


    private ProgressBar progressBar;
    private String id; // Team id for the Extracting team detail,event and projects

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.EventAct);
        setContentView(R.layout.activity_teamevent);
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

//        Glide.with(CoreTeamEventActivity.this).load(R.drawable.nimbuslogo).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(new ImageViewTarget<Bitmap>(logoView) {
//            @Override
//            protected void setResource(Bitmap resource) {
//                RoundedBitmapDrawable drawable= RoundedBitmapDrawableFactory.create(CoreTeamEventActivity.this.getResources(),resource);
//                drawable.setCircular(true);
//                logoView.setImageDrawable(drawable);
//            }
//        });
        Bitmap bm = decodeSampledBitmapFromResource(getResources(),R.drawable.nimbuslogo,200,200);

        RoundedBitmapDrawable drawable= RoundedBitmapDrawableFactory.create(CoreTeamEventActivity.this.getResources(),bm);
                drawable.setCircular(true);
                logoView.setImageDrawable(drawable);
        eventList= (RecyclerView) findViewById(R.id.eventList);
        eventList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true));
        adapter=new CoreTeamEventAdapter(this);
        eventList.setAdapter(adapter);




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
        Call<CoreTeamEvents> call= Util.getRetrofitService().getCoreTeamEvents(id);
        call.enqueue(new Callback<CoreTeamEvents>() {
            @Override
            public void onResponse(Call<CoreTeamEvents> call, Response<CoreTeamEvents> response) {
                CoreTeamEvents t=response.body();
                if(response.isSuccess()&&t!=null){
                    if(t.getCoreTeam().getEvents()!=null) {
                        eventArrayList.addAll(t.getCoreTeam().getEvents());
                        adapter.refresh(eventArrayList);
                    }

                     mTitle.setText(t.getCoreTeam().getName());

                    headerTitle.setText(t.getCoreTeam().getName());
                    teamDescription.setText(t.getCoreTeam().getDesc());
                    Log.e("LOGGS","************");
                    Log.e("Teamname",t.getCoreTeam().getName());
                    Log.e("Teamtitle",t.getCoreTeam().getName());
                    Glide.with(CoreTeamEventActivity.this).load(t.getCoreTeam().getBanner()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.nimbuslogo).into(bannerImage);

                    if(!t.getCoreTeam().getLogo().equals("")) {
                        Glide.with(CoreTeamEventActivity.this).load(t.getCoreTeam().getLogo()).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.nimbuslogo).into(new ImageViewTarget<Bitmap>(logoView) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(CoreTeamEventActivity.this.getResources(), resource);
                                drawable.setCircular(true);
                                logoView.setImageDrawable(drawable);
                            }
                        });
                        Log.e("CoreTEamAct","Logo not null");
                    }
                    CardView card=(CardView)findViewById(R.id.card_desc);
                    TextView eventlabel=(TextView)findViewById(R.id.eventslabel);

                    if(eventArrayList.size()>0)eventlabel.setVisibility(View.VISIBLE);
                  card.setVisibility(View.VISIBLE);

                }


                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<CoreTeamEvents> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
