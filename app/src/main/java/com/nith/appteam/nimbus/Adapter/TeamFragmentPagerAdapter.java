package com.nith.appteam.nimbus.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.CardView;

import com.nith.appteam.nimbus.Fragment.TeamFragment;
import com.nith.appteam.nimbus.Utils.TeamInterface;
import com.nith.appteam.nimbus.Model.TeamItem;

import java.util.ArrayList;

/**
 * Created by sahil on 7/2/17.
 */

public class TeamFragmentPagerAdapter extends FragmentStatePagerAdapter implements TeamInterface {

   private ArrayList<TeamItem> teamArrayList;
   private float baseElevation;
   private ArrayList<TeamFragment> teamFragmentList;

    public TeamFragmentPagerAdapter(FragmentManager fm,float baseElevation,ArrayList<TeamItem> list) {
        super(fm);
        teamArrayList=list;
        teamFragmentList=new ArrayList<>();
        for(int i=0;i<teamArrayList.size();i++)
            teamFragmentList.add(TeamFragment.newInstance(teamArrayList.get(i)));
        this.baseElevation=baseElevation;
    }

    @Override
    public Fragment getItem(int position) {
        return  teamFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return teamArrayList.size();
    }




    @Override
    public float getBaseElevation() {
        return baseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return teamFragmentList.get(position).getCardview();
    }
}
