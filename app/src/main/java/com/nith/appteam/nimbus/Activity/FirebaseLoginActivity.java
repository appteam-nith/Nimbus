package com.nith.appteam.nimbus.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.annotations.SerializedName;
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.SharedPref;
import com.nith.appteam.nimbus.Utils.Util;
import com.nith.appteam.nimbus.Utils.Volleycustom;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirebaseLoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 138;
    private String requestBody;
    private Button loginBtn;
    private ProgressBar progressBar;
    private List<AuthUI.IdpConfig> providers;
    private TextView textView;
    private SharedPref sharedPref;
    private JSONObject jsonObject1;
    private String HashedValue;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_login);

        loginBtn = (Button) findViewById(R.id.login_btn);
        progressBar = (ProgressBar) findViewById(R.id.login_progress);
        textView = findViewById(R.id.skip);

        providers = Arrays.asList(
                new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build());

        sharedPref = new SharedPref(this);

    }

    public void onLoginBtnClick(View v) {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setLogo(R.drawable.nimbuslogo)
                        .build(),
                RC_SIGN_IN);
        progressBar.setVisibility(View.VISIBLE);
        textView.setVisibility(View.GONE);
        loginBtn.setVisibility(View.GONE);
    }

    public void onSkipClick(View v) {
        sharedPref.setSkipStatus(true);
        startActivity(new Intent(this, HomescreenNew.class));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                user = FirebaseAuth.getInstance().getCurrentUser();
                Log.d("pno.: ", user.getPhoneNumber());
                Log.d("uid: ", user.getUid());
                final JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("mobile",user.getPhoneNumber());
                    jsonObject.put("firebase_id",user.getUid());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Volleycustom volleycustom = new Volleycustom(this);
//                String hashed_value = volleycustom.postsimpleJsonObject(jsonObject,"https://nimbus19.herokuapp.com/auth/signup");
//                Toast.makeText(FirebaseLoginActivity.this,hashed_value,Toast.LENGTH_SHORT).show();
                RequestQueue queue = Volley.newRequestQueue(this);
                requestBody = jsonObject.toString();
                StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://nimbus19.herokuapp.com/auth/signup", new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            jsonObject1 = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            HashedValue = jsonObject1.getString("authId");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(FirebaseLoginActivity.this,HashedValue,Toast.LENGTH_SHORT).show();
                        saveUserLoginData(user.getPhoneNumber(), user.getUid(),HashedValue);


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
                    protected com.android.volley.Response<String> parseNetworkResponse(NetworkResponse response) {
                        return super.parseNetworkResponse(response);
                    }
                };
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                queue.add(stringRequest);
            } else {
                // Sign in failed, check response for error code
                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        }

    }


    private void saveUserLoginData(final String phone_no, String fb_id, final String hashedValue) {

        Call<FirebaseLoginActivity.UserSentResponse> userSentResponseCall = Util.getRetrofitService().sendUserLoginData(phone_no, fb_id);
        userSentResponseCall.enqueue(new Callback<FirebaseLoginActivity.UserSentResponse>() {
            @Override
            public void onResponse(Call<FirebaseLoginActivity.UserSentResponse> call, Response<FirebaseLoginActivity.UserSentResponse> response) {
                FirebaseLoginActivity.UserSentResponse userSentResponse = response.body();
                if (userSentResponse != null && response.isSuccess()) {
                    Log.v("ID", userSentResponse.getUserId());
                    sharedPref.setLoginStatus(true);
                    sharedPref.setSkipStatus(false);// as user has login succesfully and we make sure  that screen does not come again
                    sharedPref.setUserId(userSentResponse.getUserId());
                    sharedPref.setUserPhone(phone_no);
                    sharedPref.setHashedValue(hashedValue);

                    if (userSentResponse.getBranch().isEmpty() || userSentResponse.getYear().isEmpty() || userSentResponse.getEmail().isEmpty() ||
                            userSentResponse.getName().isEmpty() || userSentResponse.getRoll_no().isEmpty()) {
                        sharedPref.setProfileStatus(false);
                        Log.d("no data", "hello");
                    }
                    else {

                        sharedPref.setUserBranch(userSentResponse.getBranch());
                        sharedPref.setUserYearPos(userSentResponse.getYear());
                        // sharePref.setUserYearText(userSentResponse.getYearText());
                        sharedPref.setUserEmail(userSentResponse.getEmail());
                        sharedPref.setUserName(userSentResponse.getName());
                        sharedPref.setUserRollno(userSentResponse.getRoll_no());
                        sharedPref.setProfileStatus(true);
                        Log.d("aa", userSentResponse.getBranch());
                        Log.d("bb", userSentResponse.getRoll_no());
                        Log.d("cc", userSentResponse.getName());
                        Log.d("dd", userSentResponse.getEmail());
                    }

                    progressBar.setVisibility(View.GONE);

                    Intent intent = new Intent(FirebaseLoginActivity.this, UserInfoActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else{
                    Toast.makeText(FirebaseLoginActivity.this, "Check Internet connection", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<FirebaseLoginActivity.UserSentResponse> call, Throwable t) {
                Toast.makeText(FirebaseLoginActivity.this, "Check Internet Connection", Toast.LENGTH_LONG).show();
            }
        });

    }



    public class UserSentResponse {
        @SerializedName("msg")
        private String message;

        @SerializedName("student_id")
        private String userId;

        @SerializedName("name")
        private String name;

        @SerializedName("roll_no")
        private String roll_no;

        @SerializedName("year")
        private String year;

        @SerializedName("branch")
        private String branch;

        @SerializedName("email")
        private String email;

        public UserSentResponse(String message, String userId, String name, String roll_no, String year, String branch, String email) {
            this.message = message;
            this.userId = userId;
            this.name = name;
            this.roll_no = roll_no;
            this.year = year;
            this.branch = branch;
            this.email = email;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRoll_no() {
            return roll_no;
        }

        public void setRoll_no(String roll_no) {
            this.roll_no = roll_no;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getBranch() {
            return branch;
        }

        public void setBranch(String branch) {
            this.branch = branch;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }




}
