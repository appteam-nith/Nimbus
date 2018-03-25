package com.nith.appteam.nimbus.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jatin on 25/3/18.
 */

public class UploadResponse {

    @SerializedName("id")
    private String id;

    @SerializedName("success")
    private boolean success;

    @SerializedName("error")
    private String error;

    public UploadResponse(String id, Boolean success, String error) {
        this.id = id;
        this.success = success;
        this.error = error;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
