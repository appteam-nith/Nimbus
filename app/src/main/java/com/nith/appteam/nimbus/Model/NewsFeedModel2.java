package com.nith.appteam.nimbus.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sahil on 9/2/17.
 */

public class NewsFeedModel2 implements Parcelable {
    @SerializedName("_id")
    private String _id;
    @SerializedName("title")
    private String title;
    @SerializedName("photo")
    private String photo;
    @SerializedName("description")
    private String description;

    @SerializedName("uId")
    private String userid;

    @SerializedName("uName")
    private String username;

    @SerializedName("status")
    private boolean status;

    @SerializedName("likes")
    private int likes;

    @SerializedName("date")
    private String date;


    public NewsFeedModel2(String _id, String title, String photo, String description, String userid, String username, String date,int likes,boolean status) {
        this._id = _id;
        this.title = title;
        this.photo = photo;
        this.description = description;
        this.userid = userid;
        this.username = username;
        this.likes=likes;
        this.date = date;
    }

    protected NewsFeedModel2(Parcel in) {
        _id = in.readString();
        title = in.readString();
        photo = in.readString();
        description = in.readString();
        userid = in.readString();
        username = in.readString();
        date = in.readString();
    }

    public static final Creator<NewsFeedModel2> CREATOR = new Creator<NewsFeedModel2>() {
        @Override
        public NewsFeedModel2 createFromParcel(Parcel in) {
            return new NewsFeedModel2(in);
        }

        @Override
        public NewsFeedModel2[] newArray(int size) {
            return new NewsFeedModel2[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getLikes(){return likes;}
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_id);
        parcel.writeString(title);
        parcel.writeString(photo);
        parcel.writeString(description);
        parcel.writeString(userid);
        parcel.writeString(username);
        parcel.writeInt(likes);
        parcel.writeString(date);
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
