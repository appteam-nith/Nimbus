package com.nith.appteam.nimbus.Model;

import com.google.gson.annotations.SerializedName;
import com.nith.appteam.nimbus.Model.WorkshopItem;

import java.util.ArrayList;

/**
 * Created by ndodwaria on 2/9/17.
 */

public class WorkshopListResponse {
    @SerializedName("workshops")
    ArrayList<WorkshopItem> workshops;
    @SerializedName("success")
    String success;

    public ArrayList<WorkshopItem> getWorkshops() {
        return workshops;
    }

    public void setWorkshops(ArrayList<WorkshopItem> workshops) {
        this.workshops = workshops;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String toString(){
        return success+" : "+workshops;
    }
}
