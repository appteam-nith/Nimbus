package com.nith.appteam.nimbus.Activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.SharedPref;
import com.nith.appteam.nimbus.Utils.Volleycustom;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class UserInfoActivity extends AppCompatActivity {
    private EditText FirstName,Emailid,RollNo,Branch;
    private Spinner spinnerYear;
    private Button button;
    private ProgressBar progressBar;
    private SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        FirstName = findViewById(R.id.firstname);
        Emailid = findViewById(R.id.email);
        RollNo = findViewById(R.id.rollno);
        Branch = findViewById(R.id.branch);
        spinnerYear = findViewById(R.id.spinner);
        button  =findViewById(R.id.submit_data);
        progressBar = findViewById(R.id.profile_progress);
        sharedPref = new SharedPref(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!FirstName.getText().toString().isEmpty() && !RollNo.getText().toString().isEmpty() && !Branch.getText().toString().isEmpty() && !Emailid.getText().toString().isEmpty()){
                    if (isValidEmail(Emailid.getText().toString())) {
                        button.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        postData();


                    } else {
                        Toast.makeText(UserInfoActivity.this, "Enter Correct email address", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(UserInfoActivity.this, "Enter All Details", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    private void postData(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name",FirstName.getText().toString());
            jsonObject.put("rollNumber",RollNo.getText().toString());
            jsonObject.put("authId",sharedPref.getHashedValue());
            jsonObject.put("branch",Branch.getText().toString());
            jsonObject.put("year",spinnerYear.getSelectedItem().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = jsonObject.toString();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://nimbus19.herokuapp.com/auth/info",new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
//                if(Integer.parseInt(response)==200){
//                    Toast.makeText(UserInfoActivity.this,"You have successfully filled your credentials",Toast.LENGTH_SHORT).show();
//                    sharedPref.setUserName(FirstName.getText().toString());
//                    sharedPref.setUserBranch(Branch.getText().toString());
//                    sharedPref.setUserYearPos(spinnerYear.getSelectedItem().toString());
//                    sharedPref.setUserEmail(Emailid.getText().toString());
//                    sharedPref.setUserRollno(RollNo.getText().toString());
//                    progressBar.setVisibility(View.GONE);
//                    startActivity(new Intent(UserInfoActivity.this,HomescreenNew.class));
//                    finish();
//
//                }
//                else if(Integer.parseInt(response)==501){
//                    Toast.makeText(UserInfoActivity.this,"Unsuccessful Event , Please try again.",Toast.LENGTH_SHORT).show();
//                }
//                Toast.makeText(UserInfoActivity.this,response,Toast.LENGTH_LONG).show();
                Toast.makeText(UserInfoActivity.this,response,Toast.LENGTH_SHORT).show();
            }


        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token",sharedPref.getHashedValue());
                return params;
            }

            @Override
            protected com.android.volley.Response<String> parseNetworkResponse(NetworkResponse response) {

                return super.parseNetworkResponse(response);
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

}
