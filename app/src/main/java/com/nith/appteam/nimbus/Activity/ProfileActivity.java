package com.nith.appteam.nimbus.Activity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.nith.appteam.nimbus.Adapter.ProfilePagerAdapter;
import com.nith.appteam.nimbus.Fragment.ProfileTab1;
import com.nith.appteam.nimbus.Fragment.ProfileTab2;
import com.nith.appteam.nimbus.Fragment.ProfileTab3;
import com.nith.appteam.nimbus.R;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {


    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView coverImage;
    ImageView profilePic;


    private void findViews(){

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        coverImage = (ImageView) findViewById(R.id.cover);
        profilePic = (ImageView) findViewById(R.id.profilePic);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        findViews();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout.setTitle("Aditya Arora");
        toolbar.setTitle("Aditya Arora");


        Resources res = getResources();
        Bitmap src = BitmapFactory.decodeResource(res, R.drawable.dummy);
//        RoundedBitmapDrawable dr =
//                RoundedBitmapDrawableFactory.create(res, src);
//        dr.setCornerRadius(Math.max(src.getWidth(), src.getHeight()) / 2.0f);
//        profilePic.setImageDrawable(dr);
        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getResources(),src);
        drawable.setCircular(true);
        profilePic.setImageDrawable(drawable);


//        profilePic.setImageResource(R.drawable.dummy);
//        coverImage.setImageResource(R.drawable.cover);


        ArrayList<Fragment> fragmentArrayList=new ArrayList<>();
        fragmentArrayList.add(new ProfileTab1());
        fragmentArrayList.add(new ProfileTab2());
        fragmentArrayList.add(new ProfileTab3());


        ArrayList<String> titleArrayList=new ArrayList<>();
        titleArrayList.add("Events");
        titleArrayList.add("Basic info");
        titleArrayList.add("News Feed");

        //Start of Tab Layout in Profile Activity

        ProfilePagerAdapter adapter = new ProfilePagerAdapter(getSupportFragmentManager(),fragmentArrayList,titleArrayList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(1).select();
        viewPager.setOffscreenPageLimit(3);


    }
}
