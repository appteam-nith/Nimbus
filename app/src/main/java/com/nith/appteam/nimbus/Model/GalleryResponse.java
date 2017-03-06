package com.nith.appteam.nimbus.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by sahil on 4/3/17.
 */

public class GalleryResponse {

    @SerializedName("gallery")
    private ArrayList<Gallery> galleryArrayList;

    public GalleryResponse(ArrayList<Gallery> galleryArrayList) {
        this.galleryArrayList = galleryArrayList;
    }

    public ArrayList<Gallery> getGalleryArrayList() {
        return galleryArrayList;
    }

    public void setGalleryArrayList(ArrayList<Gallery> galleryArrayList) {
        this.galleryArrayList = galleryArrayList;
    }
}
