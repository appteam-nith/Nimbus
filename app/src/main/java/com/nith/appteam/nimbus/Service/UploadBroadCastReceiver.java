package com.nith.appteam.nimbus.Service;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.nith.appteam.nimbus.R;

/**
 * Created by sahil on 17/2/17.
 */

public class UploadBroadCastReceiver extends BroadcastReceiver {
    private static final String UPLOADING_START="start";
    private static final  String UPLOADING_FINISH="finish";
    private static final String UPLOADING_ERROR="error";
    private static final  String WORK="work";
    private static final int UPLOAD_ID=11126741;
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
         switch (intent.getAction()){
             case UPLOADING_START:
                 notificationManager.cancel(UPLOAD_ID);
                 Log.d("reciever","start");
                 builder.setContentTitle("Uploading the "+intent.getStringExtra(WORK));
                 builder.setSmallIcon(R.drawable.person_icon);
                 builder.setProgress(0,0,true);
                 notificationManager.notify(UPLOAD_ID,builder.build());
                 break;
             case UPLOADING_FINISH:
                 notificationManager.cancel(UPLOAD_ID);
                 Log.d("reciever","finish");
                 builder.setContentTitle("Finished Uploading the "+intent.getStringExtra(WORK));
                 builder.setSmallIcon(R.drawable.person_icon);
                 builder.setProgress(0,0,false);
                 notificationManager.notify(UPLOAD_ID,builder.build());
                 break;
             case UPLOADING_ERROR:
                 notificationManager.cancel(UPLOAD_ID);
                 Log.d("reciever","error");
                 builder.setProgress(0,0,false);
                 builder.setSmallIcon(R.drawable.person_icon);
                 builder.setContentTitle("Error While Uploading the "+intent.getStringExtra(WORK));
                 notificationManager.notify(UPLOAD_ID,builder.build());
                 break;

         }
    }
}
