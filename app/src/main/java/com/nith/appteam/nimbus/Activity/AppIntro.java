package com.nith.appteam.nimbus.Activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.SharedPref;

public class AppIntro extends AppCompatActivity {

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnskip, btnnext;
    private SharedPref prefManager;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefManager=new SharedPref(this);
        if (prefManager.showIsFirstTime()) {
            launchHomeScreen();
            finish();
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.content_appintro);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnskip = (Button) findViewById(R.id.btn_skip);
        btnnext = (Button) findViewById(R.id.btn_next);

        layouts = new int[]{
                R.layout.slide1,
                R.layout.slide2,
                R.layout.slide3,
                R.layout.slide4,

        };
        addBottomDots(0);
        changeStatusBarColor();
        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(view_pagerPageChangeListener);
        btnskip.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                launchHomeScreen();
            }
        });
        btnnext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int current = getitem(+1);
                if (current < layouts.length)

                {
                    viewPager.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                }
            }
        });
    }


    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];
        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);
        dotsLayout.removeAllViews();
        ;
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);

        }
        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getitem(int i) {
        return viewPager.getCurrentItem() + i;

    }

    private void launchHomeScreen() {
        prefManager.setIsFirstTime();
        startActivity(new Intent(AppIntro.this,MainActivity.class));
        finish();
    }

    ViewPager.OnPageChangeListener view_pagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            if (position == layouts.length - 1) {
                btnnext.setText(getString(R.string.done));
                btnskip.setVisibility(View.GONE);
            } else {
                btnnext.setText(getString(R.string.next));
                btnskip.setVisibility(View.VISIBLE);

            }
        }

        public void onPageScrollStateChanged(int arg0) {

        }
    };

    private void changeStatusBarColor() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }


    }

    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);
            return view;


        }

        public int getCount() {
            return layouts.length;
        }

        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }

    }
}
