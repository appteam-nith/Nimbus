package com.nith.appteam.nimbus.Activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CollapsingToolbarLayout;

import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.text.util.Linkify;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.nith.appteam.nimbus.Adapter.SlidingImageAdapter;
import com.nith.appteam.nimbus.Manifest;
import com.nith.appteam.nimbus.Model.MainPagerResponse;
import com.nith.appteam.nimbus.Model.ProfileDataModel;
import com.nith.appteam.nimbus.Notification.NotificationActivity;
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.ApiInterface;
import com.nith.appteam.nimbus.Utils.Connection;
import com.nith.appteam.nimbus.Utils.SharedPref;
import com.nith.appteam.nimbus.Utils.Util;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {


    private SharedPref sharedPref;
    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;
    private SlidingImageAdapter imageAdapter;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtSubName;
    Toolbar toolbar;
    private LinearLayout quiz_layout, gallery_layout, map_layout,  newsfeed_layout, coreteam_layout , aboutnimbus_layout , teams_layout, feedback_layout,sponsor_layout,workshop_layout,contributor_layout;
    final String number[] = {"9816291592", "9882551107"};
    final String links[] = {"https://www.facebook.com/Nit.Hamirpur.Himachal/","https://github.com/appteam-nith/Nimbus"};
    //public static int navItemIndex = 0;
    private static final int PERMISSIONS_REQUEST_PHONE_CALL = 100;
    private static String[] PERMISSIONS_PHONECALL = {android.Manifest.permission.CALL_PHONE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = new SharedPref(this);
        if (!sharedPref.getLoginStatus() && !sharedPref.getSkipStatus()) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
        setContentView(R.layout.activity_main);

        initCollapsingToolbar();
        init();

        Log.v("Checking UserId:", "" + sharedPref.getUserId());

        loadNavHeader();
        setUpNavigationView();
        //Ends here

        //Code to deal with the ViewPager.
        imageAdapter=new SlidingImageAdapter(MainActivity.this);

        if(new Connection(this).isInternet()){
            getPagerData();
            profileBasicInfo(sharedPref.getUserId());
        }

        viewPager.setAdapter(imageAdapter);
        viewPager.setClipToPadding(false);
        viewPager.setPadding(100,0,100,0);
        viewPager.setPageMargin(60);

        clickListenersMainMenu();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.action_leaderboard:
                        startActivity(new Intent(MainActivity.this,LeaderBoardActivity.class));
                        return true;
                    case R.id.action_profile:
                        startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                        return  true;
                    case R.id.action_notifications:
                        startActivity(new Intent(MainActivity.this,NotificationActivity.class));
                        return true;
                }
                return false;
            }
        });


    }

    public void init(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nvView);

        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtSubName = (TextView) navHeader.findViewById(R.id.subname);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);

        viewPager = (ViewPager)findViewById(R.id.main_view_pager);

        quiz_layout= (LinearLayout) findViewById(R.id.quiz_layout);
        gallery_layout= (LinearLayout) findViewById(R.id.gallery_layout);
        map_layout= (LinearLayout) findViewById(R.id.map_layout);
        newsfeed_layout= (LinearLayout) findViewById(R.id.newsfeed_layout);
        coreteam_layout= (LinearLayout) findViewById(R.id.coreteam_layout);
        aboutnimbus_layout= (LinearLayout) findViewById(R.id.aboutnimbus_layout);
        teams_layout= (LinearLayout) findViewById(R.id.teams_layout);
        feedback_layout= (LinearLayout) findViewById(R.id.feedback_layout);
        contributor_layout= (LinearLayout) findViewById(R.id.contributor_layout);
        sponsor_layout= (LinearLayout) findViewById(R.id.sponsor_layout);
        workshop_layout= (LinearLayout) findViewById(R.id.workshop_layout);
        bottomNavigationView= (BottomNavigationView) findViewById(R.id.bottom_navigation);

    }

    public void clickListenersMainMenu(){
        quiz_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,QuizActivity.class));
            }
        });

        gallery_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,GalleryActivity.class));
            }
        });

        map_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MapActivity.class));
            }
        });

        newsfeed_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,WallIntroActivity.class));
            }
        });

        coreteam_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CoreTeamActivity.class));
            }
        });

        aboutnimbus_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AboutActivity.class));
            }
        });

        teams_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,TeamActivity.class));
            }
        });

        feedback_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                String uriText = "mailto:" + Uri.encode("appteam.nith@gmail.com") + "?subject=" + Uri.encode("Reporting A Bug/Feedback") + "&body=" + Uri.encode("Hello, \nI want to report a bug/give feedback corresponding to the app Nimbus App.\n.....\n\n-Your name");
                Uri uri = Uri.parse(uriText);
                intent.setData(uri);
                startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });

        workshop_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Workshops.class));
            }
        });

        sponsor_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SponsorActivity.class));
            }
        });

        contributor_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ContributorsActivity.class));
            }
        });

    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(sharedPref.getSkipStatus())
            getMenuInflater().inflate(R.menu.main_skipmenu,menu);
        else
            getMenuInflater().inflate(R.menu.main_rightmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void loadNavHeader() {
        txtName.setText("Nimbus 2k17");
        txtSubName.setText("NIT Hamirpur");
        imgNavHeaderBg.setImageResource(R.drawable.cover);
        imgProfile.setImageResource(R.drawable.nimbuslogo);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            sharedPref.setUserId("");
            sharedPref.setLoginStatus(false);
            sharedPref.setUserRollno("");
            sharedPref.setUserEmail("");
            sharedPref.setUserPicUrl("");
            sharedPref.setUserName("");
            LoginManager.getInstance().logOut();
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.nav_aboutapp:
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                        alertDialog.setTitle("About App");

                        alertDialog.setMessage("\nThe Official Android App for 'Nimbus 2k17', the Annual Technical Fest of NIT Hamirpur developed by App Team-NITH\n\n");
                        alertDialog.setIcon(R.drawable.nimbuslogo);
                        alertDialog.show();
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_team:
                        startActivity(new Intent(MainActivity.this, ContributorsActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_contactus:
                        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(MainActivity.this);
                        alertDialog2.setTitle("Contact : App Team-NITH");
                        String link1 = getString(R.string.Link1);
                        String link2 = getString(R.string.Link2);
                        String link3 = getString(R.string.Link3);
                        CharSequence[] contact = { link1 +"\n"," Like our Facebook Page : \n" + link2 + "\n"," GitHub Organisation :" + link3};

                        alertDialog2.setItems(contact, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                     call_contact(which);
                            }
                        });
                        alertDialog2.setIcon(R.drawable.appteam);
                        alertDialog2.show();
                        drawer.closeDrawers();
                        return true;
                        case R.id.nav_reportbug:
                        Intent intent = new Intent(Intent.ACTION_SENDTO);

                        String uriText = "mailto:" + Uri.encode("appteam.nith@gmail.com") + "?subject=" + Uri.encode("Reporting A Bug/Feedback") + "&body=" + Uri.encode("Hello, \nI want to report a bug/give feedback corresponding to the app Nimbus App.\n.....\n\n-Your name");
                        Uri uri = Uri.parse(uriText);
                        intent.setData(uri);
                        startActivity(Intent.createChooser(intent, "Send Email"));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_emergencycontact:
                        AlertDialog.Builder alertDialog3 = new AlertDialog.Builder(MainActivity.this);

                        alertDialog3.setTitle("Phone Number\n\n");

                        CharSequence name[] = {"Abhinav Anand: 9816291592","Pranav Bhardwaj: 9882551107"};

                        alertDialog3.setItems(name, new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface di,int i){

                                call(i);
                            }
                        });


                        alertDialog3.show();
                        drawer.closeDrawers();
                        return true;

                    default:
                        //navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                }

                //loadHomeFragment();

                return true;
            }
        });
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
    }


    private void getPagerData(){
        Call<MainPagerResponse> response= Util.getRetrofitService().getMainResponse();
        response.enqueue(new Callback<MainPagerResponse>() {
            @Override
            public void onResponse(Call<MainPagerResponse> call, Response<MainPagerResponse> response) {
                MainPagerResponse mainPagerResponse=response.body();
                if(response!=null&&response.isSuccess()){
                    ArrayList<String> list=mainPagerResponse.getImageList();
                    imageAdapter.refresh(list);
                }
            }

            @Override
            public void onFailure(Call<MainPagerResponse> call, Throwable t) {

            }
        });
    }

    private void profileBasicInfo(String id){

        ApiInterface mAPI = Util.getRetrofitService();
        Call<ProfileDataModel> mService = mAPI.profileBasicInfo(id);

        mService.enqueue(new Callback<ProfileDataModel>() {
            @Override
            public void onResponse(Call<ProfileDataModel> call, Response<ProfileDataModel> response) {
                if(response!=null && response.isSuccess()){
                    if(response.body().isSuccess()){
                        ProfileDataModel model = response.body();
                        //For Testing
                        Log.v("RESPONSE SUCCESS"," "+model.getRollno()+" "+model.getName()+" "+model.getEmail()+ " "+model.getPhoto());

                        if(model!=null){

                            sharedPref.setUserName(model.getName());
                            sharedPref.setUserEmail(model.getEmail());
                            sharedPref.setUserRollno(model.getRollno());
                            sharedPref.setUserPicUrl(model.getPhoto());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileDataModel> call, Throwable t) {
                t.printStackTrace();

            }
        });}



    private void call(int i)
    {
        String phone;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.CALL_PHONE}, PERMISSIONS_REQUEST_PHONE_CALL);
        }
        else{
            phone = number[i];
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:+91" + phone));
            startActivity(intent);
        }

    }
    private void call_contact(int i){
        String uri1;
        if(i==0){
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            String uriText = "mailto:" + Uri.encode("appteam.nith@gmail.com");
            Uri uri = Uri.parse(uriText);
            intent.setData(uri);
            startActivity(intent);

        }
        else{
            uri1 = links[i-1];
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse(uri1));
            startActivity(intent);
        }
    }
}