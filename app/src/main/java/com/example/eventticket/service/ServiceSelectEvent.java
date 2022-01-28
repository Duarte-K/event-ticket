package com.example.eventticket.service;

import com.example.eventticket.model.EventModel;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceSelectEvent {

    @POST("selectEvent")
    Call<EventModel> selectEvent(@Body RequestBody object);
}
