package com.nith.appteam.nimbus.Model;

import com.google.gson.annotations.SerializedName;
import com.nith.appteam.nimbus.Fragment.ProfileTab2;

/**
 * Created by aditya on 16/2/17.
 */

public class ProfileDataModel {


    @SerializedName("success")
    private boolean success;

    @SerializedName("name")
    private String name;

    @SerializedName("roll_no")
    private String rollno;

    @SerializedName("photo")
    private String photo;

    @SerializedName("email")
    private String email;

    @SerializedName("msg")
    private String msg;

    public boolean isSuccess() {
        return success;
    }

    public String getName() {
        return name;
    }

    public String getRollno() {
        return rollno;
    }

    public String getPhoto() {
        return photo;
    }

    public String getEmail() {
        return email;
    }

    public String getMsg() {
        return msg;
    }

}
