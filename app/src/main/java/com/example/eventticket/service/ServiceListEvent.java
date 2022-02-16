package com.example.eventticket.service;

import com.example.eventticket.model.EventModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceListEvent {
    @GET("listEvent")
    Call<ArrayList<EventModel>> listEvent();
}
