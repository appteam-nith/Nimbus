package com.nith.appteam.nimbus.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sahil on 4/3/17.
 */

public class GalleryDetail {
    @SerializedName("title")
    private String title;
    @SerializedName("imageUrl")
    private String imageUrl;

    public GalleryDetail(String title, String imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
