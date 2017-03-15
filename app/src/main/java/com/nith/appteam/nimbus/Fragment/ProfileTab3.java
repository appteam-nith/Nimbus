package com.nith.appteam.nimbus.Fragment;

import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nith.appteam.nimbus.Adapter.NewsFeedAdapter;
import com.nith.appteam.nimbus.Model.NewsFeed;
import com.nith.appteam.nimbus.Model.NewsFeedResponse;
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.SharedPref;
import com.nith.appteam.nimbus.Utils.Util;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by aditya on 13/2/17.
 */

public class ProfileTab3 extends Fragment {

    private static final String USER_POST = "post";

    private TextView nodata;
    private RecyclerView recyclerView;
    private ProgressBar progress;
    private NewsFeedAdapter adapter;
    private SharedPref sharedPref;
    private ArrayList<NewsFeed> list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_profiletab3, container, false);

        nodata = (TextView) view.findViewById(R.id.nodata);
        recyclerView = (RecyclerView) view.findViewById(R.id.news_list);
        progress = (ProgressBar) view.findViewById(R.id.progress);
        list = new ArrayList<>();

        adapter = new NewsFeedAdapter(getContext());
        recyclerView.setAdapter(adapter);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        sharedPref = new SharedPref(getContext());

        Log.d("id",sharedPref.getUserId());
        if(savedInstanceState==null){
            getData(1,sharedPref.getUserId());
            progress.setVisibility(View.VISIBLE);
        }

        else{
            if(savedInstanceState.getParcelableArray(USER_POST)!=null){
                recyclerView.setVisibility(View.VISIBLE);
                list = savedInstanceState.getParcelableArrayList(USER_POST);
                adapter.refresh(list);
            }
            else{
                nodata.setVisibility(View.VISIBLE);
                nodata.setText("No Post Uploaded");
            }

        }


        return view;
    }




    private void getData(int from, String id){

        Call<NewsFeedResponse> getUserNewsFeed = Util.getRetrofitService().getUserNews(id);
        getUserNewsFeed.enqueue(new Callback<NewsFeedResponse>() {
            @Override
            public void onResponse(Call<NewsFeedResponse> call, Response<NewsFeedResponse> response) {
                NewsFeedResponse data = response.body();


                if (data != null && response.isSuccess()) {
                    if (data.isSuccess()) {
                        if(data.getFeed()!=null){
                            list.addAll(data.getFeed());
                            if(list.size()>0){
                                recyclerView.setVisibility(View.VISIBLE);
                                progress.setVisibility(View.GONE);
                                adapter.refresh(list);
                            }
                            else {
                                recyclerView.setVisibility(View.GONE);
                                progress.setVisibility(View.GONE);
                                nodata.setVisibility(View.VISIBLE);
                                nodata.setText("No Post Uploaded");
                            }

                        }
                    }
                    else {
                        nodata.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                        progress.setVisibility(View.GONE);
                        nodata.setText(data.getMsg());

                    }
                }
                else {
                    nodata.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    progress.setVisibility(View.GONE);
                    nodata.setText("Please Check Internet Connection");

                }
            }

            @Override
            public void onFailure(Call<NewsFeedResponse> call, Throwable t) {

                recyclerView.setVisibility(View.GONE);
                progress.setVisibility(View.GONE);
                t.printStackTrace();
                nodata.setText("Please Check Internet Connection");

            }
        });


    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(USER_POST,list);
    }



}
