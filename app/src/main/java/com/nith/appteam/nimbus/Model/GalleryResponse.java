package com.nith.appteam.nimbus.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by sahil on 5/3/17.
 */

public class GalleryResponse {

    @SerializedName("albums")
    private ArrayList<Gallery> galleryList;

    public ArrayList<Gallery> getGalleryList() {
        return galleryList;
    }

    public void setGalleryList(ArrayList<Gallery> galleryList) {
        this.galleryList = galleryList;
    }

    public GalleryResponse(ArrayList<Gallery> galleryList) {
        this.galleryList = galleryList;
    }
}
