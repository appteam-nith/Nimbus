package com.nith.appteam.nimbus.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by sahil on 9/2/17.
 */

public class TeamListResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("teams")
    private ArrayList<TeamItem>  teamList;

    public TeamListResponse(boolean success, ArrayList<TeamItem> teamList) {
        this.success = success;
        this.teamList = teamList;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<TeamItem> getTeamList() {
        return teamList;
    }

    public void setTeamList(ArrayList<TeamItem> teamList) {
        this.teamList = teamList;
    }
}
