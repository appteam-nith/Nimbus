package com.nith.appteam.nimbus.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sahil on 7/2/17.
 */

public class TeamItem {

    @SerializedName("_id")
    private  String id;

    @SerializedName("name")
    private String name;

    @SerializedName("logo")
    private String url;

    public TeamItem(String id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
