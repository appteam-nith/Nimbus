package com.nith.appteam.nimbus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

public class CoreTeamActivity extends AppCompatActivity {

    RecyclerView recycler_view;
    CoreTeamAdapter core_team_adapter;
    ArrayList<CoreTeamItem> array_list;
    Toolbar coreTeamToolbar;
    private static final String BASE_URL="https://api-hillfair-2k16.herokuapp.com/";
    private static final String BASE_URL_JSEC="https://s3.ap-south-1.amazonaws.com/hillffair2016/images/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_core_team);

        recycler_view=(RecyclerView)findViewById(R.id.core_team_list);
        array_list=new ArrayList<>();
        array_list.add(new CoreTeamItem("Ajay k. Sharma","Director",BASE_URL+"photos/director.jpg"));
        array_list.add(new CoreTeamItem("Dr. A.S. Singha","Dean Student Welfare",BASE_URL+"photos/as.jpg"));
        array_list.add(new CoreTeamItem("Dr. Surender Soni","Faculty Coordinator","https://s3-ap-southeast-1.amazonaws.com/nimbus2k16/nimbusteam/surender_soni.png"));

        array_list.add(new CoreTeamItem("Deepak Kumar Jain","Hillffair Secretary",BASE_URL_JSEC+"sec_cultural.jpg"));
        array_list.add(new CoreTeamItem("Rishabh Bhandari","Clubs Secretary",BASE_URL_JSEC+"sec_club.jpg"));
        array_list.add(new CoreTeamItem("Nikhil Gusaiwal","Finance Secretary",BASE_URL_JSEC+"sec_finance.jpg"));
        array_list.add(new CoreTeamItem("Akshika Verma","Treasurer",BASE_URL_JSEC+"treasurer.jpg"));
        array_list.add(new CoreTeamItem("Kunal Sharma","Jt. Secretary (Web Team)",BASE_URL_JSEC+"jsec_web.jpg"));
        array_list.add(new CoreTeamItem("Sourabh Thakur","Jt. Secretary (Dramatics)",BASE_URL_JSEC+"jsec_dramatics.jpg"));
        array_list.add(new CoreTeamItem("Mukul Chandel","Creative Head (Pixonoids)",BASE_URL_JSEC+"jsec_creative.jpg"));
        array_list.add(new CoreTeamItem("Avantika Sharma","Graphic Head (Pixonoids)",BASE_URL_JSEC+"jsec_graphics.jpg"));
        array_list.add(new CoreTeamItem("Bhanu Pratap Singh","Jt. Secretary (Music Club)",BASE_URL+"photos/bhanu_pratap.jpg"));
        array_list.add(new CoreTeamItem("Medha Agrawal","Jt. Secretary (PR Club)",BASE_URL_JSEC+"jsec_pr.jpg"));
        array_list.add(new CoreTeamItem("Aprajit Pandit","Jt. Secretary (Dance Club)",BASE_URL_JSEC+"jsec_dance.jpg"));
        array_list.add(new CoreTeamItem("Ashima Anand","Jt. Secretary (App Team)",BASE_URL_JSEC+"jsec_app.jpg"));
        array_list.add(new CoreTeamItem("Aanchal Negi","Jt. Secretary (Fashion Prade)",BASE_URL+"photos/aanchal_negi.jpg"));
        array_list.add(new CoreTeamItem("Aditya Verma","Jt. Secretary (Discipline committee)",BASE_URL+"photos/aditya_verma.jpg"));
        array_list.add(new CoreTeamItem("Shikhar Shrivastava","Jt. Secretary (Organization)",BASE_URL_JSEC+"jsec_organisation.jpg"));
        array_list.add(new CoreTeamItem("Naveen Banyal","Jt. Secretary (Technical Committee)",BASE_URL_JSEC+"jsec_technical.jpg"));
        array_list.add(new CoreTeamItem("Shubham Dhiman","Jt. Secretary (INS & Control)",BASE_URL_JSEC+"convener_ins.jpg"));


        core_team_adapter=new CoreTeamAdapter(array_list,CoreTeamActivity.this);
        recycler_view.setAdapter(core_team_adapter);
        LinearLayoutManager liner_layout_manager=new LinearLayoutManager(this);
        liner_layout_manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_view.setLayoutManager(liner_layout_manager);

        coreTeamToolbar=(Toolbar)findViewById(R.id.core_team_toolbar);
        coreTeamToolbar.setTitle("Core Team");
        setSupportActionBar(coreTeamToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
