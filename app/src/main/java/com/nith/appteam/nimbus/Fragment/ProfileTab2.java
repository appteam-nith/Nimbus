package com.nith.appteam.nimbus.Fragment;

import android.content.Intent;
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

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.google.gson.annotations.SerializedName;
import com.nith.appteam.nimbus.Activity.FirebaseLoginActivity;
import com.nith.appteam.nimbus.Activity.ProfileActivity;
import com.nith.appteam.nimbus.Activity.ProfileActivityEdit;
import com.nith.appteam.nimbus.Model.ProfileDataModel;
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.ApiInterface;
import com.nith.appteam.nimbus.Utils.SharedPref;
import com.nith.appteam.nimbus.Utils.Util;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by aditya on 13/2/17.
 */

public class ProfileTab2 extends Fragment {


    private static final String USER_NAME = "name";
    private static final String USER_EMAIL = "email";
    private static final String USER_ROLLNO = "roll_no";


    private TextView textName;
    private TextView textRollno;
    private TextView textEmail;
    private TextView textPhone;
    private TextView textBranch;
    private TextView textYear;

    private SharedPref sharedPref;
    private CardView cardView;
    private LinearLayout rollnoLayout;
    private Button editProfile;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_profiletab2, container, false);


        sharedPref = new SharedPref(getContext());

        cardView = (CardView) view.findViewById(R.id.cardview);
        textName = (TextView) view.findViewById(R.id.name);
        textRollno = (TextView) view.findViewById(R.id.rollno);
        textEmail = (TextView) view.findViewById(R.id.email);
        textPhone = (TextView) view.findViewById(R.id.phone);
        textBranch = (TextView) view.findViewById(R.id.branch);
        textYear = (TextView) view.findViewById(R.id.year);

        rollnoLayout = (LinearLayout) view.findViewById(R.id.rollnoLayout);
        editProfile = view.findViewById(R.id.editprofile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sharedPref.getLoginStatus()) {
                    Toast.makeText(getContext(), "Please Login to edit profile", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), FirebaseLoginActivity.class));
                    getActivity().finish();
                } else
                    startActivity(new Intent(getContext(), ProfileActivityEdit.class));
                    getActivity().finish();
            }
        });

        if (savedInstanceState == null) {

            String name = "", email = "", rollNo = "", phone = "", branch="", year_pos="";
            int year;
            String year_text;

            name = sharedPref.getUserName();
            email = sharedPref.getUserEmail();
            rollNo = sharedPref.getUserRollno();
            phone = sharedPref.getUserPhone();
            branch = sharedPref.getUserBranch();
            year_pos = sharedPref.getUserYearPos();

            if (!name.isEmpty() && !email.isEmpty()) {

                cardView.setVisibility(View.VISIBLE);
                textName.setText(name);
                textEmail.setText(email);
                textPhone.setText(phone);
                textBranch.setText(branch);

                year = Integer.parseInt(year_pos)+1;
                switch(year) {
                    case (1):
                        year_text = String.valueOf(year) + "st";
                        break;
                    case (2):
                        year_text = String.valueOf(year) + "nd";
                        break;
                    case (3):
                        year_text = String.valueOf(year) + "rd";
                        break;
                    default:
                        year_text = String.valueOf(year) + "th";
                }

                year_text += " year";
                textYear.setText(year_text);

                if (!rollNo.isEmpty()) {
                    textRollno.setText(rollNo);
                } else {
                    rollnoLayout.setVisibility(View.GONE);
                }
            }


        } else {

            cardView.setVisibility(View.VISIBLE);
            if (savedInstanceState.getString(USER_NAME) != null && !savedInstanceState.getString(USER_NAME).isEmpty()) {
                textName.setText(savedInstanceState.getString(USER_NAME));
            } else {
                textName.setVisibility(View.GONE);
            }
            if (savedInstanceState.getString(USER_EMAIL) != null && !savedInstanceState.getString(USER_EMAIL).isEmpty()) {
                textEmail.setText(savedInstanceState.getString(USER_EMAIL));
            } else {
                textEmail.setVisibility(View.GONE);
            }
            if (savedInstanceState.getString(USER_ROLLNO) != null && !savedInstanceState.getString(USER_ROLLNO).isEmpty()) {
                textRollno.setText(savedInstanceState.getString(USER_ROLLNO));
            } else {
                textRollno.setVisibility(View.GONE);
            }
        }


        return view;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(USER_NAME, textName.getText().toString());
        outState.putString(USER_EMAIL, textEmail.getText().toString());
        outState.putString(USER_ROLLNO, textRollno.getText().toString());
    }


}
