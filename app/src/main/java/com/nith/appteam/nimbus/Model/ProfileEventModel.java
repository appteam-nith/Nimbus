package com.nith.appteam.nimbus.Model;

import com.google.gson.annotations.SerializedName;
import com.nith.appteam.nimbus.Fragment.ProfileTab1;

import java.util.ArrayList;

/**
 * Created by aditya on 14/2/17.
 */

public class ProfileEventModel {

    @SerializedName("success")
    private String success;

    @SerializedName("event")
    private ArrayList<ProfileTab1.ProfileEventDataModel> event;


    public ProfileEventModel(String success, ArrayList<ProfileTab1.ProfileEventDataModel> event) {
        this.success = success;
        this.event = event;
    }


    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ArrayList<ProfileTab1.ProfileEventDataModel> getEvent() {
        return event;
    }

    public void setEvent(ArrayList<ProfileTab1.ProfileEventDataModel> event) {
        this.event = event;
    }
}
