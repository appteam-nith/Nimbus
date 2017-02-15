package com.nith.appteam.nimbus.Model;

/**
 * Created by aditya on 14/2/17.
 */

public class ProfileEventModel {

    private String eventName;
    private String clubName;

    public ProfileEventModel(String eventName, String clubName) {
        this.eventName = eventName;
        this.clubName = clubName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }


}
