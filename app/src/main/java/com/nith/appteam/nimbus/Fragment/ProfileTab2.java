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
    private ProgressBar progress;
    private SharedPref sharedPref;
    private CardView cardView;
    private LinearLayout rollnoLayout;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){


        View view = inflater.inflate(R.layout.fragment_profiletab2, container, false);

        ProfileBasicDataModel profileBasicDataModel;

        sharedPref = new SharedPref(getContext());

        cardView = (CardView) view.findViewById(R.id.cardview);
        textName = (TextView) view.findViewById(R.id.name);
        textRollno = (TextView) view.findViewById(R.id.rollno);
        textEmail = (TextView) view.findViewById(R.id.email);

        progress = (ProgressBar) view.findViewById(R.id.progress);

        rollnoLayout = (LinearLayout) view.findViewById(R.id.rollnoLayout);


        profileBasicDataModel=new ProfileBasicDataModel("","","","");

        if(savedInstanceState==null){

            progress.setVisibility(view.VISIBLE);

            profileBasicDataModel.profileBasicInfo(sharedPref.getUserId());

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




    public class ProfileBasicDataModel implements Parcelable{
        @SerializedName("name")
        private String name;

        @SerializedName("roll_no")
        private String rollno;

        @SerializedName("email")
        private String email;

        @SerializedName("photo")
        private String photo;

        public ProfileBasicDataModel(String name, String rollno, String email, String photo) {
            this.name = name;
            this.rollno = rollno;
            this.email = email;
            this.photo = photo;
        }


        protected ProfileBasicDataModel(Parcel in){
            name = in.readString();
            rollno = in.readString();
            email = in.readString();
            photo = in.readString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

            dest.writeString(name);
            dest.writeString(rollno);
            dest.writeString(email);
            dest.writeString(photo);
        }


        private final Creator<ProfileBasicDataModel> CREATOR = new Creator<ProfileBasicDataModel>() {
            @Override
            public ProfileBasicDataModel createFromParcel(Parcel source) {
                return new ProfileBasicDataModel(source);
            }

            @Override
            public ProfileBasicDataModel[] newArray(int size) {
                return new ProfileBasicDataModel[size];
            }
        };


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRollno() {
            return rollno;
        }

        public void setRollno(String rollno) {
            this.rollno = rollno;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }


        private void profileBasicInfo(String id){

            ApiInterface mAPI = Util.getRetrofitService();
            Call<ProfileDataModel> mService = mAPI.profileBasicInfo(id);

            mService.enqueue(new Callback<ProfileDataModel>() {
                @Override
                public void onResponse(Call<ProfileDataModel> call, Response<ProfileDataModel> response) {
                    if(response!=null && response.isSuccess()){
                        if(response.body().isSuccess()){
                            ProfileDataModel model = response.body();

                            //For Testing
                            Log.v("RESPONSE SUCCESS"," "+model.getRollno()+" "+model.getName()+" "+model.getEmail()+ " "+model.getPhoto());

                            if(model!=null){

                                sharedPref.setUserName(model.getName());
                                sharedPref.setUserEmail(model.getEmail());
                                sharedPref.setUserRollno(model.getRollno());
                                sharedPref.setUserPicUrl(model.getPhoto());

                                progress.setVisibility(View.GONE);
                                cardView.setVisibility(View.VISIBLE);

                                if(model.getRollno()==null || model.getRollno().isEmpty()){

                                    rollnoLayout.setVisibility(View.GONE);

                                }

                                textName.setText(model.getName());
                                textEmail.setText(model.getEmail());
                                textRollno.setText(model.getRollno());

                            }

                            else {

                                progress.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "Please check your Network Connection and Internet Permissions", Toast.LENGTH_LONG).show();
                            }
                        }

                        else {

                            progress.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "Please check your Network Connection and Internet Permissions", Toast.LENGTH_LONG).show();


                        }
                    }
                }

                @Override
                public void onFailure(Call<ProfileDataModel> call, Throwable t) {

                    progress.setVisibility(View.GONE);
                    t.printStackTrace();
                    if(getActivity()!=null) {
                        Toast.makeText(getActivity(), "Please check your network connection and internet permission", Toast.LENGTH_LONG).show();
                    }
                }
            });


        }







    }


    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString(USER_NAME,textName.getText().toString());
        outState.putString(USER_EMAIL,textEmail.getText().toString());
        outState.putString(USER_ROLLNO,textRollno.getText().toString());
    }



}
