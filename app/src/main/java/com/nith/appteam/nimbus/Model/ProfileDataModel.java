package com.nith.appteam.nimbus.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.nith.appteam.nimbus.Fragment.ProfileTab2;

/**
 * Created by aditya on 16/2/17.
 */

public class ProfileDataModel  implements Parcelable{


    @SerializedName("success")
    private boolean success;

    @SerializedName("name")
    private String name;

    @SerializedName("roll_no")
    private String rollno;

    @SerializedName("photo")
    private String photo;

    @SerializedName("email")
    private String email;

    @SerializedName("msg")
    private String msg;

    protected ProfileDataModel(Parcel in) {
        success = in.readByte() != 0;
        name = in.readString();
        rollno = in.readString();
        photo = in.readString();
        email = in.readString();
        msg = in.readString();
    }

    public static final Creator<ProfileDataModel> CREATOR = new Creator<ProfileDataModel>() {
        @Override
        public ProfileDataModel createFromParcel(Parcel in) {
            return new ProfileDataModel(in);
        }

        @Override
        public ProfileDataModel[] newArray(int size) {
            return new ProfileDataModel[size];
        }
    };

    public boolean isSuccess() {
        return success;
    }

    public String getName() {
        return name;
    }

    public String getRollno() {
        return rollno;
    }

    public String getPhoto() {
        return photo;
    }

    public String getEmail() {
        return email;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeString(name);
        dest.writeString(rollno);
        dest.writeString(photo);
        dest.writeString(email);
        dest.writeString(msg);
    }
}
