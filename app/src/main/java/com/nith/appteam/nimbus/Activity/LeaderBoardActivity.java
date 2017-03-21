package com.nith.appteam.nimbus.Activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.annotations.SerializedName;
import com.nith.appteam.nimbus.Adapter.LeaderBoardAdapter;
import com.nith.appteam.nimbus.Model.LeaderBoardModel;
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.ApiInterface;
import com.nith.appteam.nimbus.Utils.Util;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaderBoardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<LeaderBoardUserModel> users;
    private ProgressBar progressBar;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_leaderboard);

        progressBar = (ProgressBar) findViewById(R.id.leader_progress);
        recyclerView = (RecyclerView) findViewById(R.id.leader_recycler);
        toolbar = (Toolbar) findViewById(R.id.leader_toolbar);

        toolbar.setTitle("LeaderBoard");
        toolbar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar.setVisibility(View.VISIBLE);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        getLeaderBoard();

    }

    public void getLeaderBoard() {
        ApiInterface mAPI = Util.getRetrofitService();
        Call<LeaderBoardModel> mService = mAPI.getLeaderBoard();
        mService.enqueue(new Callback<LeaderBoardModel>() {
            @Override
            public void onResponse(Call<LeaderBoardModel> call, Response<LeaderBoardModel> response) {
                if(response!=null&&response.isSuccess()){
                    users = response.body().getUsers();
                    if(users.size()>0)
                    adapter = new LeaderBoardAdapter(users, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                }
                else {
                    progressBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<LeaderBoardModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Please check your network connection and internet permission", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });


    }


    public class LeaderBoardUserModel {

        @SerializedName("name")
        private String name;

        @SerializedName("photo")
        private String photo;

        @SerializedName("roll_no")
        private String rollNo;

        @SerializedName("quiz")
        private Quiz sets;

        public LeaderBoardUserModel(String name, String photo, String rollNo, Quiz sets) {
            this.photo=photo;
            this.name = name;
            this.rollNo = rollNo;
            this.sets = sets;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRollNo() {
            return rollNo;
        }

        public void setRollNo(String rollNo) {
            this.rollNo = rollNo;
        }

        public Quiz getSets() {
            return sets;
        }

        public void setSets(Quiz sets) {
            this.sets = sets;
        }

    }

    public class Quiz{

        @SerializedName("score")
        private int score;
        @SerializedName("sets")
        private int sets;

        public Quiz(int score, int sets) {
            this.score = score;
            this.sets = sets;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getSets() {
            return sets;
        }

        public void setSets(int sets) {
            this.sets = sets;
        }
    }
}
