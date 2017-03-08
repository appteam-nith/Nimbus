package com.nith.appteam.nimbus.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        holder.rollno.setText(user.getRollNo().toString());
        holder.score.setText("Score: "+Integer.toString(user.getSets().getScore()));
        holder.sets.setText("Sets: "+Integer.toString(user.getSets().getSets()));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class LeaderBoardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView username;
        TextView rollno;
        TextView score;
        TextView sets;
        Context context;
        public LeaderBoardViewHolder(View view, ArrayList<LeaderBoardActivity.LeaderBoardUserModel> users, Context context) {
            super(view);
            view.setOnClickListener(this);
            username=(TextView)view.findViewById(R.id.leader_username);
            rollno=(TextView)view.findViewById(R.id.leader_rollno);
            score=(TextView)view.findViewById(R.id.leader_score);
            sets=(TextView)view.findViewById(R.id.leader_sets);
            this.context=context;
        }

        @Override
        public void onClick(View view) {
            //what to do on click?
        }
    }
}
