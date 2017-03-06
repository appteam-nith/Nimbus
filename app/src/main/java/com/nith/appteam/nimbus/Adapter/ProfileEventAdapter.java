package com.nith.appteam.nimbus.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.nith.appteam.nimbus.Fragment.ProfileTab1;
import com.nith.appteam.nimbus.Fragment.ProfileTab1.ProfileEventDataModel;
import com.nith.appteam.nimbus.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aditya on 14/2/17.
 */

public class ProfileEventAdapter extends RecyclerView.Adapter<ProfileEventAdapter.viewHolder> {


    private List<ProfileTab1.ProfileEventDataModel> arrayList;
    private Context context;

    public ProfileEventAdapter(List<ProfileTab1.ProfileEventDataModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }


    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_event_profile,parent,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {


        ProfileTab1.ProfileEventDataModel model =  arrayList.get(position);
        String str1 = model.getName();
        String str2 = "By " + model.getTeam();
        holder.eventText.setText(str1);
        holder.clubText.setText(str2);


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView eventText;
        TextView clubText;

        public viewHolder(View itemView) {
            super(itemView);
            cardView= (CardView) itemView.findViewById(R.id.layout_card);
            eventText= (TextView) itemView.findViewById(R.id.eventName);
            clubText= (TextView) itemView.findViewById(R.id.clubName);
        }
    }



}
