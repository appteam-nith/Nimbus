package com.nith.appteam.nimbus.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.annotations.SerializedName;
import com.nith.appteam.nimbus.CustomView.EditorView;
import com.nith.appteam.nimbus.Manifest;
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Service.UploadService;
import com.nith.appteam.nimbus.Utils.SharedPref;
import com.nith.appteam.nimbus.Utils.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

/**
 * Created by sahil on 9/2/17.
 */

public class UploadNewsFeedActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final String TAG = "Upload News Feed";
    private EditorView editorView;
    private ImageView camera_image, upload_image;
    private static final String UPLOAD_SERVICE="Upload";
    private static final String TITLE="title";
    private static  final String DESCRIPTION="description";
    private static final String  URL_IMAGE="imageUrl";
    private static final  String WORK="work";
    private SharedPref sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_upload_news_feed);
        sharedPref=new SharedPref(this);
        editorView = (EditorView) findViewById(R.id.editor);
        camera_image = (ImageView) findViewById(R.id.select_image);
        upload_image = (ImageView) findViewById(R.id.upload_news);
        camera_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createChooser();
            }
        });

        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditorView.AddTopic add = editorView.buildEditData();
                StringBuilder imageUrl =new StringBuilder();
                if (add.title != null && add.detail != null)
                    if (!add.title.isEmpty() && !add.detail.isEmpty()) {
                        if(!sharedPref.getFirstTimeRollregister())
                        if(!sharedPref.getNitianStatus()&&sharedPref.getUserRollno().isEmpty()){
                            Log.d("b","b");
                            Log.d("b",sharedPref.getUserRollno());
                            if(sharedPref.getUserRollno().isEmpty()){
                              AlertDialog t= Util.promptRollNo(UploadNewsFeedActivity.this);
                                t.show();

                            }
                        }
                        else {
                            sharedPref.setFirstRollRegister(true);
                        }
                        else {
                       for(int i=0;i<add.imageUrl.size();i++)
                            imageUrl.append(add.imageUrl.get(i)+" ");
                        Log.d("image",imageUrl.toString());
                        Intent i=new Intent(UploadNewsFeedActivity.this, UploadService.class);
                        i.putExtra(UPLOAD_SERVICE,true);
                        i.putExtra(TITLE,add.title);
                        i.putExtra(DESCRIPTION,add.detail);
                        if(!imageUrl.toString().isEmpty())
                        i.putExtra(URL_IMAGE,imageUrl.toString());
                        startService(i);

                        Log.d(TAG, add.title + " " + add.detail+" "+imageUrl);
                        finish();}
                    } else {
                        Toast.makeText(UploadNewsFeedActivity.this, "Some Fields are still empty", Toast.LENGTH_SHORT).show();
                    }


            }
        });



    }

    private void createChooser() {
        if(ContextCompat.checkSelfPermission(UploadNewsFeedActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){

            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 121);
            }

            return;

        }

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "CHOOSE PHOTO"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(filePath, filePathColumn, null, null, null);
            c.moveToFirst();
            String imgDecodableString = c.getString(c.getColumnIndex(filePathColumn[0]));
            c.close();
            editorView.addImage(imgDecodableString);

        }
    }



    // class for the Uploading News Response

    public class UploadResponse {

        @SerializedName("id")
        private String id;

        @SerializedName("success")
        private boolean success;

        @SerializedName("error")
        private String error;

        public UploadResponse(String id, Boolean success, String error) {
            this.id = id;
            this.success = success;
            this.error = error;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }



}

