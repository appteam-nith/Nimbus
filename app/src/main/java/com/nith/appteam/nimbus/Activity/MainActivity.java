package com.nith.appteam.nimbus.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.nith.appteam.nimbus.Adapter.MainRecyclerAdapter;
import com.nith.appteam.nimbus.Adapter.SlidingImageAdapter;
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.SharedPref;

import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends AppCompatActivity {


    private SharedPref sharedPref;
    private RecyclerView mRecyclerView;
    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;


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

        //Here all the urls of the images are entered.
        String[] urls = new String[5];
        for(int i=0;i<5;i++)urls[i]="https://upload.wikimedia.org/wikipedia/commons/thumb/c/ce/HH_Polizeihauptmeister_MZ.jpg/233px-HH_Polizeihauptmeister_MZ.jpg";
        ArrayList<String> urlList = new ArrayList<>();
        Collections.addAll(urlList, urls);

        //Code to deal with the ViewPager.
        viewPager = (ViewPager)findViewById(R.id.main_view_pager);
        viewPager.setAdapter(new SlidingImageAdapter(this,urlList));
        viewPager.setClipToPadding(false);
        viewPager.setPadding(100,120,100,120);
        viewPager.setPageMargin(60);
        //Ends Here

        mRecyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(new MainRecyclerAdapter());
        bottomNavigationView= (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.action_leaderboard:
                        return true;
                    case R.id.action_profile:
                        startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                        return  true;
                    case R.id.action_notifications:
                        return true;
                }
                return false;
            }
        });
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
}