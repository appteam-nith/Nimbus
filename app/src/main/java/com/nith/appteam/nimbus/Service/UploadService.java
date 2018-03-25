package com.nith.appteam.nimbus.Service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.cloudinary.Cloudinary;
import com.cloudinary.android.Utils;
import com.cloudinary.utils.ObjectUtils;
import com.nith.appteam.nimbus.Activity.UploadNewsFeedActivity;
import com.nith.appteam.nimbus.Model.RegisterResponse;
import com.nith.appteam.nimbus.Utils.SharedPref;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by aditya on 18/10/17.
 */

public class UploadService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    private static final String UPLOAD_SERVICE = "Upload";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String URL_IMAGE = "imageUrl";
    private static final String UPLOADING_START="start";
    private static final String UPLOADING_FINISH="finish";
    private static final String UPLOADING_ERROR="error";
    private SharedPref sharedPref;
    private static final String REGISTER_ROLL_NO="rollNoRegister";
    private static  final String ROLL_NO="rollNo";
    private static final String WORK="work";

public UploadService(){
    super("UploadService");
}
    public UploadService(String name) {
        super(name);

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        sharedPref = new SharedPref(this);
        System.out.println("jjjssasas");
        if (intent != null) if (intent.hasExtra(UPLOAD_SERVICE)) {
            String title = "", description = "", imageUrl = "";
            if (intent.hasExtra(TITLE)) {
                title = intent.getStringExtra(TITLE);
            }
            if (intent.hasExtra(DESCRIPTION)) {
                description = intent.getStringExtra(DESCRIPTION);
            }
            if (intent.hasExtra(URL_IMAGE)) {
                imageUrl = intent.getStringExtra(URL_IMAGE);
                System.out.println(imageUrl);
                Cloudinary cloudinary = new Cloudinary(Utils.cloudinaryUrlFromContext(getApplicationContext()));
                System.out.println("hello");
                try {
                    Intent i = new Intent(UPLOADING_START);
                    i.putExtra(WORK, "NewsFeed");
                    sendBroadcast(i);
                    Map map = cloudinary.uploader().upload(imageUrl.trim(), ObjectUtils.asMap("public_id", sharedPref.getUserName() + "" + com.nith.appteam.nimbus.Utils.Util.random()));
                    String imageEncodedUrl = (String) map.get("url");
                    if(!imageEncodedUrl.isEmpty() && imageEncodedUrl.length()>0) upload(sharedPref.getUserName(),title, description,imageEncodedUrl );
                    else{
                        upload(sharedPref.getUserName(),title, description, "");
                    }

                    Log.d("image", (String) map.get("url"));
                } catch (Exception e) {
                    e.printStackTrace();
                    Intent i = new Intent();
                    i.setAction(UPLOADING_ERROR);
                    i.putExtra(WORK, "NewsFeed");
                    sendBroadcast(i);

                }
            }
            else {
                upload(sharedPref.getUserName(),title, description, "");
            }





        } else if (intent.hasExtra(REGISTER_ROLL_NO)) {
            if (intent.hasExtra(ROLL_NO)) {
                Intent i = new Intent(UPLOADING_START);
                i.putExtra(WORK, "Roll Number");
                sendBroadcast(i);
                registerRollNo(intent.getStringExtra(ROLL_NO), sharedPref.getUserId());
            }
        }
    }

    private void upload(String username, String title, String description, String imageUrl) {

        Call<UploadNewsFeedActivity.UploadResponse> uploadResponseCall = com.nith.appteam.nimbus.Utils.Util.getRetrofitService().uploadNews(title, description, sharedPref.getUserId(), sharedPref.getUserName(), imageUrl);
        uploadResponseCall.enqueue(new Callback<UploadNewsFeedActivity.UploadResponse>() {
            @Override
            public void onResponse(Call<UploadNewsFeedActivity.UploadResponse> call, Response<UploadNewsFeedActivity.UploadResponse> response) {
                UploadNewsFeedActivity.UploadResponse result = response.body();
                int status_code = response.code();
                if (result != null && response.isSuccess()) {
                    if (result.getSuccess()) {
                        Intent i=new Intent(UPLOADING_FINISH);
                        System.out.println("dsds");
                        i.putExtra(WORK,"NewsFeed");
                        sendBroadcast(i);
                    } else {
                        System.out.println("ds");
                    }
                } else {
                    if (status_code == 404) {
                        Intent i=new Intent(UPLOADING_ERROR);
                        i.putExtra(WORK,"NewsFeed");
                        sendBroadcast(i);
                    } else if (status_code == 503) {
                        Intent i=new Intent(UPLOADING_ERROR);
                        i.putExtra(WORK,"NewsFeed");
                        sendBroadcast(i);
                    }

                }

            }

            @Override
            public void onFailure(Call<UploadNewsFeedActivity.UploadResponse> call, Throwable t) {
                Intent i=new Intent(UPLOADING_ERROR);
                i.putExtra(WORK,"NewsFeed");
                sendBroadcast(i);
                t.printStackTrace();
            }
        });
    }

    private void  registerRollNo(String rollNo, String studentId){
        Call<RegisterResponse> call= com.nith.appteam.nimbus.Utils.Util.getRetrofitService().updateRollNo(studentId,rollNo);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                RegisterResponse result=response.body();
                int status_code = response.code();
                if (result != null && response.isSuccess()) {
                    if (result.isSuccess()) {
                        Intent i=new Intent(UPLOADING_FINISH);
                        i.putExtra(WORK,"Roll No");
                        sendBroadcast(i);
                    } else {

                    }
                } else {
                    if (status_code == 404) {
                        Intent i=new Intent(UPLOADING_ERROR);
                        i.putExtra(WORK,"Roll No");
                        sendBroadcast(i);
                    } else if (status_code == 503) {
                        Intent i=new Intent(UPLOADING_ERROR);
                        i.putExtra(WORK,"Roll No");
                        sendBroadcast(i);
                    }

                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Intent i=new Intent(UPLOADING_ERROR);
                i.putExtra(WORK,"Roll No");
                sendBroadcast(i);
                t.printStackTrace();
            }
        });
    }
}
