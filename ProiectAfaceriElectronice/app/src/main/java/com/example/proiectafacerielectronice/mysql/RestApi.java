package com.example.proiectafacerielectronice.mysql;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApi {
    private  static retrofit2.Retrofit retrofitInstance;
   //public final static  String serverUrl="http://192.17.1.133:5050/"; //acasa
   //public final static  String serverUrl="http://192.168.100.5:5050/";
   //public final static  String serverUrl="http://192.168.1.100:5050/";
   //public final static  String serverUrl="http://192.168.1.5:5050/";
   //public final static  String serverUrl="http://192.168.1.5:5050/"; //acasa nou;
   //public final static  String serverUrl="http://192.168.1.3:5050/";
   public final static  String serverUrl="http://192.168.1.9:5050/"; //tara
    // create http
    public static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(1000, TimeUnit.SECONDS)
            .readTimeout(1000,TimeUnit.SECONDS).build();

    public static retrofit2.Retrofit getRetrofitInstance(){
        if(retrofitInstance == null)
            retrofitInstance = new retrofit2.Retrofit.Builder()
                    .baseUrl(serverUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                   //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        return retrofitInstance;
    }
}
