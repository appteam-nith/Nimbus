package com.nith.appteam.nimbus.Utils;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sukhbir on 12/10/16.
 */

public class UpdateScoreModel {
    @SerializedName("success")
    private boolean success;

    @SerializedName("msg")
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
