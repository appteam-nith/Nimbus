package com.nith.appteam.nimbus.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.nith.appteam.nimbus.Model.Likecount;
import com.nith.appteam.nimbus.Model.NewsFeed;
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.ApiInterface;
import com.nith.appteam.nimbus.Utils.MyApplication;
import com.nith.appteam.nimbus.Utils.SharedPref;
import com.nith.appteam.nimbus.Utils.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sahil on 9/2/17.
 */

public class NewsFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private Context mContext;
    private ArrayList<NewsFeed> list_card=new ArrayList<>();
    public static final int FOOTER_VIEW = 1;
    public static final int NORMAL_VIEW = 2;
    public String truncated;
    public String non_truncated;
    private View view;
    private int l,count=0;




    public NewsFeedAdapter(Context mContext) {

        this.mContext = mContext;
    }


    public  void  refresh(ArrayList<NewsFeed> list){
        list_card=list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FOOTER_VIEW&&list_card.size()!=0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress, parent, false);
            return new footerView(view);
        }
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof MyViewHolder&&getItemViewType(position)!=FOOTER_VIEW)
        {
            l=position;
            final MyViewHolder h=(MyViewHolder) holder;
            final NewsFeed card = list_card.get(position);

            if(card!=null){
                if(card.getDescription()!=null)
                if(card.getDescription().length()>30){

                    h.see_more.setVisibility(View.VISIBLE);
                    String original = card.getDescription().toString();
                    truncated = original.substring(0,29);
                    non_truncated = original.substring(30,original.length()-1);
                    h.user_msg.setText(truncated);
                    h.see_more.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            h.user_msg.append( non_truncated);
                            h.see_less.setVisibility(View.VISIBLE);
                            h.see_more.setVisibility(View.GONE);
                        }
                    });

                    h.see_less.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            h.user_msg.setText(truncated);
                            h.see_less.setVisibility(View.GONE);
                            h.see_more.setVisibility(View.VISIBLE);
                        }
                    });

                }
                else if(card.getDescription()!=null&&!card.getDescription().isEmpty())
                    h.user_msg.setText(card.getDescription());
                if(card.getUsername()!=null&&!card.getUsername().isEmpty())
                    h.user_name.setText(card.getUsername());

                if(card.getTitle()!=null&&!card.getTitle().isEmpty())
                    h.title.setText(card.getTitle());

                if(card.getLikes()>=0)
                    h.no_of_likes.setText(""+card.getLikes());

                if(card.getDate()!=null&&!card.getDate().isEmpty()){

                    String od = card.getDate();
                    String fd = od.substring(0,10);
                    SimpleDateFormat odFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                    SimpleDateFormat ndFormat = new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
                    try {
                        Date date = odFormat.parse(fd);
                        String nd = ndFormat.format(date);
                        h.post_date.setText(nd);
                    }
                    catch (ParseException e){
                        Toast.makeText(MyApplication.getAppContext(),"can't fetch post date",Toast.LENGTH_SHORT).show();
                    }

                }

                if(card.getPhoto()!=null&&!card.getPhoto().isEmpty())
                    Glide.with(mContext).load(card.getPhoto()).into(h.post_img);
                Log.v("stwtus",""+card.isStatus());
               if(card.isStatus())
                    h.lyk_status.setLiked(true);
                else {
                    h.lyk_status.setLiked(false);
                }
                h.lyk_status.setOnLikeListener(new OnLikeListener() {
                    @Override
                    public void liked(LikeButton likeButton) {

                        ApiInterface mApiService = Util.getRetrofitService();
                        Call<Likecount> mservice = mApiService.likecount(card.get_id(),new SharedPref(MyApplication.getAppContext()).getUserId());

                        mservice.enqueue(new Callback<Likecount>() {
                            @Override
                            public void onResponse(Call<Likecount> call, Response<Likecount> response) {
                                Likecount likes = response.body();
                                if (likes != null && response.isSuccess()) {
                                    if (likes.isSuccess()) {
                                        card.setStatus(true);
                                        Log.d("id",card.get_id());
                                        Toast.makeText(mContext, "Post Liked", Toast.LENGTH_SHORT).show();
                                        card.setLikes(likes.getLikes());
                                        h.no_of_likes.setText("" + likes.getLikes());
                                    } else {
                                        Toast.makeText(mContext, "Internal Error", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Likecount> call, Throwable t) {
                                Toast.makeText(mContext, "Check Your Internet Connectivity and Permissions", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void unLiked(LikeButton likeButton) {
                        ApiInterface mApiService = Util.getRetrofitService();
                        Call<Likecount> mservice = mApiService.likecount(card.get_id(),new SharedPref(MyApplication.getAppContext()).getUserId());

                        mservice.enqueue(new Callback<Likecount>() {
                            @Override
                            public void onResponse(Call<Likecount> call, Response<Likecount> response) {
                                Likecount likes = response.body();
                                if (likes != null && response.isSuccess()) {
                                    if (likes.isSuccess()) {
                                        card.setStatus(false);
                                        Log.d("id",card.get_id());
                                        Toast.makeText(mContext, "Post Disliked", Toast.LENGTH_SHORT).show();
                                        card.setLikes(likes.getLikes());
                                        h.no_of_likes.setText("" + likes.getLikes());
                                    } else {
                                        Toast.makeText(mContext, "Internal Error", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Likecount> call, Throwable t) {

                                Toast.makeText(mContext, "Check Your Internet Connectivity and Permissions", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });



            }


        }

    }

    @Override
    public int getItemCount() {

        return list_card.size();
    }





    public class MyViewHolder extends RecyclerView.ViewHolder {
          public TextView user_name, no_of_likes, user_msg, post_date ,title;
        final public ImageView post_img;
        public TextView see_more,see_less;

        final public com.like.LikeButton lyk_status;
        int count;


        public MyViewHolder(View view) {
            super(view);
            user_name = (TextView) view.findViewById(R.id.user_name);
            no_of_likes = (TextView) view.findViewById(R.id.no_of_likes);
            post_img = (ImageView) view.findViewById(R.id.post_img);
            post_date = (TextView) view.findViewById(R.id.post_date);
            user_msg = (TextView) view.findViewById(R.id.user_msg);
            see_more = (TextView) view.findViewById(R.id.see_more);
          lyk_status = (com.like.LikeButton) view.findViewById(R.id.lyk_status);
            title = (TextView)view.findViewById(R.id.post_title);
            see_less = (TextView) view.findViewById(R.id.see_less);


        }
    }



    @Override
    public int getItemViewType(int position) {
        return list_card.get(position)==null?FOOTER_VIEW:NORMAL_VIEW;
    }

    public static class footerView extends RecyclerView.ViewHolder {
        public footerView(View itemView) {
            super(itemView);
        }
    }

}
