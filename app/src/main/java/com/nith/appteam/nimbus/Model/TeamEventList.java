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

    public class Event{
        @SerializedName("_id")
         private String id;

         @SerializedName("photo")
          private String photo;

         @SerializedName("name")
         private String name;

        public Event(String id, String photo, String name) {
            this.id = id;
            this.photo = photo;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    public class Projects{
        @SerializedName("_id")
       private String id;

        @SerializedName("photo")
        private String photo;

        @SerializedName("name")
        private String name;

        public Projects(String id, String photo, String name) {
            this.id = id;
            this.photo = photo;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
