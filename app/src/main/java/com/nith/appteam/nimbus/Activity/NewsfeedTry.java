package com.nith.appteam.nimbus.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nith.appteam.nimbus.Adapter.NewsFeedAdapter;
import com.nith.appteam.nimbus.CustomView.EditorView;
import com.nith.appteam.nimbus.Model.NewsFeed;
import com.nith.appteam.nimbus.Model.NewsFeedResponse;
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Service.UploadService;
import com.nith.appteam.nimbus.Utils.SharedPref;
import com.nith.appteam.nimbus.Utils.Util;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsfeedTry extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private static final String FEED_LIST ="list" ;
    private RecyclerView recyclerView;
    private NewsFeedAdapter adapter;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean loading = true;
    private int  pastVisiblesItems, visibleItemCount, totalItemCount, previousTotal = 0, visibleThreshold = 0,feedNo=1;
    private ArrayList<NewsFeed> list=new ArrayList<>();
    private SharedPref pref;
    private RelativeLayout coordinatorLayout;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final String TAG = "Upload News Feed";
    private EditorView editorView;
    private ImageView camera_image, upload_image;
    private static final String UPLOAD_SERVICE="Upload";
    private static final String TITLE="title";
    private static  final String DESCRIPTION="description";
    private static final String  URL_IMAGE="imageUrl";
    private static final  String WORK="work";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsfeed_try);
        pref= new SharedPref(this);
        recyclerView=(RecyclerView)findViewById(R.id.newsfeedtry_recycler);
        coordinatorLayout= (RelativeLayout) findViewById(R.id.core_try_view);
        swipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.swipeRefresh_try);
        swipeRefreshLayout.setOnRefreshListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.newsfeedtoolbar);
        progressBar= (ProgressBar) findViewById(R.id.newsfeedtry_progress);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        adapter = new NewsFeedAdapter(this);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        toolbar.setTitle(" ");

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if (totalItemCount > previousTotal) {
                            loading = false;
                            previousTotal = totalItemCount;
                        }
                    }
                    if (!loading && (totalItemCount - visibleItemCount)
                            <= (pastVisiblesItems + visibleThreshold)) {
                        adapter.notifyItemInserted(list.size() + 1);
                        feedNo+=10;
                        getNewsFeed(feedNo);
                        loading = true;
                    }
                }
            }
        });


        if(savedInstanceState==null){
            if(pref.getUserId().isEmpty()){
                progressBar.setVisibility(View.GONE);
                Snackbar.make(coordinatorLayout,"Please Login To See The Content",Snackbar.LENGTH_INDEFINITE).setAction("Login", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(NewsfeedTry.this,LoginActivity.class));
                    }
                }).show();
            }
            else
                getNewsFeed(1);}
        else {

            list=savedInstanceState.getParcelableArrayList(FEED_LIST);
            if(list!=null){
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                adapter.refresh(list);
            }
            else {
                getNewsFeed(1);
            }
        }

    }
    public void upload(View view)
    {
        View uploaddetails = (View) findViewById(R.id.uploaddetails);
        recyclerView.setVisibility(View.GONE);
        uploaddetails.setVisibility(View.VISIBLE);

        //View news = (View) findViewById(R.id.newsfeedshow);

        Button uploadbutton = (Button) findViewById(R.id.uploadbutton);
        Button newsfeedbutton = (Button) findViewById(R.id.newsfeedbutton);
        uploadbutton.setBackground(ContextCompat.getDrawable(this , R.drawable.b4));
        newsfeedbutton.setBackground(ContextCompat.getDrawable(this,R.drawable.b5));
        newsfeedbutton.setTextColor(getResources().getColor(R.color.white));
        uploadbutton.setTextColor(getResources().getColor(R.color.reddishpink));
        editorView = (EditorView) findViewById(R.id.editor);
        camera_image = (ImageView) findViewById(R.id.select_image);
        upload_image = (ImageView) findViewById(R.id.upload_news);
        camera_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createChooser();
            }
        });

        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditorView.AddTopic add = editorView.buildEditData();
                StringBuilder imageUrl =new StringBuilder();
                if (add.title != null && add.detail != null)
                    if (!add.title.isEmpty() && !add.detail.isEmpty()) {
                        if(!pref.getFirstTimeRollregister())
                            if(!pref.getNitianStatus()&&pref.getUserRollno().isEmpty()){
                                Log.d("b","b");
                                Log.d("b",pref.getUserRollno());
                                if(pref.getUserRollno().isEmpty()){
                                    AlertDialog t= Util.promptRollNo(NewsfeedTry.this);
                                    t.show();
                                }
                            }
                            else {
                                pref.setFirstRollRegister(true);
                            }
                        else {
                            for(int i=0;i<add.imageUrl.size();i++)
                                imageUrl.append(add.imageUrl.get(i)+" ");
                            Log.d("image",imageUrl.toString());
                            Intent i=new Intent(NewsfeedTry.this, UploadService.class);
                            i.putExtra(UPLOAD_SERVICE,true);
                            i.putExtra(TITLE,add.title);
                            i.putExtra(DESCRIPTION,add.detail);
                            if(!imageUrl.toString().isEmpty())
                                i.putExtra(URL_IMAGE,imageUrl.toString());
                            startService(i);

                            Log.d(TAG, add.title + " " + add.detail+" "+imageUrl);
                            finish();}
                    } else {
                        Toast.makeText(NewsfeedTry.this, "Some Fields are still empty", Toast.LENGTH_SHORT).show();
                    }


            }
        });

    }

    private void createChooser() {
        if(ContextCompat.checkSelfPermission(NewsfeedTry.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){

            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 121);
            }

            return;

        }

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "CHOOSE PHOTO"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(filePath, filePathColumn, null, null, null);
            c.moveToFirst();
            String imgDecodableString = c.getString(c.getColumnIndex(filePathColumn[0]));
            c.close();
            editorView.addImage(imgDecodableString);

        }
    }
    
    public void newsfeed(View view)
    {
        View uploaddetails = (View) findViewById(R.id.uploaddetails);
        uploaddetails.setVisibility(View.GONE);

        recyclerView.setVisibility(View.VISIBLE);
        Button uploadbutton = (Button) findViewById(R.id.uploadbutton);
        Button newsfeedbutton = (Button) findViewById(R.id.newsfeedbutton);
        uploadbutton.setBackground(ContextCompat.getDrawable(this , R.drawable.b3));
        uploadbutton.setTextColor(getResources().getColor(R.color.white));
        newsfeedbutton.setTextColor(getResources().getColor(R.color.reddishpink));
        newsfeedbutton.setBackground(ContextCompat.getDrawable(this,R.drawable.b2));
    }

    public void getNewsFeed(int from)
    {
        if(from==1) {
            list.clear();
            adapter.refresh(list);
            recyclerView.setVisibility(View.GONE);
        }
        Call<NewsFeedResponse> newsfeedResponse= Util.getRetrofitService().getAllNews(pref.getUserId(),from);
        newsfeedResponse.enqueue(new Callback<NewsFeedResponse>() {
            @Override
            public void onResponse(Call<NewsFeedResponse> call, Response<NewsFeedResponse> response) {
                if(swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                if(response!=null&&response.isSuccess()){
                    if(response.body().getFeed()!=null){
                        if(response.body().getFeed().size()>0){

                            if(list.size()!=0){
                                list.remove(list.size()-1);
                                adapter.refresh (list);
                            }

                            list.addAll(response.body().getFeed());
                            list.add(null);

                            adapter.refresh(list);


                        }

                        Log.d("rr","check");

                    }
                    else {
                        Log.d("sa","check");

                        if(list.size()!=0){
                            list.remove(list.size()-1);
                            adapter.refresh(list);
                        }

                    }

                }
                else {

                    if(list.size()!=0){
                        list.remove(list.size()-1);
                        adapter.refresh(list);
                    }


                    Snackbar.make(coordinatorLayout,"Unable To load Data",Snackbar.LENGTH_INDEFINITE).setAction("Reload", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            progressBar.setVisibility(View.VISIBLE);
                            getNewsFeed(1);
                        }
                    }).show();
                }
            }

            @Override
            public void onFailure(Call<NewsFeedResponse> call, Throwable t) {
                if(swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }

                if(list.size()!=0){
                    list.remove(list.size()-1);
                    adapter.refresh(list);
                }

                t.printStackTrace();
                progressBar.setVisibility(View.GONE);
                Toast.makeText(NewsfeedTry.this, "Please check your network connection and internet permission", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onRefresh()
    {
        getNewsFeed(1);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(list!=null)
            outState.putParcelableArrayList(FEED_LIST,list);
    }
}
