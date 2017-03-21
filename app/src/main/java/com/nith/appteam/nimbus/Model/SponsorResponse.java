package com.nith.appteam.nimbus.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by sahil on 16/3/17.
 */

public class SponsorResponse {

    @SerializedName("sponsors")
    private ArrayList<Sponsor> sponsors;


   @SerializedName("success")
    private boolean success;

    public SponsorResponse(ArrayList<Sponsor> sponsors, boolean success) {
        this.sponsors = sponsors;
        this.success = success;
    }

    public ArrayList<Sponsor> getSponsors() {
        return sponsors;
    }

    public void setSponsors(ArrayList<Sponsor> sponsors) {
        this.sponsors = sponsors;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
