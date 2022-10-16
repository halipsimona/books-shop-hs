package com.example.proiectafacerielectronice.mysql;

import com.example.proiectafacerielectronice.BooksUser;

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

public interface CartService {
    @POST("booksuser/")
    Call<Integer> insertIntoCart(@Body BooksUser booksuser);

    @GET("user/{id}/books")
    Call<List<BooksUser>> getCart(@Path("id") String id);

    @DELETE("booksuser/{id}")
    Call<Response<Void>> deleteBookFromCart(@Path("id") int id);

    @DELETE("booksusers/{id}")
    Call<Response<Void>> deletAll(@Path("id") String id);
}
