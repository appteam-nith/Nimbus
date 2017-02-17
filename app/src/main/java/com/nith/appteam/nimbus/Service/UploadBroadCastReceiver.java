package com.nith.appteam.nimbus.Service;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by sahil on 17/2/17.
 */

public class UploadBroadCastReceiver extends BroadcastReceiver {
    private static final String UPLOADING_START="start";
    private static final  String UPLOADING_FINISH="finish";
    private static final String UPLOADING_ERROR="error";
    private static final int UPLOAD_ID=1;
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context);

         switch (intent.getAction()){
             case UPLOADING_START:
                 builder.setContentTitle("Uploading the NewsFeed");
                 builder.setProgress(0,0,true);
                 notificationManager.notify(UPLOAD_ID,builder.build());
                 break;
             case UPLOADING_FINISH:
                 builder.setContentTitle("Finished Uploading the NewsFeed");
                 builder.setProgress(0,0,false);
                 notificationManager.notify(UPLOAD_ID,builder.build());
                 break;
             case UPLOADING_ERROR:
                 builder.setProgress(0,0,false);
                 builder.setContentTitle("Error While Uploading the NewsFeed");
                 notificationManager.notify(UPLOAD_ID,builder.build());
                 break;

         }
    }
}
