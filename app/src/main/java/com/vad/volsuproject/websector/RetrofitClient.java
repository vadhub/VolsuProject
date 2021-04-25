package com.vad.volsuproject.websector;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private final static String URL_BASE = "https://volsu.ru";
    private static Retrofit retrofit;
    private static RetrofitClient retrofitClient;

    private RetrofitClient(){
        retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(URL_BASE).build();
    }
    public static RetrofitClient getRetrofit(){
        if(retrofitClient==null){
            retrofitClient = new RetrofitClient();
        }
        return retrofitClient;
    }

    public JsonPlaceHolder getJsonApi(){
        return retrofit.create(JsonPlaceHolder.class);
    }



}
