package com.nith.appteam.nimbus.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by sahil on 9/2/17.
 */

public class NewsFeedResponse {
    @SerializedName("feed")
    private ArrayList<NewsFeedModel2> feed;
    @SerializedName("success")
    private boolean success;
    @SerializedName("msg")
    private String msg;

    public NewsFeedResponse(ArrayList<NewsFeedModel2> feed, boolean success, String msg) {
        this.feed = feed;
        this.success = success;
        this.msg = msg;
    }

    public ArrayList<NewsFeedModel2> getFeed() {
        return feed;
    }

    public void setFeed(ArrayList<NewsFeedModel2> feed) {
        this.feed = feed;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
