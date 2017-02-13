package com.nith.appteam.nimbus.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ndodwaria on 2/9/17.
 */

public class SingleWorkshopResponse {
    @SerializedName("name")
    String name;
    @SerializedName("photo")
    String imgUrl;
    @SerializedName("desc")
    String desc;
    @SerializedName("register_status")
    Boolean regStatus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Boolean getRegStatus() {
        return regStatus;
    }

    public void setRegStatus(Boolean regStatus) {
        this.regStatus = regStatus;
    }
}
