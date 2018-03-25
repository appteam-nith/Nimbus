package com.nith.appteam.nimbus.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nith.appteam.nimbus.Activity.RegisterEvent;
import com.nith.appteam.nimbus.Model.TeamEventList;
import com.nith.appteam.nimbus.R;

import java.util.ArrayList;

/**
 * Created by jatin on 25/3/18.
 */

public class EventAdapterList extends RecyclerView.Adapter<EventAdapterList.viewHolder> {

    private Context context;
    private ArrayList<TeamEventList.Event> list=new ArrayList<>();

    public EventAdapterList(Context context) {
        this.context = context;

    }
    public  void refresh(ArrayList<TeamEventList.Event> list){
        this.list=list;
        notifyDataSetChanged();
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_event,parent,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        TeamEventList.Event e=list.get(position);
        holder.title.setText(e.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public static  class  viewHolder extends RecyclerView.ViewHolder{

        private TextView title;

        public viewHolder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.event1_text);
        }
    }
}
