package com.nith.appteam.nimbus.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nith.appteam.nimbus.Activity.GalleryActivity;
import com.nith.appteam.nimbus.Activity.GalleryDetailActivity;
import com.nith.appteam.nimbus.CustomView.AspectRatioImageView;
import com.nith.appteam.nimbus.CustomView.GalleryView;
import com.nith.appteam.nimbus.Model.Gallery;
import com.nith.appteam.nimbus.Model.GalleryDetail;
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.Connection;

import java.util.ArrayList;

/**
 * Created by sahil on 5/3/17.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.viewHolder> {

    private Context context;
    private static final String ID="id";

    public GalleryAdapter(Context context) {
        this.context = context;
    }

    private ArrayList<Gallery> list=new ArrayList<>();

    public  void refresh(ArrayList<Gallery> list){
        this.list=list;
        notifyDataSetChanged();
    }
    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom_gallery,parent,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(final viewHolder holder, final int position) {
        final Gallery g=list.get(position);
        if(g.getTitle()!=null&&!g.getTitle().isEmpty()){
            holder.galleryView.setTextNormalView(g.getTitle());
        }
        if(g.getHeaderImageUrl()!=null&&!g.getHeaderImageUrl().isEmpty()){
            holder.galleryView.setImageNormalView(g.getHeaderImageUrl());
        }
        if(g.getExpandedImageUrl()!=null&&g.getExpandedImageUrl().size()>0){
            Log.d("h","kk");
            ArrayList<GalleryDetail> l=g.getExpandedImageUrl();
            holder.galleryView.setImageExpandedView(l.get(0).getImageUrl(),l.get(1).getImageUrl(),l.get(2).getImageUrl());
        }
        holder.galleryView.setMoreClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,GalleryDetailActivity.class);
                i.putExtra(ID,g.getId());
                context.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder{
        private GalleryView galleryView;
        private ImageView expand;

        public viewHolder(View itemView) {
            super(itemView);
            galleryView= (GalleryView) itemView.findViewById(R.id.galleryView);
        }
    }
}
