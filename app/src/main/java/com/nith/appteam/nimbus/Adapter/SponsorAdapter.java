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
import com.nith.appteam.nimbus.Model.Sponsor;
import com.nith.appteam.nimbus.R;

import java.util.ArrayList;

/**
 * Created by sahil on 16/3/17.
 */

public class SponsorAdapter extends RecyclerView.Adapter<SponsorAdapter.viewHolder>{

    private Context context;
    private ArrayList<Sponsor> list=new ArrayList<>();

    public  void refresh(ArrayList<Sponsor> list){
        this.list=list;
        notifyDataSetChanged();
    }

    public SponsorAdapter(Context context) {
        this.context = context;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_layout,parent,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        if(list!=null) {
            Sponsor s = list.get(position);
            if (s != null) {
                holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                holder.coreteam.setVisibility(View.GONE);
                if (s.getName() != null && !s.getName().isEmpty())
                    holder.textView.setText(s.getName());
                if (s.getImg_url() != null && !s.getImg_url().isEmpty())
                    Glide.with(context).load(s.getImg_url()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.person_icon).into(holder.imageView);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static  class  viewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView textView,coreteam;

        public viewHolder(View itemView) {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.core_team_image_view);
            textView= (TextView) itemView.findViewById(R.id.core_team_designation);
            coreteam = (TextView) itemView.findViewById(R.id.core_team_name);
        }
    }
}
