package com.nith.appteam.nimbus.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nith.appteam.nimbus.Adapter.ProfileEventAdapter;
import com.nith.appteam.nimbus.Model.ProfileEventModel;
import com.nith.appteam.nimbus.R;

import java.util.ArrayList;

/**
 * Created by aditya on 13/2/17.
 */

public class ProfileTab1 extends Fragment {


    private RecyclerView recyclerView;
    private ProfileEventAdapter profileEventAdapter;
    private ArrayList<ProfileEventModel> list = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_profiletab1, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.list);

        list.add(new ProfileEventModel("Hackathon","AppTeam"));
        list.add(new ProfileEventModel("InApp Quiz","AppTeam"));

        profileEventAdapter = new ProfileEventAdapter(list, getContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(profileEventAdapter);


        return view;
    }


}
