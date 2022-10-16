package com.example.proiectafacerielectronice.mysql;

import com.example.proiectafacerielectronice.Book;
import com.example.proiectafacerielectronice.BookApi;

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

public interface BookService {
    @POST("book/")
        // @FormUrlEncoded
    Call<Integer> insertBook(@Body BookApi book);

    @GET("book/{id}")
        //  @FormUrlEncoded
    Call<Book> getBookById(@Path("id") Integer id);

    @GET("books")
        //   @FormUrlEncoded
    Call<List<Book>> getAllBooks();

}
