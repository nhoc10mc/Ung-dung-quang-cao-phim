package com.example.administrator.myapplication;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IMyService {
    @Headers("Content-Type: application/json")
    @POST("comments")
    Call<String> getUser(@Body String body);
}
