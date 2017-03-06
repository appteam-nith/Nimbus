package com.nith.appteam.nimbus.Adapter;

/**
 * Created by aditya on 13/2/17.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;


public class ProfilePagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> fragmentArrayList=new ArrayList<>();
    private ArrayList<String>   titleArrayList=new ArrayList<>();
    int tabCount;

    public ProfilePagerAdapter(FragmentManager fm, ArrayList<Fragment> fragmentArrayList, ArrayList<String> titleArrayList) {
        super(fm);
        this.fragmentArrayList = fragmentArrayList;
        this.titleArrayList = titleArrayList;
    }

    public ProfilePagerAdapter(FragmentManager fm, int tabCount){
        super(fm);
        this.tabCount=tabCount;
    }

    @Override
    public Fragment getItem(int position){
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount(){
        return fragmentArrayList==null?tabCount:fragmentArrayList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleArrayList.get(position);
    }


}
