package com.example.eventticket.service;

import com.example.eventticket.model.EventModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceFilterGenre {
    @POST("filterGenre")
    Call<ArrayList<EventModel>> filtergenre(@Body String object);
}
