package com.nith.appteam.nimbus.Utils;

import retrofit2.http.POST;
import com.nith.appteam.nimbus.Fragment.FbLoginFragment;
import com.nith.appteam.nimbus.Model.EventRegisterResponse;
import com.nith.appteam.nimbus.Activity.UploadNewsFeedActivity;
import com.nith.appteam.nimbus.Model.Likecount;
import com.nith.appteam.nimbus.Model.MainPagerResponse;
import com.nith.appteam.nimbus.Model.NewsFeedResponse;

import retrofit2.http.GET;

import com.nith.appteam.nimbus.Model.ProfileDataModel;
import com.nith.appteam.nimbus.Model.ProfileEventModel;
import com.nith.appteam.nimbus.Model.TeamEventList;

import com.nith.appteam.nimbus.Model.QuizQuestionsModel;
import com.nith.appteam.nimbus.Model.TeamListResponse;
import com.nith.appteam.nimbus.Model.SingleWorkshopResponse;
import com.nith.appteam.nimbus.Model.WorkshopListResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Path;

import retrofit2.http.Query;

/**
 * Created by sahil on 9/2/17.
 */

public interface ApiInterface {

    @GET("team")
    Call<TeamListResponse> getAllTeam();

    @GET("workshop")
    Call<WorkshopListResponse> getAllWorkshop();

    @GET("workshop/{id}")
    Call<SingleWorkshopResponse> getSingleWorkshop(@Path("id") String id);

    @GET("event/{event_id}")
    Call<SingleWorkshopResponse> getEventDetail(@Path("event_id") String event_id, @Query("student_id") String student_id);

    @POST("event/register/{event_id}")
    @FormUrlEncoded
    Call<EventRegisterResponse> getEventRegisterResponse(@Path("event_id") String event_id, @Field("student_id") String student_id);

    /*@POST("/EndPointOfProfile")
    @FormUrlEncoded
    Call<FbLoginFragment.UserSentResponse> sendFbUserData(@Field("name") String name,@Field("email") String email,@Field("picUrl")String picUrl);
    */
    @GET("team/{id}")
    Call<TeamEventList> getTeamEvents(@Path("id") String id);

    @POST("register")
    @FormUrlEncoded
    Call<FbLoginFragment.UserSentResponse> sendFbUserData(@Field("name") String name,@Field("email") String email,@Field("pic_url")String picUrl);

    @FormUrlEncoded
    @POST("quiz/questions")
    Call<QuizQuestionsModel> getQuiz(@Field("id") String id);

    @FormUrlEncoded
    @POST("quiz/score")
    Call<UpdateScoreModel> updateScore(@Field("id") String id, @Field("score") int score);

    @FormUrlEncoded
    @POST("newsfeed/post")
    Call<UploadNewsFeedActivity.UploadResponse> uploadNews(@Field("title") String title, @Field("description") String description, @Field("uId") String userId, @Field("uName") String userName,@Field("imageUrl") String imageUrl);


    @GET("newsfeed/all")
    Call<NewsFeedResponse> getAllNews(@Query("from") String from, @Query("uId") String userId);

    @FormUrlEncoded
    @POST("newsfeed/like")
    Call<Likecount>likecount(@Field("id") String id, @Field("uId") String userId);

    @GET("profile/{id}")
    Call<ProfileDataModel> profileBasicInfo(@Path("id") String id);
    @GET("profile/event/{id}")
    Call<ProfileEventModel> profileEventList(@Path("id") String id);
    @GET("main")
    Call<MainPagerResponse> getMainResponse();
}
