package com.nith.appteam.nimbus.Service;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.cloudinary.Cloudinary;
import com.cloudinary.android.Utils;
import com.cloudinary.utils.ObjectUtils;
import com.nith.appteam.nimbus.Activity.UploadNewsFeedActivity;
import com.nith.appteam.nimbus.Utils.MyApplication;
import com.nith.appteam.nimbus.Utils.SharedPref;
import com.nith.appteam.nimbus.Utils.Util;

import java.io.IOException;
import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

/**
 * Created by sahil on 17/2/17.
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
    private static final  String UPLOADING_FINISH="finish";
    private static final String UPLOADING_ERROR="error";
    private SharedPref sharedPref;


public UploadService(){
    super("UploadService");
}
    public UploadService(String name) {
        super(name);

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        sharedPref = new SharedPref();
        if (intent != null) {
            if (intent.hasExtra(UPLOAD_SERVICE)) {
                String title = "", description = "", imageUrl = "";
                Uri uri = null;
                if (intent.hasExtra(TITLE)) {
                    title = intent.getStringExtra(TITLE);
                }
                if (intent.hasExtra(DESCRIPTION)) {
                    description = intent.getStringExtra(DESCRIPTION);
                }
                if (intent.hasExtra(URL_IMAGE)) {
                    imageUrl = intent.getStringExtra(URL_IMAGE);
                    uri = Uri.parse(imageUrl);
                }
               Cloudinary cloudinary = new Cloudinary(Utils.cloudinaryUrlFromContext(MyApplication.getAppContext()));
                try {
                    Intent i=new Intent(UPLOADING_START);
                    sendBroadcast(i);
                   Map map = cloudinary.uploader().upload(imageUrl.trim(), ObjectUtils.asMap("public_id", sharedPref.getUserName()+""+ Util.random()));
                    upload(title, description, (String) map.get("url"));
                   Log.d("image",(String) map.get("url"));
                } catch (Exception e) {
                    e.printStackTrace();
                    Intent i=new Intent();
                    i.setAction(UPLOADING_ERROR);
                    sendBroadcast(i);
                }


            }
        }
    }

    private void upload(String title, String description, String imageUrl) {

        Call<UploadNewsFeedActivity.UploadResponse> uploadResponseCall = Util.getRetrofitService().uploadNews(title, description, sharedPref.getUserId(), sharedPref.getUserName(), imageUrl);
        uploadResponseCall.enqueue(new Callback<UploadNewsFeedActivity.UploadResponse>() {
            @Override
            public void onResponse(Call<UploadNewsFeedActivity.UploadResponse> call, Response<UploadNewsFeedActivity.UploadResponse> response) {
                UploadNewsFeedActivity.UploadResponse result = response.body();
                int status_code = response.code();
                if (result != null && response.isSuccess()) {
                    if (result.getSuccess()) {
                        Intent i=new Intent(UPLOADING_FINISH);
                        sendBroadcast(i);
                    } else {

                    }
                } else {
                    if (status_code == 404) {
                        Intent i=new Intent(UPLOADING_ERROR);
                        sendBroadcast(i);
                    } else if (status_code == 503) {
                        Intent i=new Intent(UPLOADING_ERROR);
                        sendBroadcast(i);
                    }

                }

            }

            @Override
            public void onFailure(Call<UploadNewsFeedActivity.UploadResponse> call, Throwable t) {
                Intent i=new Intent(UPLOADING_ERROR);
                sendBroadcast(i);
                t.printStackTrace();
            }
        });
    }
}
