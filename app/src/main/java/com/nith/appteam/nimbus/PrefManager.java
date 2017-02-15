package com.nith.appteam.nimbus;

import android.content.Context;
import android.content.SharedPreferences;

import static android.R.id.edit;

/**
 * Created by sukhbir on 7/2/17.
 */

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    int PRIVATE_MODE=0;

    private static final String PREF_NAME="appintro";
    private static final String IS_FIRST_TIME_LAUNCH="IsFirstTimeLaunch";
    public PrefManager(Context context) {
        this. context = context;
        pref= context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor= pref.edit();
    }

    public void setIsFirstTimeLaunch(Boolean isFirstTime)
    {
       editor.putBoolean(IS_FIRST_TIME_LAUNCH,isFirstTime);
        editor.commit();

    }
    public boolean isFirstTimeLaunch(){

        return  pref.getBoolean(IS_FIRST_TIME_LAUNCH,true);
    }


}
