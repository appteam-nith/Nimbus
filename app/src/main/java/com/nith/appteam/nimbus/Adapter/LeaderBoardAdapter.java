package com.nith.appteam.nimbus.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.nith.appteam.nimbus.Activity.LeaderBoardActivity;
import com.nith.appteam.nimbus.R;

import java.util.ArrayList;

/**
 * Created by jaykay12 on 17/2/17.
 */
public class LeaderBoardAdapter extends  RecyclerView.Adapter<LeaderBoardAdapter.LeaderBoardViewHolder> {
    ArrayList<LeaderBoardActivity.LeaderBoardUserModel> users;
    Context context;

    public LeaderBoardAdapter(ArrayList<LeaderBoardActivity.LeaderBoardUserModel> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @Override
    public LeaderBoardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_leaderboard,parent,false);
        LeaderBoardViewHolder leaderBoardViewHolder = new LeaderBoardViewHolder(view,users,context);

        return leaderBoardViewHolder;
    }

    @Override
    public void onBindViewHolder(LeaderBoardViewHolder holder, int position) {
        LeaderBoardActivity.LeaderBoardUserModel user=users.get(position);

        holder.username.setText(user.getName().toString());
        holder.score.setText("Score: "+Integer.toString(user.getSets().getScore()));
        Glide.with(context).load(user.getPhoto()).into(holder.photo);

        final ImageView imageView=holder.photo;

        Glide.with(context).load(user.getPhoto()).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });

        Integer prsnlscore = user.getSets().getScore();
        if(prsnlscore>=800) holder.useraward.setImageResource(R.drawable.trophy_gold);
        else if(prsnlscore>=600) holder.useraward.setImageResource(R.drawable.trophy_silver);
        else if(prsnlscore>=450) holder.useraward.setImageResource(R.drawable.trophy_bronze);
        else if(prsnlscore>=300) holder.useraward.setImageResource(R.drawable.trophy_goldbadge);
        else if(prsnlscore>=150) holder.useraward.setImageResource(R.drawable.trophy_silverbadge);
        else if(prsnlscore>0) holder.useraward.setImageResource(R.drawable.trophy_bronzebadge);
        else if(prsnlscore==0) holder.useraward.setImageResource(R.drawable.trophy_participation);
        holder.sets.setText("Sets: "+Integer.toString(user.getSets().getSets()));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class LeaderBoardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView username;
        ImageView photo;
        TextView score;
        TextView sets;
        ImageView useraward;
        Context context;

        public LeaderBoardViewHolder(View view, ArrayList<LeaderBoardActivity.LeaderBoardUserModel> users, Context context) {
            super(view);
            view.setOnClickListener(this);
            username=(TextView)view.findViewById(R.id.leader_username);
            photo = (ImageView)view.findViewById(R.id.leader_pic);
            score=(TextView)view.findViewById(R.id.leader_score);
            sets=(TextView)view.findViewById(R.id.leader_sets);
            useraward=(ImageView)view.findViewById(R.id.leader_award);
            this.context=context;
        }

        @Override
        public void onClick(View view) {
            //what to do on click?
        }
    }
}
