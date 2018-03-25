package com.nith.appteam.nimbus.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.nith.appteam.nimbus.Adapter.SponsorAdapter;
import com.nith.appteam.nimbus.R;

public class SponsorActivityTry extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SponsorAdapter adapter;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor_try);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        recyclerView= (RecyclerView) findViewById(R.id.list);
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//        adapter=new SponsorAdapter(this);
//        recyclerView.setAdapter(adapter);
//        progressBar= (ProgressBar) findViewById(R.id.progress);
    }
}
