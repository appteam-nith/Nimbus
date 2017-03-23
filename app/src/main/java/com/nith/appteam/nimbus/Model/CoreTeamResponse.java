package com.nith.appteam.nimbus.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created Nimbus by akatsuki on 22/3/17.
 */

public class CoreTeamResponse {
    @SerializedName("success")
    private boolean success;
    @SerializedName("Core_Team")
    private ArrayList<CoreTeam> coreTeams;

    public CoreTeamResponse(boolean success, ArrayList<CoreTeam> coreTeams) {
        this.success = success;
        this.coreTeams = coreTeams;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<CoreTeam> getCoreTeams() {
        return coreTeams;
    }

    public void setCoreTeams(ArrayList<CoreTeam> coreTeams) {
        this.coreTeams = coreTeams;
    }
}
