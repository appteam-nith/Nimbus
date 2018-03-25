package com.nith.appteam.nimbus.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.annotations.SerializedName;
import com.nith.appteam.nimbus.Model.LeaderBoardModel;
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.ApiInterface;
import com.nith.appteam.nimbus.Utils.SharedPref;
import com.nith.appteam.nimbus.Utils.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivityEdit extends AppCompatActivity {
    private EditText firstName;
    private EditText rollNo;
    private EditText branch;
    private EditText year;
    private Button submit;
    private EditText email;
    private SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        sharedPref=new SharedPref(this);
        firstName = (EditText) findViewById(R.id.firstname);
        rollNo = (EditText)findViewById(R.id.rollno);
        branch = (EditText) findViewById(R.id.branch);
        year = (EditText) findViewById(R.id.year);
        submit = (Button) findViewById(R.id.submit_data);
        email = (EditText) findViewById(R.id.email);
        firstName.setText(sharedPref.getUserName());
        rollNo.setText(sharedPref.getUserRollno());
        email.setText(sharedPref.getUserEmail());
        branch.setText(sharedPref.getBRANCH());
        rollNo.setText(sharedPref.getUserRollno());
        year.setText(sharedPref.getYEAR());
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!firstName.getText().toString().isEmpty() && !rollNo.getText().toString().isEmpty() && !branch.getText().toString().isEmpty() && !year.getText().toString().isEmpty() && !email.getText().toString().isEmpty()) {
                    setData();
                }
            }
        });

    }
    public void setData(){
        ApiInterface mAPI = Util.getRetrofitService();
        Call<ProfileResponse> mService = mAPI.setProfile(sharedPref.getUserId(), firstName.getText().toString(), email.getText().toString(), rollNo.getText().toString(), branch.getText().toString(), year.getText().toString());
        mService.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if(response.isSuccess())
                {
                    if(response.body().isSuccess())
                    {
                        Toast.makeText(ProfileActivityEdit.this, "Post updated successfully", Toast.LENGTH_SHORT).show();
                        setSharedPrefData();
                        sharedPref.setProfileStatus(true);
                        finish();
                    }
                    else{
                        Toast.makeText(ProfileActivityEdit.this, "Try Again", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                Toast.makeText(ProfileActivityEdit.this, "Try Again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class ProfileResponse {
        @SerializedName("id")
        private String id;

        @SerializedName("success")
        private boolean success;

        @SerializedName("error")
        private String error;

        public ProfileResponse(String id, boolean success, String error) {
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

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }
    public void setSharedPrefData() {
        sharedPref.setBranch(branch.getText().toString());
        sharedPref.setYear(year.getText().toString());
        sharedPref.setUserRollno(rollNo.getText().toString());
        sharedPref.setUserEmail(email.getText().toString());
        sharedPref.setUserName(firstName.getText().toString());
    }

}