package com.nith.appteam.nimbus.Utils;

import com.nith.appteam.nimbus.Fragment.FbLoginFragment;
import com.nith.appteam.nimbus.Model.QuizQuestionsModel;
import com.nith.appteam.nimbus.Model.TeamListResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

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
    @POST("quiz/questions")
    Call<QuizQuestionsModel> getQuiz(@Field("id") String id);

    @FormUrlEncoded
    @POST("quiz/score")
    Call<UpdateScoreModel> updateScore(@Field("id") String id, @Field("score") int score);

}
