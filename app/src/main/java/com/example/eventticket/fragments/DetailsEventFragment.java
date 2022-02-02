package com.example.eventticket.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

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
        TextView name = layout.findViewById(R.id.tx_nameEvent);
        TextView descricao = layout.findViewById(R.id.tx_DescEvent);
        TextView genero = layout.findViewById(R.id.tx_genreEvent);
        TextView cidade = layout.findViewById(R.id.tx_localEvent);
        TextView endereco = layout.findViewById(R.id.tx_locateEvent);
        TextView data = layout.findViewById(R.id.tx_dateEvent);
        TextView hora = layout.findViewById(R.id.tx_hourEvent);

        name.setText(event.getName());
        descricao.setText(event.getDescription());
        genero.setText(event.getGenre());
        cidade.setText(event.getCity());
        endereco.setText(event.getLocal());
        data.setText(event.getDate());
        hora.setText(event.getHour());

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return layout;
    }

}