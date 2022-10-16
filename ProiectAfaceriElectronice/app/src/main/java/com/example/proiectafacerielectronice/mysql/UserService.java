package com.example.proiectafacerielectronice.mysql;

import com.example.proiectafacerielectronice.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {
    @POST("user/")
   // @FormUrlEncoded
    Call<ResponseBody> insertUser(@Body User user);

    @GET("user/{id}")
  //  @FormUrlEncoded
    Call<User> getUserById(@Path("id") String id);


    @Headers({"Content-Type: application/json"})
    @PUT("user/{id}")
  //  @FormUrlEncoded
    Call<ResponseBody> updateUser(@Path("id") String id, @Body User user);

    @GET("usermail/{email}")
    //  @FormUrlEncoded
    Call<User> getUserByEmail(@Path("email") String email);
}
