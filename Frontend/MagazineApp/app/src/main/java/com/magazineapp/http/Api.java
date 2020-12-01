package com.magazineapp.http;

import com.magazineapp.models.MessageModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Api {

    @Headers({"Content-Type: application/x-www-form-urlencoded;charset=UTF-8"})
    @GET("api.php?")
    Call<String> makeRequest();

    @FormUrlEncoded
    @Headers({"Content-Type: application/x-www-form-urlencoded;charset=UTF-8"})
    @POST("index.php")
    Call<String> makeRequest(@Body String body);

    @Headers({"Content-Type: application/x-www-form-urlencoded;charset=UTF-8"})
    @GET("api.php?")
    Call<MessageModel> makeCallRequest();

    @Headers({"Content-Type: application/x-www-form-urlencoded;charset=UTF-8"})
    @POST("api.php?")
    Call<MessageModel> makeCallRequest(@Body String body);

}
