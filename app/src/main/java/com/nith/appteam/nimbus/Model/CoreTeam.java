package com.nith.appteam.nimbus.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created Nimbus by akatsuki on 22/3/17.
 */

public class CoreTeam {

    @SerializedName("_id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("logo")
    private String logo;
    @SerializedName("banner")
    private String banner;


    public CoreTeam(String id, String name, String logo, String banner) {
        this.id = id;
        this.name = name;
        this.logo = logo;
        this.banner = banner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }
}
