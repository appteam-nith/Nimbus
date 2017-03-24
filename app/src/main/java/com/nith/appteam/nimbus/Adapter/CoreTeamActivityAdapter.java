package com.nith.appteam.nimbus.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.CardView;

import com.nith.appteam.nimbus.Fragment.TeamFragment;
import com.nith.appteam.nimbus.Model.CoreTeam;
import com.nith.appteam.nimbus.Model.TeamItem;
import com.nith.appteam.nimbus.Utils.TeamInterface;

import java.util.ArrayList;

/**
 * Created Nimbus by akatsuki on 22/3/17.
 */

public class CoreTeamActivityAdapter extends FragmentStatePagerAdapter implements TeamInterface{

    private ArrayList<CoreTeam> teamArrayList;
    private float baseElevation;
    private ArrayList<TeamFragment> teamFragmentList;

    public CoreTeamActivityAdapter(FragmentManager fm, float baseElevation, ArrayList<CoreTeam> list) {
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
    public CardView getCardViewAt(int position) {//
        TeamFragment teamFragment=teamFragmentList.get(position);///
        teamFragment.setActCore(true);////
        return teamFragmentList.get(position).getCardview();
    }
}
