package com.nith.appteam.nimbus.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.annotations.SerializedName;
import com.nith.appteam.nimbus.Activity.LoginActivity;
import com.nith.appteam.nimbus.Activity.MainActivity;
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.SharedPref;
import com.nith.appteam.nimbus.Utils.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jaykay12 on 12/2/17.
 */
public class FbLoginFragment extends Fragment {

    private LoginButton btnLogin;
    private TextView tvSkip;
    private SharedPref sharedPref;
    private ArrayList<String> permissions;
    private CallbackManager callbackManager;
    private ProgressBar pbLogin;

    public FbLoginFragment() {
        //Required
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = new SharedPref(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fblogin,container,false);
        btnLogin = (LoginButton)view.findViewById(R.id.btnLogin);
        pbLogin = (ProgressBar)view.findViewById(R.id.pbLogin);
        btnLogin.setFragment(this);

        tvSkip = (TextView)view.findViewById(R.id.tvSkip);

        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPref.setSkipStatus(true);
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });

        permissions = new ArrayList<>();
        permissions.add("email");
        btnLogin.setReadPermissions(permissions);

        callbackManager = CallbackManager.Factory.create();

        btnLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        String name = "", email = "", picUrl = "";
                        if (object.has("name")) {
                            try {
                                name = object.getString("name");
                                Log.d("Login",name);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (object.has("email")) {
                            try {
                                email = object.getString("email");
                                Log.d("email",email);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            email = " ";
                        }
                        if (object.has("id")) {
                            try {

                                String id = object.getString("id");
                                picUrl = "https://graph.facebook.com/" + id + "/picture?width=200&height=200";
                                Log.d("Login",picUrl);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        btnLogin.setVisibility(View.GONE);
                        tvSkip.setVisibility(View.GONE);
                        saveFbUserData(name, email, picUrl);
                        pbLogin.setVisibility(View.VISIBLE);
                    }
                });

                Bundle bundle = new Bundle();
                bundle.putString("fields","name,email,id");
                graphRequest.setParameters(bundle);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {
                sharedPref.setSkipStatus(true);
                sharedPref.setLoginStatus(false);
                getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getActivity(), "Error Occur While Processing your Facebook Login, Please Try Again!!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void saveFbUserData(String name,String email,String picUrl)
    {
        Call<FbLoginFragment.UserSentResponse> userSentResponseCall = Util.getRetrofitService().sendFbUserData(name,email,picUrl);
        userSentResponseCall.enqueue(new Callback<UserSentResponse>() {
            @Override
            public void onResponse(Call<UserSentResponse> call, Response<UserSentResponse> response) {
                UserSentResponse userSentResponse = response.body();
                if(userSentResponse!=null && response.isSuccess())
                {
                    Log.v("ID", userSentResponse.getUserId());
                    sharedPref.setLoginStatus(true);
                    sharedPref.setSkipStatus(false);// as user has login succesfully and we make sure  that screen does not come again
                    sharedPref.setUserId(userSentResponse.getUserId());

                    pbLogin.setVisibility(View.GONE);
                    Intent intent = new Intent(getActivity(),MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                    getActivity().startActivity(intent);
                    getActivity().finish();
                }
                else
                {
                    Toast.makeText(getActivity(),"Check Internet connection",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserSentResponse> call, Throwable t) {
                Toast.makeText(getActivity(),"Check Internet Connection",Toast.LENGTH_LONG).show();
            }
        });
    }

    public class UserSentResponse{
        @SerializedName("msg")
        private String message;

        @SerializedName("student_id")
        private String userId;

        public UserSentResponse(String message, String userId){
            this.message = message;
            this.userId = userId;
        }

        public void setMessage(String message){ this.message = message; }

        public String getMessage() {    return message; }

        public void setUserId(String userId){   this.userId = userId;   }

        public String getUserId()   {   return userId;  }
    }

}
