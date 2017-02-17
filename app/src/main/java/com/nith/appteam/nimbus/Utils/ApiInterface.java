package com.nith.appteam.nimbus.Utils;


import com.nith.appteam.nimbus.Fragment.FbLoginFragment;

import com.nith.appteam.nimbus.Activity.UploadNewsFeedActivity;
import com.nith.appteam.nimbus.Model.Likecount;
import com.nith.appteam.nimbus.Model.NewsFeedModel;
import com.nith.appteam.nimbus.Model.TeamListResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

import retrofit2.http.Query;


/**
 * Created by sahil on 9/2/17.
 */

public interface ApiInterface {

    @GET("team")
    Call<TeamListResponse> getAllTeam();


    @POST("/EndPointOfProfile")
    @FormUrlEncoded
    Call<FbLoginFragment.UserSentResponse> sendFbUserData(@Field("name") String name,@Field("email") String email,@Field("picUrl")String picUrl);


    @FormUrlEncoded
    @POST("newsfeed/post")
    Call<UploadNewsFeedActivity.UploadResponse> uploadNews(@Field("title") String title, @Field("description") String description, @Field("uId") String userId, @Field("uName") String userName);


    @GET("newsfeed/all")
    Call<NewsFeedModel> getAllNews(@Query("from") String from, @Query("uId") String userId);

    @FormUrlEncoded
    @POST("newsfeed/like")
    Call<Likecount>likecount(@Field("id") String id, @Field("uId") String userId);
}
