package com.nith.appteam.nimbus.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nith.appteam.nimbus.R;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by shasha on 17/2/17.
 */

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder> {

    private TextView recvTv;
    private ImageView imageView;

    public MainRecyclerAdapter() {}

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_recycler_main,parent,false));
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        String[] recvText = new String[]{"Quiz", "Sponsors", "Core Team", "Teams", "Map", "About Nimbus","Workshop","NewsFeed","GalleryDetail"};
        int[] recvImages = {R.drawable.main_quiz,R.drawable.main_sponsor,R.drawable.main_core_team,R.drawable.main_teams,R.drawable.main_map,R.drawable.main_about,R.drawable.main_workshop,R.drawable.main_workshop,R.drawable.main_about};
        recvTv.setText(recvText[position]);
        imageView.setImageResource(recvImages[position]);
    }

    @Override
    public int getItemCount() {
        return 9;
    }

    public class MainViewHolder extends RecyclerView.ViewHolder{
        public MainViewHolder(View itemView) {
            super(itemView);
            recvTv = (TextView)itemView.findViewById(R.id.recv_tv);
            imageView = (ImageView) itemView.findViewById(R.id.recv_image);
        }
    }
}
