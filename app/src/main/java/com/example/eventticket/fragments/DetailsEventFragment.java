package com.example.eventticket.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.eventticket.R;
import com.example.eventticket.model.EventModel;

import fr.tvbarthel.lib.blurdialogfragment.SupportBlurDialogFragment;


public class DetailsEventFragment extends SupportBlurDialogFragment {
    private ImageButton btnClose;
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
        btnClose = layout.findViewById(R.id.btn_close);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return layout;
    }

}