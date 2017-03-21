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
    private static final String BASE_URL_JSEC="http://nimbus2k17api.herokuapp.com/images/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_core_team);

        recycler_view=(RecyclerView)findViewById(R.id.core_team_list);
        array_list=new ArrayList<>();
        array_list.add(new CoreTeamItem("Ajay k. Sharma","Director",BASE_URL+"photos/director.jpg"));
        array_list.add(new CoreTeamItem("Dr. A.S. Singha","Dean Student Welfare",BASE_URL+"photos/as.jpg"));
        array_list.add(new CoreTeamItem("Dr Saroj Thakur","Faculty Coordinator",BASE_URL_JSEC+"saroj.jpg"));

        array_list.add(new CoreTeamItem("Abhinav Anand","Secretary (Technical Activities)",BASE_URL_JSEC+"abhinav.jpg"));
        array_list.add(new CoreTeamItem("Pranav Bhardwaj","Club Secretary (Core)",BASE_URL_JSEC+"pranav.jpg"));
        array_list.add(new CoreTeamItem("Harsh Sharma","Club Secretary (Departmental)",BASE_URL_JSEC+"harsh.jpg"));
        array_list.add(new CoreTeamItem("Harshit Nadda","Secretary (Finance and treasury)",BASE_URL_JSEC+"harshit.jpg"));
        array_list.add(new CoreTeamItem("Rohit Raman","Secretary (Finance and treasury)",BASE_URL_JSEC+"rohit.jpg"));
        array_list.add(new CoreTeamItem("Pooja Bayana","Secretary (Public Relation)",BASE_URL_JSEC+"pooja.jpg"));
        array_list.add(new CoreTeamItem("Himanshu Khandelwal","Jt. Secretary (Public Relation)",BASE_URL_JSEC+"himanshu.jpg"));
        array_list.add(new CoreTeamItem("Ishan Dhiman","Creative Head",BASE_URL_JSEC+"ishan.jpg"));
        array_list.add(new CoreTeamItem("Shubam Rana","Graphic Head",BASE_URL_JSEC+"shubam.jpg"));
        array_list.add(new CoreTeamItem("Saloni Bakshi","Web Head",BASE_URL_JSEC+"saloni.jpg"));
        array_list.add(new CoreTeamItem("Chetanya Kaushal","Secretary (Hospitality)",BASE_URL+"chetanya.jpg"));
        array_list.add(new CoreTeamItem("Abhisek Mehra","Secretary (Registration)",BASE_URL_JSEC+"abhishekm.jpg"));
        array_list.add(new CoreTeamItem("Abhishek Singh Parihar","Secretary (Accomodation)",BASE_URL_JSEC+"abhisheks.JPG"));
        array_list.add(new CoreTeamItem("Shubham Mahajan","Secretary (Transportation)",BASE_URL_JSEC+"shubham.jpg"));
        array_list.add(new CoreTeamItem("Nishant Chaudhary","Secretary (Promotional & Marketing)",BASE_URL_JSEC+"nishant.jpg"));
        array_list.add(new CoreTeamItem("Medisetti Tanuja","App Team Head",BASE_URL_JSEC+"medisetti.jpg"));
        array_list.add(new CoreTeamItem("Anurag Sharma","Event Quality Head",BASE_URL+"anurag.jpg"));
        array_list.add(new CoreTeamItem("Tushar joshi","Event Scheduling Manager",BASE_URL+"tushar.jpg"));
        array_list.add(new CoreTeamItem("Abhimanyu Kumar","Secretary (Design & Decoration)",BASE_URL_JSEC+"abhimanyu.jpg"));
        array_list.add(new CoreTeamItem("Chirag Tyagi","Secretary (Technical Decoration)",BASE_URL_JSEC+"chirag.jpg"));
        array_list.add(new CoreTeamItem("Sumit Kumar Singh","Jt. Secretary (Technical Decoration)",BASE_URL_JSEC+"sumit.jpg"));
        array_list.add(new CoreTeamItem("Aditya Verma","Secretary (Discipline (Boy))",BASE_URL_JSEC+"aditya.jpg"));
        array_list.add(new CoreTeamItem("Kunal Sharma","Jt. Secretary (Discipline (Boy))",BASE_URL_JSEC+"kunal.jpg"));
        array_list.add(new CoreTeamItem("Shriya Kaul","Secretary (Discipline (Girl))",BASE_URL_JSEC+"shriya.jpg"));
        array_list.add(new CoreTeamItem("Pranab Mukamia","Secretary (Environmental)",BASE_URL_JSEC+"pranab.jpg"));
        array_list.add(new CoreTeamItem("Rajat Dohroo","Secretary (Human Value & Ethics)",BASE_URL_JSEC+"rajat.jpg"));


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
