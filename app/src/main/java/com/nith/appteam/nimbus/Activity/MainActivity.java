package com.nith.appteam.nimbus.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CollapsingToolbarLayout;

import android.support.v4.view.ViewPager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.nith.appteam.nimbus.Adapter.MainRecyclerAdapter;
import com.nith.appteam.nimbus.Adapter.SlidingImageAdapter;
import com.nith.appteam.nimbus.Model.MainPagerResponse;
import com.nith.appteam.nimbus.Model.ProfileDataModel;
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.ApiInterface;
import com.nith.appteam.nimbus.Utils.Connection;
import com.nith.appteam.nimbus.Utils.RecyclerItemClickListener;
import com.nith.appteam.nimbus.Utils.SharedPref;
import com.nith.appteam.nimbus.Utils.Util;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {


    private SharedPref sharedPref;
    private RecyclerView mRecyclerView;
    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;
    private SlidingImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = new SharedPref(this);
        if (!sharedPref.getSkipStatus()) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
        setContentView(R.layout.activity_main);

        initCollapsingToolbar();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.v("Checking UserId:",""+sharedPref.getUserId());



        //Code to deal with the ViewPager.
        viewPager = (ViewPager)findViewById(R.id.main_view_pager);
        imageAdapter=new SlidingImageAdapter(MainActivity.this);
        if(new Connection(this).isInternet()){
            getPagerData();
            profileBasicInfo(sharedPref.getUserId());
        }

        viewPager.setAdapter(imageAdapter);
        viewPager.setClipToPadding(false);
        viewPager.setPadding(100,120,100,120);
        viewPager.setPageMargin(60);
        //Ends Here

        //Handling the Recycler View
        mRecyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(new MainRecyclerAdapter());
        //Ends Here

        bottomNavigationView= (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.action_leaderboard:
                        startActivity(new Intent(MainActivity.this,LeaderBoardActivity.class));
                        return true;
                    case R.id.action_profile:
                        startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                        return  true;
                    case R.id.action_notifications:
                        startActivity(new Intent(MainActivity.this,MapActivity.class));
                        return true;
                }
                return false;
            }
        });

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position){
                    case 0:
                        startActivity(new Intent(MainActivity.this,QuizActivity.class));
                        break;
                    case 1:
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this,CoreTeamActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this,TeamActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(MainActivity.this,MapActivity.class));
                        break;
                    case 5:
                        break;
                    case 6:
                        startActivity(new Intent(MainActivity.this,Workshops.class));

                }
            }
        }));
    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void getPagerData(){
        Call<MainPagerResponse> response= Util.getRetrofitService().getMainResponse();
        response.enqueue(new Callback<MainPagerResponse>() {
            @Override
            public void onResponse(Call<MainPagerResponse> call, Response<MainPagerResponse> response) {
                MainPagerResponse mainPagerResponse=response.body();
                if(response!=null&&response.isSuccess()){
                    ArrayList<String> list=mainPagerResponse.getImageList();
                    imageAdapter.refresh(list);
                }
            }

            @Override
            public void onFailure(Call<MainPagerResponse> call, Throwable t) {

            }
        });
    }

    private void profileBasicInfo(String id){

        ApiInterface mAPI = Util.getRetrofitService();
        Call<ProfileDataModel> mService = mAPI.profileBasicInfo(id);

        mService.enqueue(new Callback<ProfileDataModel>() {
            @Override
            public void onResponse(Call<ProfileDataModel> call, Response<ProfileDataModel> response) {
                if(response!=null && response.isSuccess()){
                    if(response.body().isSuccess()){
                        ProfileDataModel model = response.body();
                        //For Testing
                        Log.v("RESPONSE SUCCESS"," "+model.getRollno()+" "+model.getName()+" "+model.getEmail()+ " "+model.getPhoto());

                        if(model!=null){

                            sharedPref.setUserName(model.getName());
                            sharedPref.setUserEmail(model.getEmail());
                            sharedPref.setUserRollno(model.getRollno());
                            sharedPref.setUserPicUrl(model.getPhoto());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileDataModel> call, Throwable t) {
                t.printStackTrace();

            }
        });


    }

}