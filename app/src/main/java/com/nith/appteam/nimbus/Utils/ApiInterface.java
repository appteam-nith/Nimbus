package com.nith.appteam.nimbus.Utils;

import com.nith.appteam.nimbus.Model.TeamListResponse;
import com.nith.appteam.nimbus.Model.SingleWorkshopResponse;
import com.nith.appteam.nimbus.Model.WorkshopListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

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
}
