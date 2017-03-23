package com.nith.appteam.nimbus.Utils;

import retrofit2.http.POST;
import com.nith.appteam.nimbus.Fragment.FbLoginFragment;
import com.nith.appteam.nimbus.Model.CoreTeamEvents;
import com.nith.appteam.nimbus.Model.CoreTeamResponse;
import com.nith.appteam.nimbus.Model.EventRegisterResponse;
import com.nith.appteam.nimbus.Activity.UploadNewsFeedActivity;
import com.nith.appteam.nimbus.Model.GalleryDetailResponse;
import com.nith.appteam.nimbus.Model.GalleryResponse;
import com.nith.appteam.nimbus.Model.LeaderBoardModel;
import com.nith.appteam.nimbus.Model.Likecount;
import com.nith.appteam.nimbus.Model.MainPagerResponse;
import com.nith.appteam.nimbus.Model.NewsFeed;
import com.nith.appteam.nimbus.Model.NewsFeedResponse;

import retrofit2.http.GET;

import com.nith.appteam.nimbus.Model.ProfileDataModel;
import com.nith.appteam.nimbus.Model.ProfileEventModel;
import com.nith.appteam.nimbus.Model.RegisterResponse;
import com.nith.appteam.nimbus.Model.SponsorResponse;
import com.nith.appteam.nimbus.Model.TeamEventList;

import com.nith.appteam.nimbus.Model.QuizQuestionsModel;
import com.nith.appteam.nimbus.Model.TeamListResponse;
import com.nith.appteam.nimbus.Model.SingleWorkshopResponse;
import com.nith.appteam.nimbus.Model.WorkshopListResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by sahil on 9/2/17.
 */

public interface ApiInterface {

    @GET("team_95")
    Call<TeamListResponse> getAllTeam();
    @GET("team_core_95")
    Call<CoreTeamResponse> getAllCoreTeam();


    @GET("workshop_95")
    Call<WorkshopListResponse> getAllWorkshop();

    @GET("workshop_95/{id}")
    Call<SingleWorkshopResponse> getSingleWorkshop(@Path("id") String id);

    @GET("event_95/{event_id}")
    Call<SingleWorkshopResponse> getEventDetail(@Path("event_id") String event_id, @Query("student_id") String student_id);


    @POST("event/register_95/{event_id}")
    Call<EventRegisterResponse> getEventRegisterResponse(@Path("event_id") String event_id, @Query("student_id") String student_id);

    @GET("team_95/{id}")
    Call<TeamEventList> getTeamEvents(@Path("id") String id);

    @GET("team_core_95/{id}")
    Call<CoreTeamEvents> getCoreTeamEvents(@Path("id") String id);

    @POST("register_95")
    @FormUrlEncoded
    Call<FbLoginFragment.UserSentResponse> sendFbUserData(@Field("name") String name,@Field("email") String email,@Field("pic_url")String picUrl);

    @GET("quiz/get_95/{id}")
    Call<QuizQuestionsModel> getQuiz(@Path("id") String id);

    @GET("quiz/update_95/{id}")
    Call<UpdateScoreModel> updateScore(@Path("id") String id, @Query("score") int score);

    @FormUrlEncoded
    @POST("newsfeed/post_95/{student_id}")
    Call<UploadNewsFeedActivity.UploadResponse> uploadNews(@Field("title") String title, @Field("desc") String description, @Path("student_id") String userId, @Field("name") String userName,@Field("photo") String imageUrl);


    @GET("newsfeed/getall_95/{id}")
    Call<NewsFeedResponse> getAllNews(@Path("id") String userId,@Query("from") int from);


    @POST("newsfeed/like_95/{id}")
    Call<Likecount>likecount(@Path("id") String id, @Query("student_id") String userId);

    @GET("quiz/leaderboard_95")
    Call<LeaderBoardModel> getLeaderBoard();

    @GET("profile_95/{id}")
    Call<ProfileDataModel> profileBasicInfo(@Path("id") String id);

    @GET("profile/event_95/{id}")
    Call<ProfileEventModel> profileEventList(@Path("id") String id);

    @GET("profile/newsfeed_95/{student_id}")
    Call<NewsFeedResponse> getUserNews(@Path("student_id") String userId);

    @GET("main/images/get_95")
    Call<MainPagerResponse> getMainResponse();

    @GET("gallery_95/{id}")
    Call<GalleryDetailResponse> getGalleryResponse(@Path("id") String id);

    @GET("galleryAll_95")
    Call<GalleryResponse> getGalleryAll();

    @POST("update/rollno_95/{id}")
    Call<RegisterResponse> updateRollNo(@Path("id") String id,@Query("roll_no") String rollNo);

    @GET("sponsor_95")
    Call<SponsorResponse> getSponsorList();
}
