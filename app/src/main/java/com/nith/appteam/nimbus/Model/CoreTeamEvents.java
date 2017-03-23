package com.nith.appteam.nimbus.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created Nimbus by akatsuki on 22/3/17.
 */

public class CoreTeamEvents {

    @SerializedName("success")
    private boolean success;
    @SerializedName("msg")
    private String msg;
    @SerializedName("coreteam")
    private CoreTeam coreTeam;

    public CoreTeamEvents(boolean success, String msg, CoreTeam coreTeam) {
        this.success = success;
        this.msg = msg;
        this.coreTeam = coreTeam;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public CoreTeam getCoreTeam() {
        return coreTeam;
    }

    public void setCoreTeam(CoreTeam coreTeam) {
        this.coreTeam = coreTeam;
    }

    public class CoreTeam{
         @SerializedName("name")
         private String name;
         @SerializedName("desc")
         private String desc;
         @SerializedName("logo")
         private String logo;
         @SerializedName("banner")
         private String banner;
         @SerializedName("events")
         ArrayList<Event>events;

         public CoreTeam(String name, String desc, String logo, String banner, ArrayList<Event> events) {
             this.name = name;
             this.desc = desc;
             this.logo = logo;
             this.banner = banner;
             this.events = events;
         }

         public String getName() {
             return name;
         }

         public void setName(String name) {
             this.name = name;
         }

         public String getDesc() {
             return desc;
         }

         public void setDesc(String desc) {
             this.desc = desc;
         }

         public String getLogo() {
             return logo;
         }

         public void setLogo(String logo) {
             this.logo = logo;
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
     }
   public class Event{
        @SerializedName("_id")
        private String id;


        @SerializedName("name")
        private String name;

        @SerializedName("photo")
        private String photo;

        @SerializedName("team_id")
        private String teamid;

        public Event(String id, String name, String photo, String teamid) {
            this.id = id;
            this.name = name;
            this.photo = photo;
            this.teamid = teamid;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getTeamid() {
            return teamid;
        }

        public void setTeamid(String teamid) {
            this.teamid = teamid;
        }
    }
}
