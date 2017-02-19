package com.nith.appteam.nimbus.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.nith.appteam.nimbus.Adapter.CoreTeamAdapter;
import com.nith.appteam.nimbus.Model.CoreTeamItem;
import com.nith.appteam.nimbus.R;

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

        array_list.add(new CoreTeamItem("Abhinav Anand","Nimbus Secretary",BASE_URL_JSEC+"sec_cultural.jpg"));
        array_list.add(new CoreTeamItem("Pranav Bhardwaj","Clubs Secretary (Core)",BASE_URL_JSEC+"sec_club.jpg"));
        array_list.add(new CoreTeamItem("Harsh Sharma","Clubs Secretary (Departmental)",BASE_URL_JSEC+"sec_club.jpg"));
        array_list.add(new CoreTeamItem("Harshiit Nadda","Finance Secretary",BASE_URL_JSEC+"sec_finance.jpg"));
        array_list.add(new CoreTeamItem("Rohit Raman","Treasurer",BASE_URL_JSEC+"treasurer.jpg"));
        array_list.add(new CoreTeamItem("Pooja Bayana","Secretary (Public Relation)",BASE_URL_JSEC+"jsec_web.jpg"));
        array_list.add(new CoreTeamItem("Himanshu Khandelwal","Jt. Secretary (Public Relation)",BASE_URL_JSEC+"jsec_dramatics.jpg"));
        array_list.add(new CoreTeamItem("Ishan Dhiman","Creative Head",BASE_URL_JSEC+"jsec_creative.jpg"));
        array_list.add(new CoreTeamItem("Shubam Rana","Graphic Head",BASE_URL_JSEC+"jsec_graphics.jpg"));
        array_list.add(new CoreTeamItem("Saloni Bakshi","Web Head",BASE_URL_JSEC+"jsec_graphics.jpg"));
        array_list.add(new CoreTeamItem("Chetanya Kaushal","Secretary (Hospitality)",BASE_URL+"photos/bhanu_pratap.jpg"));
        array_list.add(new CoreTeamItem("Abhisek Mehra","Jt. Secretary (Registration)",BASE_URL_JSEC+"jsec_pr.jpg"));
        array_list.add(new CoreTeamItem("Abhishek Singh Parihar","Secretary (Accomodation)",BASE_URL_JSEC+"jsec_dance.jpg"));
        array_list.add(new CoreTeamItem("Shubham Mahajan","Secretary (Transportation)",BASE_URL_JSEC+"jsec_dance.jpg"));
        array_list.add(new CoreTeamItem("Nishant Chaudhary","Secretary (Promotional & Marketing)",BASE_URL_JSEC+"jsec_dance.jpg"));
        array_list.add(new CoreTeamItem("Medisetti Tanuja","App Team Head",BASE_URL_JSEC+"jsec_app.jpg"));
        array_list.add(new CoreTeamItem("Anurag Sharma","Event Quality Head",BASE_URL+"photos/aanchal_negi.jpg"));
        array_list.add(new CoreTeamItem("Tushar joshi","Event Scheduling Manager",BASE_URL+"photos/aditya_verma.jpg"));
        array_list.add(new CoreTeamItem("Abhimanyu Kumar","Secretary (Design & Decoration)",BASE_URL_JSEC+"jsec_organisation.jpg"));
        array_list.add(new CoreTeamItem("Chirag Tyagi","Secretary (Technical Decoration)",BASE_URL_JSEC+"jsec_technical.jpg"));
        array_list.add(new CoreTeamItem("Sumit Kumar Singh","Jt. Secretary (Technical Decoration)",BASE_URL_JSEC+"convener_ins.jpg"));
        array_list.add(new CoreTeamItem("Aditya Verma","Secretary (Discipline (Boy))",BASE_URL_JSEC+"convener_ins.jpg"));
        array_list.add(new CoreTeamItem("Kunal Sharma","Jt. Secretary (Discipline (Boy))",BASE_URL_JSEC+"convener_ins.jpg"));
        array_list.add(new CoreTeamItem("Shriya Kaul","Secretary (Discipline (Girl))",BASE_URL_JSEC+"convener_ins.jpg"));
        array_list.add(new CoreTeamItem("Pranab Mukamia","Secretary (Environmental)",BASE_URL_JSEC+"convener_ins.jpg"));
        array_list.add(new CoreTeamItem("Rajat Dohroo","Secretary (Human Value & Ethics)",BASE_URL_JSEC+"convener_ins.jpg"));


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
