package com.nith.appteam.nimbus.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jatin on 22/3/18.
 */

public class ProfileEdit {
    @SerializedName("name")
    private String Name;
    @SerializedName("email")
    private String email;
    @SerializedName("branch")
    private String branch;
    @SerializedName("year")
    private String year;
    @SerializedName("roll_no")
    private String roll_no;

    public ProfileEdit(String name, String email, String branch, String year, String roll_no) {
        Name = name;
        this.email = email;
        this.branch = branch;
        this.year = year;
        this.roll_no = roll_no;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRoll_no() {
        return roll_no;
    }

    public void setRoll_no(String roll_no) {
        this.roll_no = roll_no;
    }
}
