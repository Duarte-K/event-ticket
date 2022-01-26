package com.example.eventticket.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventticket.R;
import com.example.eventticket.model.EventModel;

import fr.tvbarthel.lib.blurdialogfragment.SupportBlurDialogFragment;


public class DetailsEventFragment extends SupportBlurDialogFragment {

    private EventModel event;
    public DetailsEventFragment(EventModel eventdetail) {
        // Required empty public constructor
        event = eventdetail;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_details_event, container, false);


        return layout;
    }

}