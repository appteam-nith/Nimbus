package com.nith.appteam.nimbus.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nith.appteam.nimbus.CustomView.AspectRatioImageView;
import com.nith.appteam.nimbus.R;

/**
 * Created by sahil on 4/3/17.
 */

public class GalleryFragment extends Fragment {
    private static final String TITLE="title";
    private static final String IMAGE_URL="imageUrl";
    private String title,imageUrl;
    private AspectRatioImageView galleryImageView;
    private TextView titleTextView;
    private ProgressBar progressBar;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title=getArguments().getString(TITLE);
        imageUrl=getArguments().getString(IMAGE_URL);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.item_gallery,container,false);
        galleryImageView= (AspectRatioImageView) v.findViewById(R.id.item_image_gallery);
        titleTextView= (TextView) v.findViewById(R.id.item_text_gallery);
        progressBar= (ProgressBar) v.findViewById(R.id.progress);
        if(title!=null&&!title.isEmpty())
        titleTextView.setText(title);
        Glide.with(this).load(imageUrl).diskCacheStrategy(DiskCacheStrategy.ALL).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
               progressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(galleryImageView);
        return v;
    }

    public static GalleryFragment newInstance(String title,String imageUrl){
        GalleryFragment galleryFragment=new GalleryFragment();
        Bundle bundle=new Bundle();
        bundle.putString(TITLE,title);
        bundle.putString(IMAGE_URL,imageUrl);
        galleryFragment.setArguments(bundle);
        return galleryFragment;
    }
}
