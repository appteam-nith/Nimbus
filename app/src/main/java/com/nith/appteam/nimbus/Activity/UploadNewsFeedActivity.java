package com.nith.appteam.nimbus.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.annotations.SerializedName;
import com.nith.appteam.nimbus.CustomView.EditorView;
import com.nith.appteam.nimbus.R;
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
    private ProgressBar progressBar;
    private SharedPref sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_upload_news_feed);
        sharedPref=new SharedPref(this);

        editorView = (EditorView) findViewById(R.id.editor);
        camera_image = (ImageView) findViewById(R.id.select_image);
        upload_image = (ImageView) findViewById(R.id.upload_news);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        camera_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createchooser();
            }
        });

        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditorView.AddTopic add = editorView.buildEditData();
                if (add.title != null && add.detail != null)
                    if (!add.title.isEmpty() && !add.detail.isEmpty()) {
                        upload_image.setVisibility(GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        upload(add.title, add.detail);
                    } else {
                        Toast.makeText(UploadNewsFeedActivity.this, "Some Fields are still empty", Toast.LENGTH_SHORT).show();
                    }
                Log.d(TAG, add.title + " " + add.detail);

            }
        });



    }

    private void createchooser() {
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
            Bitmap bitmap = BitmapFactory.decodeFile(imgDecodableString);
            Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap, getWindow().getWindowManager().getDefaultDisplay().getWidth() / 2, getWindow().getWindowManager().getDefaultDisplay().getHeight() / 2, true);
            editorView.addImage(bitmap1);
        }
    }



    // class for the Uploading News Respons

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


    private void upload(String title, String description) {

        Call<UploadResponse> uploadResponseCall = Util.getRetrofitService().uploadNews(title, description, sharedPref.getUserId(), sharedPref.getUserName());
        uploadResponseCall.enqueue(new Callback<UploadResponse>() {
            @Override
            public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                progressBar.setVisibility(GONE);
                UploadResponse result = response.body();
                int status_code = response.code();
                if (result != null) {
                    if (result.getSuccess()) {
                        Toast.makeText(UploadNewsFeedActivity.this, "Post Successfully Uploaded", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        upload_image.setVisibility(View.VISIBLE);
                        Toast.makeText(UploadNewsFeedActivity.this, "Error While Uploading Please Retry", Toast.LENGTH_LONG).show();
                    }
                } else {
                    upload_image.setVisibility(View.VISIBLE);
                    if (status_code == 503) {
                        Toast.makeText(UploadNewsFeedActivity.this, "Server Done", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UploadNewsFeedActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<UploadResponse> call, Throwable t) {
                upload_image.setVisibility(View.VISIBLE);
                progressBar.setVisibility(GONE);
                t.printStackTrace();
            }
        });
    }
}

