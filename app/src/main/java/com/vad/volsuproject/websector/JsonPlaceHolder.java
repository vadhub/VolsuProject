package com.vad.volsuproject.websector;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolder {
    @GET("/struct/administrative/it/apiget.php")
    Call<List<Notificate>> getNotificates();
}
