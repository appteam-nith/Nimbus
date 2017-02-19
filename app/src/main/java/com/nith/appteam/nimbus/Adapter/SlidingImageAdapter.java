package com.nith.appteam.nimbus.Adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nith.appteam.nimbus.R;

import java.util.ArrayList;

/**
 * Created by shasha on 18/2/17.
 */

public class SlidingImageAdapter extends PagerAdapter {
    //As discussed before the first two pics will not loaded from internet reason so that during poor connection. The page doesn't looks empty.
    private int[] IMAGES = new int[]{R.drawable.slide_3, R.drawable.slide_4};
    private LayoutInflater inflater;
    private ArrayList<String> urlList=new ArrayList<>();
    private Context context;
    private final String LOG_TAG = getClass().getSimpleName();

    public SlidingImageAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void refresh(ArrayList<String> list){
        urlList=list;
        notifyDataSetChanged();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES.length+urlList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.sliding_image_layout, view, false);
        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.image);
        if(position==0 || position==1)
        imageView.setImageResource(IMAGES[position]);
        else {
            Glide.with(context).load(urlList.get(position)).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.slide_4).into(imageView);
        }
        view.addView(imageLayout, 0);
        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

}
