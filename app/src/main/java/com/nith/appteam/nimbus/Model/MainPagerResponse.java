package com.nith.appteam.nimbus.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by shasha on 19/2/17.
 */

public class MainPagerResponse implements Parcelable {

    @SerializedName("images")
    private ArrayList<String> imageList;

    protected MainPagerResponse(Parcel in) {
        imageList = in.createStringArrayList();
    }

    public static final Creator<MainPagerResponse> CREATOR = new Creator<MainPagerResponse>() {
        @Override
        public MainPagerResponse createFromParcel(Parcel in) {
            return new MainPagerResponse(in);
        }

        @Override
        public MainPagerResponse[] newArray(int size) {
            return new MainPagerResponse[size];
        }
    };

    public ArrayList<String> getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList<String> imageList) {
        this.imageList = imageList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringList(imageList);
    }
}
