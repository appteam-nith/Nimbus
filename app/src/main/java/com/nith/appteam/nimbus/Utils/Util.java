package com.nith.appteam.nimbus.Utils;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sahil on 9/2/17.
 */

public class Util {

    public  static ApiInterface getRetrofitService(){

        OkHttpClient.Builder oBuilder = new OkHttpClient.Builder();
        oBuilder.connectTimeout(15l, TimeUnit.SECONDS);
        oBuilder.readTimeout(15l,TimeUnit.SECONDS);
// code to add cache in retrofit

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

}
