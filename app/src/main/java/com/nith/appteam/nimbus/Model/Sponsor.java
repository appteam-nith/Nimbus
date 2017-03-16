package com.nith.appteam.nimbus.Model;

/**
 * Created by sahil on 16/3/17.
 */

public class Sponsor {

    private String name;

    private String img_url;

    public Sponsor(String name, String img_url) {
        this.name = name;
        this.img_url = img_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
