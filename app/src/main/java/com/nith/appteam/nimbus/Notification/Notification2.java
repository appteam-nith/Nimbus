package com.nith.appteam.nimbus.Notification;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nith.appteam.nimbus.Notification.DbHelper;
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.SharedPref;


/**
 * Created by jatin on 7/3/17.
 */

public class Notification2 extends AppCompatActivity {
    TextView title,body,launch_url;
    ImageView big_picture;
    CardView sec_card,thrd_card;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SharedPref pref= new SharedPref(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_post_expand);
        Toolbar toolbar= (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Notifications");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DbHelper dbHelper = new DbHelper(this);
        title = (TextView) findViewById(R.id.not2_title);
        body = (TextView) findViewById(R.id.not2_body);
        launch_url = (TextView) findViewById(R.id.launch_url);
        sec_card=(CardView)findViewById(R.id.secondcard);
        thrd_card=(CardView)findViewById(R.id.thiredcard);
        big_picture = (ImageView) findViewById(R.id.not2_big_picture);
        String id = getIntent().getStringExtra("id");
        //String id = bundle.getString("id",null);

        Cursor cursor = dbHelper.homeposteinnerdata(id);
        cursor.moveToFirst();
        String ftitle =cursor.getString(cursor.getColumnIndex("title"));
        String fbody =cursor.getString(cursor.getColumnIndex("body"));
        String fbig_pic=cursor.getString(cursor.getColumnIndex("bigpicture"));
        final String l_url=cursor.getString(cursor.getColumnIndex("launchurl"));
        Log.d("sdgvajdsf","getstringextras"+body+"_"+fbig_pic+"_"+l_url);
        title.setText(ftitle);
        body.setText(fbody);
        if(l_url!=null) {
            launch_url.setText(l_url);
            launch_url.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(l_url));
                    startActivity(i);
                }
            });
        }
        else {
            thrd_card.setVisibility(View.GONE);
        }
        final ProgressBar progressBar1 = (ProgressBar) findViewById(R.id.progress_not2);
        if(fbig_pic!=null  ) {

            Glide.with(getApplicationContext()).load(fbig_pic).diskCacheStrategy(DiskCacheStrategy.SOURCE).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    progressBar1.setVisibility(View.GONE);
                    return false;
                }
            }).into(big_picture);
        }
        else {
            sec_card.setVisibility(View.GONE);
        }


    }
}


