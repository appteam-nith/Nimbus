package com.nith.appteam.nimbus.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nith.appteam.nimbus.R;

/**
 * Created by shasha on 17/2/17.
 */

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder> {
    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_recycler_main,parent,false));
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder{

        public MainViewHolder(View itemView) {
            super(itemView);
        }
    }
}
