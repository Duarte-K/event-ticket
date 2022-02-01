package com.example.eventticket.service;

import com.example.eventticket.model.UserModel;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceAuthenticate {
    //busca o usuário com base no nome.
    @POST("selectUser")
    Call<UserModel> authenticateUser(@Body RequestBody object);
}
