package com.nith.appteam.nimbus.Utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Service.UploadService;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sahil on 9/2/17.
 */

public class Util {
    private static  final String ROLL_NO="rollNo";
    private static final String REGISTER_ROLL_NO="rollNoRegister";
    public  static ApiInterface getRetrofitService(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient.Builder oBuilder = new OkHttpClient.Builder();
        oBuilder.addNetworkInterceptor(loggingInterceptor);
        oBuilder.connectTimeout(15l, TimeUnit.SECONDS);
        oBuilder.readTimeout(15l,TimeUnit.SECONDS);
// code to add cache in retrofit

        /*
        oBuilder.cache(new Cache(new File(MyApplication.getAppContext().getCacheDir(),"cache"),10*1024*1024));
        oBuilder.addInterceptor(new Interceptor() {
            @Override public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (new Connection(MyApplication.getAppContext()).isInternet()) {
                    request = request.newBuilder().header("Cache-Control", "public, max-age=" + 36000).build();
                } else {
                    request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
                }
                return chain.proceed(request);
            }
        });

*/
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://nimbus2k17api.herokuapp.com/api/").addConverterFactory(GsonConverterFactory.create()).
                client(oBuilder.build()).
                build();

        ApiInterface service = retrofit.create(ApiInterface.class);
        return service;
    }

    public static  int random(){
        Random r=new Random();
        return  r.nextInt(10000000);
    }
    public static AlertDialog promptRollNo(final AppCompatActivity context){
        final SharedPref sharedPref=new SharedPref(context);
        AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(context);
        LayoutInflater inflater=context.getLayoutInflater();
        LinearLayout l= (LinearLayout) inflater.inflate(R.layout.dialog_register_rollno,null);
        alertDialogBuilder.setView(l);
        final CheckBox checkBox= (CheckBox) l.findViewById(R.id.checkbox_register);
        final EditText rollNoEditText= (EditText) l.findViewById(R.id.rollno_register);
        final EditText phoneNoEditText= (EditText) l.findViewById(R.id.phone_register);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    rollNoEditText.setVisibility(View.VISIBLE);
                    phoneNoEditText.setVisibility(View.VISIBLE);
                }
                else {
                    rollNoEditText.setVisibility(View.GONE);
                    phoneNoEditText.setVisibility(View.GONE);
                }
            }
        });

        alertDialogBuilder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i=new Intent(context, UploadService.class);
                i.putExtra(REGISTER_ROLL_NO,true);
                i.putExtra(ROLL_NO,rollNoEditText.getText().toString());
                if(checkBox.isChecked()){
                    sharedPref.setNitianStatus(true);
                    sharedPref.setUserRollno(rollNoEditText.getText().toString());
                    context.startService(i);
                }
                else{
                    sharedPref.setNitianStatus(false);
                    sharedPref.setUserRollno("");
                }


            }
        });
         return alertDialogBuilder.create();
    }

}
