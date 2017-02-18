package com.nith.appteam.nimbus.Model;

import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.Streams;

/**
 * Created by ndodwaria on 2/18/17.
 */

public class EventRegisterResponse {
    @SerializedName("success")
    Boolean successStatus;
    @SerializedName("msg")
    String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getSuccessStatus() {

        return successStatus;
    }

    public void setSuccessStatus(Boolean successStatus) {
        this.successStatus = successStatus;
    }
}
