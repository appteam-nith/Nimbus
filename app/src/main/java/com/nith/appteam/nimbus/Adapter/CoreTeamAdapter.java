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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.nith.appteam.nimbus.Model.CoreTeamItem;
import com.nith.appteam.nimbus.R;

import java.util.ArrayList;

/**
 * Created by Aman Pratap on 08-Feb-17.
 */

public class CoreTeamAdapter extends RecyclerView.Adapter<CoreTeamAdapter.ViewHolder>{

    ArrayList<CoreTeamItem> array_list;
    Context context;

    public CoreTeamAdapter(ArrayList<CoreTeamItem> array_list, Context context){
        this.array_list=array_list;
        this.context=context;
    }

    @Override
    public void onBindViewHolder(final ViewHolder view_Holder, int i){
        if(!(array_list.get(i).name.isEmpty())){
            view_Holder.name.setText(array_list.get(i).name);
        }

        if(!(array_list.get(i).designation.isEmpty())){
            view_Holder.designation.setText(array_list.get(i).designation);
        }

        if(!array_list.get(i).url.isEmpty()){
            Glide.with(context).load(array_list.get(i).url).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.person_icon).error(R.drawable.person_icon).into(new ImageViewTarget<Bitmap>(view_Holder.imageView) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(context.getResources(),resource);
                    drawable.setCircular(true);
                    view_Holder.imageView.setImageDrawable(drawable);
                }
            });
        }
    }

    @Override
    public int getItemCount(){
        return array_list.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup view_group, int i) {
        View view = LayoutInflater.from(view_group.getContext()).inflate(R.layout.card_view_layout, view_group, false);
        ViewHolder view_holder = new ViewHolder(view);
        return view_holder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView name, designation;
        ImageView imageView;
        public ViewHolder(View v){
            super(v);
            this.name = (TextView)v.findViewById(R.id.core_team_name);
            this.designation = (TextView)v.findViewById(R.id.core_team_designation);
            imageView = (ImageView)v.findViewById(R.id.core_team_image_view);
        }
    }
}
