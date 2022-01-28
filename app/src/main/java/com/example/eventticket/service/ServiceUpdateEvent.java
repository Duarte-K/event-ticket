package com.example.eventticket.service;

import com.example.eventticket.model.EventModel;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceUpdateEvent {
    @POST("updateEvent")
    Call<EventModel> updateEvent(@Body RequestBody object);
}
