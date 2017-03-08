package com.nith.appteam.nimbus.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nith.appteam.nimbus.Model.TeamEventList;
import com.nith.appteam.nimbus.R;

import java.util.ArrayList;

/**
 * Created by sahil on 7/3/17.
 */
public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.viewHolder>

{
    private Context context;
    private ArrayList<TeamEventList.Projects> list=new ArrayList<>();

    public ProjectAdapter(Context context) {
        this.context = context;
    }


    public  void refresh(ArrayList<TeamEventList.Projects> list){
        this.list=list;
        notifyDataSetChanged();
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_team,parent,false);
        return new  viewHolder(v);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {

        TeamEventList.Projects e=list.get(position);
        holder.title.setText(e.getName());
        Glide.with(context).load(e.getPhoto()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.nimbuslogo).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static  class  viewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView title;

        public viewHolder(View itemView) {
            super(itemView);

            imageView= (ImageView) itemView.findViewById(R.id.image_team);
            title= (TextView) itemView.findViewById(R.id.text_name_team);
        }
    }
}
