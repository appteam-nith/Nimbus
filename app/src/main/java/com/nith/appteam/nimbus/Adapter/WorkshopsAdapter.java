package com.nith.appteam.nimbus.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Activity.WorkshopDetail;
import com.nith.appteam.nimbus.Model.WorkshopItem;

import java.util.ArrayList;

import static com.nith.appteam.nimbus.Adapter.WorkshopsAdapter.ViewHolder.ACTIVITY;
import static com.nith.appteam.nimbus.Adapter.WorkshopsAdapter.ViewHolder.WORKSHOP;
import static com.nith.appteam.nimbus.Adapter.WorkshopsAdapter.ViewHolder.WORKSHOP_ID;
import static com.nith.appteam.nimbus.Adapter.WorkshopsAdapter.ViewHolder.WORKSHOP_NAME;

/**
 * Created by ndodwaria on 2/7/17.
 */

public class WorkshopsAdapter extends RecyclerView.Adapter<WorkshopsAdapter.ViewHolder>{
    ArrayList<WorkshopItem> workshopsItemArrayList;
    Context context;

    public WorkshopsAdapter(Context context) {
        this.context = context;
        this.workshopsItemArrayList=new ArrayList<>();
    }

    public WorkshopsAdapter(ArrayList<WorkshopItem> workshopsItemArrayList, Context context) {
        this.workshopsItemArrayList= workshopsItemArrayList;
        this.context = context;
    }

    public void refresh(ArrayList<WorkshopItem> items){
        this.workshopsItemArrayList= items;

        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        Log.v("Size",""+workshopsItemArrayList.size());
        return workshopsItemArrayList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workshops_cardview,parent,false);
        ViewHolder view_holder = new ViewHolder(view);
        return view_holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //Log.v("Work_name",workshopsItemArrayList.get(position).getName()+" ******************########***************");
        if(!(workshopsItemArrayList.get(position).getName().isEmpty())){
            holder.workshop_name.setText(workshopsItemArrayList.get(position).getName());
        }
        if(!(workshopsItemArrayList.get(position).getImgUrl()==null)){
            Glide.with(context).load(workshopsItemArrayList.get(position).getImgUrl()).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.w1).into(new ImageViewTarget<Bitmap>(holder.image_url) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable drawable= RoundedBitmapDrawableFactory.create(context.getResources(),resource);
                    //drawable.setCircular(true);
                    holder.image_url.setImageDrawable(drawable);
                }
            });
        }
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context,WorkshopDetail.class);
                i.putExtra(WORKSHOP_ID,workshopsItemArrayList.get(position).getId());
                i.putExtra(WORKSHOP_NAME,workshopsItemArrayList.get(position).getName());
                i.putExtra(ACTIVITY,WORKSHOP);
                context.startActivity(i);
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView workshop_name;
        public ImageView image_url;
        public CardView card_view;
        public static final String WORKSHOP_ID ="id" ;
        public static final String WORKSHOP_NAME ="wname",ACTIVITY="activity";
        public static final String WORKSHOP="workshop", EVENT="event";
        public String wname;
        public ViewHolder(View v){
            super(v);
            this.image_url = (ImageView)v.findViewById(R.id.workshop_image);
            this.workshop_name = (TextView)v.findViewById(R.id.workshop_name);
            this.card_view =(CardView)v.findViewById(R.id.cardview_workshops);
        }
    }


}
