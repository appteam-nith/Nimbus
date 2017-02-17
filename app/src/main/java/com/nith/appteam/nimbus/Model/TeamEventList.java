package com.nith.appteam.nimbus.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created Nimbus by akatsuki on 14/2/17.
 */

public class TeamEventList {

    @SerializedName("success")
    private boolean sucess;



    @SerializedName("name")
    private String name;//team name


    @SerializedName("dept")
    private String department;

    @SerializedName("logo")
    private String logo;

    @SerializedName("desc")
    private String desc;

    @SerializedName("banner")
    private String banner;

    @SerializedName("events")
    private ArrayList<Event>events;

    @SerializedName("projects")
    private ArrayList<Projects>projects;

    public boolean isSucess() {
        return sucess;
    }

    public void setSucess(boolean sucess) {
        this.sucess = sucess;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public ArrayList<Projects> getProjects() {
        return projects;
    }

    public void setProjects(ArrayList<Projects> projects) {
        this.projects = projects;
    }

    private class Event{
        @SerializedName("_id")
         String id;

         @SerializedName("photo")
         String photo;

         @SerializedName("name")
         String name;
    }
    private class Projects{
        @SerializedName("_id")
        String id;

        @SerializedName("photo")
        String photo;

        @SerializedName("name")
        String name;
    }

}
