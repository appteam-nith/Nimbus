package com.nith.appteam.nimbus.Adapter;

/**
 * Created by acer on 2/19/2017.
 */

import java.util.ArrayList;
import android.content.Context;
        import android.graphics.Color;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseExpandableListAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

import com.nith.appteam.nimbus.Model.Team;
import com.nith.appteam.nimbus.R;

public class SponsorAdapter{}
    /*
    // extends BaseExpandableListAdapter {
    private Context c;
    private ArrayList<Team> team;
    private LayoutInflater inflater;
    public SponsorAdapter(Context c,ArrayList<Team> team)
    {
        this.c=c;
        this.team=team;
        inflater=(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    //GET A SINGLE PLAYER
    @Override
    public Object getChild(int groupPos, int childPos) {
        // TODO Auto-generated method stub
        return team.get(groupPos).players.get(childPos);
    }
    //GET PLAYER ID
    @Override
    public long getChildId(int arg0, int arg1) {
        // TODO Auto-generated method stub
        return 0;
    }
    //GET PLAYER ROW
    /*
    @Override
    public View getChildView(int groupPos, int childPos, boolean isLastChild, View convertView,
                             ViewGroup parent) {
        //ONLY INFLATER XML ROW LAYOUT IF ITS NOT PRESENT,OTHERWISE REUSE IT
        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.player, null);
        }
        //GET CHILD/PLAYER NAME
        String  child=(String) getChild(groupPos, childPos);
        //SET CHILD NAME
        TextView nameTv=(TextView) convertView.findViewById(R.id.textView1);
        ImageView img=(ImageView) convertView.findViewById(R.id.imageView1);
        nameTv.setText(child);
        //GET TEAM NAME
        String teamName= getGroup(groupPos).toString();
        //ASSIGN IMAGES TO PLAYERS ACCORDING TO THEIR NAMES AN TEAMS
        if(teamName=="Educational Partner")
        {
            if(child=="S1")
            {
                img.setImageResource(R.drawable.team)  ;
            }else if(child=="S2")
            {
                img.setImageResource(R.drawable.team)  ;
            }else if(child=="S3")
            {
                img.setImageResource(R.drawable.team)  ;
            }else if(child=="S4")
            {
                img.setImageResource(R.drawable.team)  ;
            }
        }else if(teamName=="Food Partner")
        {
            if(child=="f1")
            {
                img.setImageResource(R.drawable.team)  ;
            }else if(child=="f2")
            {
                img.setImageResource(R.drawable.team)  ;
            }else if(child=="f3")
            {
                img.setImageResource(R.drawable.team)  ;
            }else if(child=="f4")
            {
                img.setImageResource(R.drawable.team)  ;
            }
        }else if(teamName=="Other")
        {
            if(child=="o1")
            {
                img.setImageResource(R.drawable.team)  ;
            }else if(child=="o2")
            {
                img.setImageResource(R.drawable.team)  ;
            }else if(child=="o3")
            {
                img.setImageResource(R.drawable.team)  ;
            }else if(child=="o4")
            {
                img.setImageResource(R.drawable.team)  ;
            }
        }
        return convertView;
    }
    //GET NUMBER OF PLAYERS
    @Override
    public int getChildrenCount(int groupPosw) {
        // TODO Auto-generated method stub
        return team.get(groupPosw).players.size();
    }
    //GET TEAM
    @Override
    public Object getGroup(int groupPos) {
        // TODO Auto-generated method stub
        return team.get(groupPos);
    }
    //GET NUMBER OF TEAMS
    @Override
    public int getGroupCount() {
        // TODO Auto-generated method stub
        return team.size();
    }
    //GET TEAM ID
    @Override
    public long getGroupId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }
    //GET TEAM ROW
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        //ONLY INFLATE XML TEAM ROW MODEL IF ITS NOT PRESENT,OTHERWISE REUSE IT
        if(convertView == null)
        {
            convertView=inflater.inflate(R.layout.team, null);
        }
        //GET GROUP/TEAM ITEM
        Team t=(Team) getGroup(groupPosition);
        //SET GROUP NAME
        TextView nameTv=(TextView) convertView.findViewById(R.id.textView1);
        ImageView img=(ImageView) convertView.findViewById(R.id.imageView1);
        String name=t.Name;
        nameTv.setText(name);
        //ASSIGN TEAM IMAGES ACCORDING TO TEAM NAME
        if(name=="Educational Partner")
        {
            img.setImageResource(R.drawable.team);
        }else if(name=="Food Partner")
        {
            img.setImageResource(R.drawable.team);
        }else if(name=="Other")
        {
            img.setImageResource(R.drawable.team);
        }
        //SET TEAM ROW BACKGROUND COLOR
        convertView.setBackgroundColor(Color.WHITE);
        return convertView;
    }
    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        // TODO Auto-generated method stub
        return true;
    }
    */
//}