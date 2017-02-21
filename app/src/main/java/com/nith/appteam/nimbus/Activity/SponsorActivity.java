package com.nith.appteam.nimbus.Activity;

/**
 * Created by joshafest on 2/19/2017.
 */
import java.util.ArrayList;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;
import android.widget.TextView;
import com.nith.appteam.nimbus.Activity.Team;
import com.nith.appteam.nimbus.Adapter.SponsorAdapter;
import com.nith.appteam.nimbus.R;

import static com.facebook.FacebookSdk.getApplicationContext;

public class SponsorActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor);
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.sponsor_toolbar);
        TextView mTitle = (TextView) toolbarTop.findViewById(R.id.toolbar_title);
        mTitle.setText("Sponsors");
        //setSupportActionBar(toolbar);
        //THE EXPANDABLE
        ExpandableListView elv=(ExpandableListView) findViewById(R.id.expandableListView1);
        final ArrayList<Team> team=getData();
        //CREATE AND BIND TO ADAPTER
        SponsorAdapter adapter=new SponsorAdapter(this, team);
        elv.setAdapter(adapter);
        //SET ONCLICK LISTENER
        elv.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPos,
                                        int childPos, long id) {
                Toast.makeText(getApplicationContext(), team.get(groupPos).players.get(childPos), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
    //ADD AND GET DATA
    private ArrayList<Team> getData()
    {
        Team t1=new Team("Educational Partner");
        t1.players.add("S1");
        t1.players.add("S2");
        t1.players.add("S3");
        t1.players.add("S4");
        Team t2=new Team("Food Partner");
        t2.players.add("f1");
        t2.players.add("f2");
        t2.players.add("f3");
        t2.players.add("f4");
        Team t3=new Team("Other");
        t3.players.add("o1");
        t3.players.add("o2");
        t3.players.add("o3");
        t3.players.add("o4");
        ArrayList<Team> allTeams=new ArrayList<Team>();
        allTeams.add(t1);
        allTeams.add(t2);
        allTeams.add(t3);
        return allTeams;
    }
}