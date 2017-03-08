package com.nith.appteam.nimbus.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by sahil on 5/3/17.
 */

public class Gallery {

    @SerializedName("albumId")
    private String id;
    @SerializedName("title")
    private String title;

    @SerializedName("headerImage")
    private String headerImageUrl;

    @SerializedName("images")
    private ArrayList<GalleryDetail> expandedImageUrl;


    public Gallery(String id, String title, String headerImageUrl, ArrayList<GalleryDetail> expandedImageUrl) {
        this.id = id;
        this.title = title;
        this.headerImageUrl = headerImageUrl;
        this.expandedImageUrl = expandedImageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHeaderImageUrl() {
        return headerImageUrl;
    }

    public void setHeaderImageUrl(String headerImageUrl) {
        this.headerImageUrl = headerImageUrl;
    }

    public ArrayList<GalleryDetail> getExpandedImageUrl() {
        return expandedImageUrl;
    }

    public void setExpandedImageUrl(ArrayList<GalleryDetail> expandedImageUrl) {
        this.expandedImageUrl = expandedImageUrl;
    }
}
