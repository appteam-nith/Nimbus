package com.nith.appteam.nimbus.Fragment;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.Streams;
import com.nith.appteam.nimbus.Adapter.ProfileEventAdapter;
import com.nith.appteam.nimbus.Model.ProfileEventModel;
import com.nith.appteam.nimbus.Fragment.ProfileTab1.ProfileEventDataModel;
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.ApiInterface;
import com.nith.appteam.nimbus.Utils.MyApplication;
import com.nith.appteam.nimbus.Utils.SharedPref;
import com.nith.appteam.nimbus.Utils.Util;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by aditya on 13/2/17.
 */

public class ProfileTab1 extends Fragment {


    private static final String EVENT_LIST ="event_list" ;


    private RecyclerView recyclerView;
    private ProfileEventAdapter profileEventAdapter;
    private ArrayList<ProfileTab1.ProfileEventDataModel> list = new ArrayList<>();
    private SharedPref sharedPref;
    private ProgressBar progress;
    private TextView textView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_profiletab1, container, false);


        ProfileEventDataModel profileEventDataModel;
        sharedPref = new SharedPref(getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        progress = (ProgressBar) view.findViewById(R.id.progress);
        textView = (TextView) view.findViewById(R.id.text);


        profileEventAdapter = new ProfileEventAdapter(list,getContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(profileEventAdapter);


        profileEventDataModel = new ProfileEventDataModel("","","");

        if(savedInstanceState==null){

            progress.setVisibility(view.VISIBLE);
            profileEventDataModel.profileEventList(sharedPref.getUserId());

        }
        else {
            list = savedInstanceState.getParcelableArrayList(EVENT_LIST);
            if(list!=null){
                recyclerView.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);

            }

        }



        return view;
    }



    public class ProfileEventDataModel implements Parcelable{

        @SerializedName("_id")
        private String _id;

        @SerializedName("team")
        private String team;

        @SerializedName("name")
        private String name;


        public ProfileEventDataModel(String _id, String team, String name) {
            this._id = _id;
            this.team = team;
            this.name = name;
        }

        protected ProfileEventDataModel(Parcel in){
            _id = in.readString();
            team = in.readString();
            name = in.readString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

            dest.writeString(_id);
            dest.writeString(team);
            dest.writeString(name);
        }


        private final Creator<ProfileEventDataModel> CREATOR = new Creator<ProfileEventDataModel>() {
            @Override
            public ProfileEventDataModel createFromParcel(Parcel source) {
                return new ProfileEventDataModel(source);
            }

            @Override
            public ProfileEventDataModel[] newArray(int size) {
                return new ProfileEventDataModel[size];
            }
        };


        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getTeam() {
            return team;
        }

        public void setTeam(String team) {
            this.team = team;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        private void profileEventList(String id){

            ApiInterface mAPI = Util.getRetrofitService();
            Call<ProfileEventModel> mService = mAPI.profileEventList(id);


            mService.enqueue(new Callback<ProfileEventModel>() {
                @Override
                public void onResponse(Call<ProfileEventModel> call, Response<ProfileEventModel> response) {

                    recyclerView.setVisibility(View.VISIBLE);
                    progress.setVisibility(View.GONE);
                    if(response!=null && response.isSuccess()){
                        if(response.body().getEvent()!=null){
                            if(response.body().getEvent().size()>0){

                                list.addAll(response.body().getEvent());

                            }
                            else{

                                Toast.makeText(MyApplication.getAppContext(),"No Events Registered",Toast.LENGTH_LONG).show();
                            }
        
                        }
                        else {

                            Toast.makeText(MyApplication.getAppContext(),"Unable to fetch Event List",Toast.LENGTH_LONG).show();

                        }
        
                    }
                    else {
        
                        Toast.makeText(MyApplication.getAppContext(),"Unable to fetch Event List",Toast.LENGTH_SHORT).show();
                    }
                }
        
                @Override
                public void onFailure(Call<ProfileEventModel> call, Throwable t) {
        
                    t.printStackTrace();
                    progress.setVisibility(View.GONE);
                    Toast.makeText(MyApplication.getAppContext(), "Please check your network connection and internet permission", Toast.LENGTH_LONG).show();
                }
            });
        }



    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(list!=null)
            outState.putParcelableArrayList(EVENT_LIST,list);
    }
}



