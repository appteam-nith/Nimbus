package com.nith.appteam.nimbus.Utils;

import com.nith.appteam.nimbus.Fragment.FbLoginFragment;
import com.nith.appteam.nimbus.Model.EventRegisterResponse;
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

    @POST("/EndPointOfProfile")
    @FormUrlEncoded
    Call<FbLoginFragment.UserSentResponse> sendFbUserData(@Field("name") String name,@Field("email") String email,@Field("picUrl")String picUrl);
}
