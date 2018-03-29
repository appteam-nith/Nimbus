package com.nith.appteam.nimbus.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.nith.appteam.nimbus.Adapter.contributorsAdapter;
import com.nith.appteam.nimbus.Model.contributorsItem;
import com.nith.appteam.nimbus.R;

import java.util.ArrayList;

public class ContributorsActivity extends AppCompatActivity {

    RecyclerView rvContributors;
    contributorsAdapter ContributorAdapter;
    Toolbar tbContributers;
    ArrayList<contributorsItem> contributorsItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contributors);

        String BASE_URL = "https://github.com/";
        rvContributors = (RecyclerView) findViewById(R.id.contributors_view);

        contributorsItems = new ArrayList<>();

        //contributorsItems.add(new contributorsItem("", BASE_URL + ".png", BASE_URL + ""));

        //4th Year
        contributorsItems.add(new contributorsItem("Ashish Gurjar", BASE_URL + "akgurjar.png", BASE_URL + "akgurjar"));
        contributorsItems.add(new contributorsItem("Sahil Ramola", BASE_URL + "RamolaWeb.png", BASE_URL + "RamolaWeb"));
        contributorsItems.add(new contributorsItem("Sukhbir Singh", BASE_URL + "sukhbir-singh.png",BASE_URL + "sukhbir-singh"));
        contributorsItems.add(new contributorsItem("Jalaz Kumar", BASE_URL + "jaykay12.png", BASE_URL + "jaykay12"));
        contributorsItems.add(new contributorsItem("Narendra Dodwaria", BASE_URL + "narendra36.png", BASE_URL + "narendra36"));
        contributorsItems.add(new contributorsItem("Goutham Reddy ", BASE_URL + "zeus512.png", BASE_URL + "zeus512"));
        contributorsItems.add(new contributorsItem("Vijaya Laxmi", BASE_URL + "vijaya22.png", BASE_URL + "vijaya22"));

        //3rd Year
        contributorsItems.add(new contributorsItem("Aditya Arora", BASE_URL + "adi23arora.png",BASE_URL + "adi23arora"));
        contributorsItems.add(new contributorsItem("B Jatin Rao", BASE_URL + "Jatin0312.png", BASE_URL + "Jatin0312"));
        contributorsItems.add(new contributorsItem("Nitin Sharma", BASE_URL + "iamNitin16.png", BASE_URL + "iamNitin16"));
        contributorsItems.add(new contributorsItem("Suraj Negi", BASE_URL + "Akatsuki06.png",BASE_URL + "Akatsuki06"));
        contributorsItems.add(new contributorsItem("Parvesh Dhull", BASE_URL + "Parveshdhull.png", BASE_URL + "Parveshdhull"));

        //2nd Year
        contributorsItems.add(new contributorsItem("Bharat Shah", BASE_URL + "bharatshah1498.png",BASE_URL + "bharatshah1498"));
        contributorsItems.add(new contributorsItem("Kartik Saxena", BASE_URL + "SaxenaKartik.png",BASE_URL + "SaxenaKartik"));
        contributorsItems.add(new contributorsItem("Muskan Jhunjhunwalla", BASE_URL + "musukeshu.png",BASE_URL + "musukeshu"));
        contributorsItems.add(new contributorsItem("Anubhaw Bhalotia", BASE_URL + "anubhawbhalotia.png",BASE_URL + "anubhawbhalotia"));
        contributorsItems.add(new contributorsItem("Kaushal Kishore", BASE_URL + "kaushal16124.png",BASE_URL + "kaushal16124"));

        //1st Year
        contributorsItems.add(new contributorsItem("Utkarsh Singh", BASE_URL + "utkarshsingh99.png", BASE_URL + "utkarshsingh99"));
        contributorsItems.add(new contributorsItem("Vishal Parmar", BASE_URL + "Vishal17599.png", BASE_URL + "Vishal17599"));
        contributorsItems.add(new contributorsItem("Abhinav Lamba", BASE_URL + "Abhinavlamba.png", BASE_URL + "Abhinavlamba"));
        contributorsItems.add(new contributorsItem("Abhiraj Singh Rathore", BASE_URL + "AbhirajSinghRathore.png", BASE_URL + "AbhirajSingh99"));
        contributorsItems.add(new contributorsItem("Alisha Mehta", BASE_URL + "Alisha1116.png", BASE_URL + "Alisha1116"));
        contributorsItems.add(new contributorsItem("Anshu Akansha", BASE_URL + "AnshuAkansha52227.png", BASE_URL + "AnshuAkansha"));
        contributorsItems.add(new contributorsItem("Daniyaal Khan", BASE_URL + "drtweety.png", BASE_URL + "drtweety"));
        contributorsItems.add(new contributorsItem("Kumar Kartikay", BASE_URL + "Kartikay26.png", BASE_URL + "Kartikay26"));
        contributorsItems.add(new contributorsItem("Tanvi Mahajan", BASE_URL + "tanvi003.png", BASE_URL + "tanvi003"));


        ContributorAdapter = new contributorsAdapter(contributorsItems, ContributorsActivity.this);
        rvContributors.setAdapter(ContributorAdapter);

        tbContributers = (Toolbar) findViewById(R.id.contributors_toolbar);
        tbContributers.setTitle("Contributors");
        setSupportActionBar(tbContributers);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        rvContributors.setLayoutManager(new GridLayoutManager(this,2));

    }
}
