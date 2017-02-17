package com.nith.appteam.nimbus.Utils;

import com.nith.appteam.nimbus.Model.TeamEventList;
import com.nith.appteam.nimbus.Model.TeamListResponse;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by sahil on 9/2/17.
 */

public interface ApiInterface {

    @GET("team")
    Call<TeamListResponse> getAllTeam();

    @GET("team/{id}")
    Call<TeamEventList> getTeamEvents(@Path("id") String id);



}
