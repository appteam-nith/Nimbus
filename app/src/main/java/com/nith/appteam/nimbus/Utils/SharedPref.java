package com.nith.appteam.nimbus.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private static final String PREF_NAME="UserInfo";
    private static final String LOGIN_STATUS="loginstatus";
    private static final String SKIP_STATUS="skipstatus";
    private static final String USER_ID="apikey";
    private static final String IS_FIRST_TIME="isfirstTime";


    public SharedPref(Context context){
        sharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setLoginStatus(boolean isLogIn){
        editor.putBoolean(LOGIN_STATUS,isLogIn);
        editor.commit();
    }

    public boolean getLoginStatus(){
        return sharedPreferences.getBoolean(LOGIN_STATUS,false);
    }

    public void setSkipStatus(boolean isSkip){
        editor.putBoolean(SKIP_STATUS,isSkip);
        editor.commit();
    }

    public boolean getSkipStatus(){
        return sharedPreferences.getBoolean(SKIP_STATUS,false);
    }

    public void setUserId(String userId){
        editor.putString(USER_ID,userId);
        editor.commit();
    }

    public String getUserId(){
        return sharedPreferences.getString(USER_ID,"");
    }

    public void setIsFirstTime(){
        editor.putBoolean(IS_FIRST_TIME,true);
        editor.commit();
    }

    public boolean showIsFirstTime(){
        return sharedPreferences.getBoolean(IS_FIRST_TIME,false);
    }


}
