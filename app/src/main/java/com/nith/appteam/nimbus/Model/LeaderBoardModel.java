package com.nith.appteam.nimbus.Model;

import com.google.gson.annotations.SerializedName;
import com.nith.appteam.nimbus.Activity.LeaderBoardActivity;

import java.util.ArrayList;

/**
 * Created by jaykay12 on 17/2/17.
 */
public class LeaderBoardModel {

    @SerializedName("scoreboard")
    private ArrayList<LeaderBoardActivity.LeaderBoardUserModel> users;

    public LeaderBoardModel(ArrayList<LeaderBoardActivity.LeaderBoardUserModel> users) {
        this.users = users;
    }

    public ArrayList<LeaderBoardActivity.LeaderBoardUserModel> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<LeaderBoardActivity.LeaderBoardUserModel> users) {
        this.users = users;
    }
}
