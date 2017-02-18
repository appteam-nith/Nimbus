package com.nith.appteam.nimbus.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sahil on 9/2/17.
 */

public class Likecount {

    @SerializedName("likes")
    int likes;

    @SerializedName("success")
    boolean success;

    @SerializedName("msg")
    String msg;


    public  Likecount(int likes,boolean success, String msg)
    {
        this.likes=likes;
        this.success=success;
        this.msg=msg;
    }


    public int getLikes(){
        return likes;
    }

    public void setLikes(int like){ this.likes=like; }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg(){

        return  msg;
    }

    public void setMsg(String msg) {
        this.msg=msg;
    }


}
