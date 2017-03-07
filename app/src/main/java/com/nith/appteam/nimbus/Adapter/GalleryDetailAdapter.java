package com.nith.appteam.nimbus.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.nith.appteam.nimbus.Fragment.GalleryFragment;
import com.nith.appteam.nimbus.Model.GalleryDetail;

import java.util.ArrayList;

/**
 * Created by sahil on 4/3/17.
 */

public class GalleryDetailAdapter extends FragmentStatePagerAdapter{
    private ArrayList<GalleryDetail> galleryDetailArrayList =new ArrayList<>();

    public GalleryDetailAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        GalleryDetail g= galleryDetailArrayList.get(position);
        return GalleryFragment.newInstance(g.getTitle(),g.getImageUrl());
    }

    @Override
    public int getCount() {
        return galleryDetailArrayList.size();
    }

    public void refresh(ArrayList<GalleryDetail> list){
        galleryDetailArrayList =list;
        notifyDataSetChanged();
    }
}
