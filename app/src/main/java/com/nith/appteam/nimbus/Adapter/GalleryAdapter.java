package com.nith.appteam.nimbus.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.nith.appteam.nimbus.Fragment.GalleryFragment;
import com.nith.appteam.nimbus.Model.Gallery;

import java.util.ArrayList;

/**
 * Created by sahil on 4/3/17.
 */

public class GalleryAdapter extends FragmentStatePagerAdapter{
    private ArrayList<Gallery> galleryArrayList=new ArrayList<>();

    public GalleryAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Gallery g=galleryArrayList.get(position);
        return GalleryFragment.newInstance(g.getTitle(),g.getImageUrl());
    }

    @Override
    public int getCount() {
        return galleryArrayList.size();
    }

    public void refresh(ArrayList<Gallery> list){
        galleryArrayList=list;
        notifyDataSetChanged();
    }
}
