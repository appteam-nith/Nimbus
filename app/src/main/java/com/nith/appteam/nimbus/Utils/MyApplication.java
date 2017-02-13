package com.nith.appteam.nimbus.Utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by sahil on 9/2/17.
 */

public class MyApplication extends Application {

    private static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication=this;
    }
    public static synchronized Context getAppContext(){
        return myApplication.getApplicationContext();
    }
}
