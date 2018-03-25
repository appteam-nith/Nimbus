package com.nith.appteam.nimbus.Utils;

import android.content.Context;

/**
 * Created by jatin on 23/3/18.
 */

public class LoginStatus {
    private SharedPref sharedPref;
    private Context context;

    public LoginStatus(SharedPref sharedPref, Context context) {
        this.sharedPref = sharedPref;
        this.context = context;
    }

    public void setLoginStatusFalse()
    {
        sharedPref.setUserId("");
        sharedPref.setLoginStatus(false);
        sharedPref.setUserRollno("");
        sharedPref.setUserEmail("");
        sharedPref.setUserPicUrl("");
        sharedPref.setProfileStatus(false);
    }
}
