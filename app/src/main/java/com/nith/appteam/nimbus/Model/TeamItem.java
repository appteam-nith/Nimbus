package com.nith.appteam.nimbus.Model;

/**
 * Created by sahil on 7/2/17.
 */

public class TeamItem {

    private String name;

    private String url;

    public TeamItem(String name, String url) {
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
}
