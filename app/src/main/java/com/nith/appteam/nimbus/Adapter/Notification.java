package com.nith.appteam.nimbus.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
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
import com.nith.appteam.nimbus.Model.HomePostsModel;
import com.nith.appteam.nimbus.R;

import java.util.List;

/**
 * Created by jatin on 7/3/17.
 */

public class Notification extends RecyclerView.Adapter<Notification.viewHolder> {

    private List<HomePostsModel> arrayList;
    private Context context;

    public Notification(List<HomePostsModel> arrayList, Context context) {
        this.arrayList=arrayList;
        this.context = context;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_model_card,parent,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(final viewHolder holder, int position) {

        HomePostsModel home_post = arrayList.get(position);
        holder.title.setText(home_post.getTitle());
        holder.notification_id.setText(home_post.getNotification_id());
        String ab="android.R.drawable."+home_post.getSmall_icon();
        Log.v("ab:",""+ab+"small_icon"+home_post.getSmall_icon());
        if (home_post.getSmall_icon() == null || home_post.getSmall_icon().isEmpty() || home_post.getSmall_icon().length() == 0) {
            Glide.with(context).load(android.R.drawable.ic_dialog_alert).into(holder.small_icon);
        } else {
            Glide.with(context).load(R.drawable.logo_b_t).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).error(android.R.drawable.ic_dialog_alert).into(new ImageViewTarget<Bitmap>(holder.small_icon) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    drawable.setCircular(true);
                    holder.small_icon.setImageDrawable(drawable);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder{
        ImageView small_icon;
        TextView title,notification_id;

        public viewHolder(View itemView) {
            super(itemView);
            small_icon=(ImageView) itemView.findViewById(R.id.small_icon);

            title=(TextView)itemView.findViewById(R.id.not_title);

            notification_id=(TextView)itemView.findViewById(R.id.not_id);
        }
    }
}



