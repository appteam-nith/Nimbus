package com.nith.appteam.nimbus.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.text.TextDirectionHeuristicCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nith.appteam.nimbus.Adapter.NewsFeedAdapter;
import com.nith.appteam.nimbus.Model.NewsFeedResponse;
import com.nith.appteam.nimbus.Model.NewsFeed;
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.SharedPref;
import com.nith.appteam.nimbus.Utils.Util;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sahil on 9/2/17.
 */

public class NewsFeedActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private static final String FEED_LIST ="list" ;
    private RecyclerView recyclerView;
    private NewsFeedAdapter adapter;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean loading = true;
    private int  pastVisiblesItems, visibleItemCount, totalItemCount, previousTotal = 0, visibleThreshold = 0,feedNo=1;
    private ArrayList<NewsFeed> list=new ArrayList<>();
    private SharedPref pref;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        pref= new SharedPref(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.newsfeed);
        coordinatorLayout= (CoordinatorLayout) findViewById(R.id.core_view);
        swipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        progressBar= (ProgressBar) findViewById(R.id.progress);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        adapter = new NewsFeedAdapter(this);

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if (totalItemCount > previousTotal) {
                            loading = false;
                            previousTotal = totalItemCount;
                        }
                    }
                    if (!loading && (totalItemCount - visibleItemCount)
                            <= (pastVisiblesItems + visibleThreshold)) {
                        adapter.notifyItemInserted(list.size() + 1);
                        feedNo+=10;
                        showData(feedNo);
                        loading = true;
                    }
                }
            }
        });


        if(savedInstanceState==null){
            if(pref.getUserId().isEmpty()){
                progressBar.setVisibility(View.GONE);
                Snackbar.make(coordinatorLayout,"Please Login To See The Content",Snackbar.LENGTH_INDEFINITE).setAction("Login", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(NewsFeedActivity.this,LoginActivity.class));
                    }
                }).show();
            }
            else
            showData(1);}
        else {

            list=savedInstanceState.getParcelableArrayList(FEED_LIST);
            if(list!=null){
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                adapter.refresh(list);
            }
            else {
                showData(1);
            }
        }

        // Button to upload the NewsFeed

//        FloatingActionButton upload= (FloatingActionButton) findViewById(R.id.upload_btn);
//        upload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(NewsFeedActivity.this,UploadNewsFeedActivity.class));
//            }
//        });

    }

    private void  showData(int from){

        if(from==1) {
            list.clear();
            adapter.refresh(list);
            recyclerView.setVisibility(View.GONE);
        }

        Call<NewsFeedResponse> newsfeedResponse= Util.getRetrofitService().getAllNews(pref.getUserId(),from);
        newsfeedResponse.enqueue(new Callback<NewsFeedResponse>() {
            @Override
            public void onResponse(Call<NewsFeedResponse> call, Response<NewsFeedResponse> response) {
                if(swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                if(response!=null&&response.isSuccess()){
                    if(response.body().getFeed()!=null){
                        if(response.body().getFeed().size()>0){

                            if(list.size()!=0){
                                list.remove(list.size()-1);
                                adapter.refresh (list);
                            }

                            list.addAll(response.body().getFeed());
                            list.add(null);

                            adapter.refresh(list);


                        }

                        Log.d("rr","check");

                    }
                    else {
                        Log.d("sa","check");

                        if(list.size()!=0){
                            list.remove(list.size()-1);
                            adapter.refresh(list);
                        }

                    }

                }
                else {

                    if(list.size()!=0){
                        list.remove(list.size()-1);
                        adapter.refresh(list);
                    }


                    Snackbar.make(coordinatorLayout,"Unable To load Data",Snackbar.LENGTH_INDEFINITE).setAction("Reload", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            progressBar.setVisibility(View.VISIBLE);
                            showData(1);
                        }
                    }).show();
                }
            }

            @Override
            public void onFailure(Call<NewsFeedResponse> call, Throwable t) {
                if(swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }

                if(list.size()!=0){
                    list.remove(list.size()-1);
                    adapter.refresh(list);
                }

                t.printStackTrace();
                progressBar.setVisibility(View.GONE);
                Toast.makeText(NewsFeedActivity.this, "Please check your network connection and internet permission", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onRefresh()
    {
        showData(1);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(list!=null)
            outState.putParcelableArrayList(FEED_LIST,list);
    }
}