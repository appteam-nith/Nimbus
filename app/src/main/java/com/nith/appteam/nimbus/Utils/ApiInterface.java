package com.nith.appteam.nimbus.Utils;

import com.nith.appteam.nimbus.Model.TeamListResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by sahil on 9/2/17.
 */

public interface ApiInterface {

    @GET("team")
    Call<TeamListResponse> getAllTeam();
}
