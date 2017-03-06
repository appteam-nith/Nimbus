package com.nith.appteam.nimbus.Fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.google.gson.annotations.SerializedName;
import com.nith.appteam.nimbus.Activity.ProfileActivity;
import com.nith.appteam.nimbus.Model.ProfileDataModel;
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.ApiInterface;
import com.nith.appteam.nimbus.Utils.SharedPref;
import com.nith.appteam.nimbus.Utils.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by aditya on 13/2/17.
 */

public class ProfileTab2 extends Fragment {


    private static final String USER_NAME ="name" ;
    private static final String USER_EMAIL = "email";
    private static final String USER_ROLLNO ="roll_no" ;



    private TextView textName;
    private TextView textRollno;
    private TextView textEmail;
    private SharedPref sharedPref;
    private CardView cardView;
    private LinearLayout rollnoLayout;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){


        View view = inflater.inflate(R.layout.fragment_profiletab2, container, false);


        sharedPref = new SharedPref(getContext());

        cardView = (CardView) view.findViewById(R.id.cardview);
        textName = (TextView) view.findViewById(R.id.name);
        textRollno = (TextView) view.findViewById(R.id.rollno);
        textEmail = (TextView) view.findViewById(R.id.email);
        rollnoLayout = (LinearLayout) view.findViewById(R.id.rollnoLayout);

        if(savedInstanceState==null){

           String name="",email="",rollNo="";
            name=sharedPref.getUserName();
            email=sharedPref.getUserEmail();
            rollNo=sharedPref.getUserRollno();
            if(!name.isEmpty()&&!email.isEmpty()){
                cardView.setVisibility(View.VISIBLE);
                textName.setText(name);
                textEmail.setText(email);
                if(!rollNo.isEmpty()){
                    textRollno.setText(rollNo);
                }
                else {
                    rollnoLayout.setVisibility(View.GONE);
                }
            }


        }
        else{

            cardView.setVisibility(View.VISIBLE);
            if(savedInstanceState.getString(USER_NAME)!=null&&!savedInstanceState.getString(USER_NAME).isEmpty()){
                textName.setText(savedInstanceState.getString(USER_NAME));
            }
            else {
                textName.setVisibility(View.GONE);
            }
            if(savedInstanceState.getString(USER_EMAIL)!=null&&!savedInstanceState.getString(USER_EMAIL).isEmpty()){
                textEmail.setText(savedInstanceState.getString(USER_EMAIL));
            }
            else {
                textEmail.setVisibility(View.GONE);
            }
            if(savedInstanceState.getString(USER_ROLLNO)!=null&&!savedInstanceState.getString(USER_ROLLNO).isEmpty()){
                textRollno.setText(savedInstanceState.getString(USER_ROLLNO));
            }
            else {
                textRollno.setVisibility(View.GONE);
            }
        }




        return view;
    }






    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString(USER_NAME,textName.getText().toString());
        outState.putString(USER_EMAIL,textEmail.getText().toString());
        outState.putString(USER_ROLLNO,textRollno.getText().toString());
    }



}
