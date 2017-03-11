package com.nith.appteam.nimbus.Model;

/**
 * Created by sahil on 11/3/17.
 */

public class RegisterResponse {

private boolean success;
    private String msg;

    public RegisterResponse(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
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
