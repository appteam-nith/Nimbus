package com.nith.appteam.nimbus.Activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.ShadowTransformer;
import com.nith.appteam.nimbus.Adapter.TeamFragmentPagerAdapter;
import com.nith.appteam.nimbus.Model.TeamItem;

import java.util.ArrayList;

public class TeamActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TeamFragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager= (ViewPager) findViewById(R.id.pager);
        ArrayList<TeamItem> list=new ArrayList<>();
        list.add(new TeamItem("C-Helix","https://s3-ap-southeast-1.amazonaws.com/nimbus2k16/teams/chelix.png"));
        list.add(new TeamItem(".EXE","https://s3-ap-southeast-1.amazonaws.com/nimbus2k16/teams/exe.png"));
        list.add(new TeamItem("Hermatica","https://s3-ap-southeast-1.amazonaws.com/nimbus2k16/teams/hermatica.png"));
        list.add(new TeamItem("C-Helix","https://s3-ap-southeast-1.amazonaws.com/nimbus2k16/teams/chelix.png"));
        adapter=new TeamFragmentPagerAdapter(getSupportFragmentManager(),2f,list);
        viewPager.setAdapter(adapter);
        ShadowTransformer shadowTransformer=new ShadowTransformer(viewPager,adapter);
        viewPager.setPageTransformer(false,shadowTransformer);
        viewPager.setOffscreenPageLimit(3);

    }

}
