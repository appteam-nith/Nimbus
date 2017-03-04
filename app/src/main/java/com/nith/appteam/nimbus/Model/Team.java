package com.nith.appteam.nimbus.Model;

/**
 * Created by acer on 2/19/2017.
 */

import java.util.ArrayList;

public class Team {
    //PROPERTIES OF A SINGLE TEAM
    public String Name;
    public String Image;
    public ArrayList<String> players=new ArrayList<String>();
    public Team(String Name)
    {
        this.Name=Name;
    }
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return Name;
    }
}
