package com.example.eventticket.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventticket.R;
import com.example.eventticket.fragments.DetailsEventFragment;
import com.example.eventticket.model.EventModel;

import java.util.ArrayList;

public class AdapterEvent extends RecyclerView.Adapter<AdapterEvent.MyViewHolder> {
    private ArrayList<EventModel> listEvent;

    public AdapterEvent(ArrayList<EventModel> eventos){
        this.listEvent = eventos;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View event = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_event, parent, false);
                return new MyViewHolder(event);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(listEvent.get(position).getName());
        holder.genre.setText(listEvent.get(position).getGenre());
        holder.local.setText(listEvent.get(position).getLocal());
        holder.date.setText(listEvent.get(position).getDate());
        //holder.hour.setText(listEvent.get(position).getHour());

    }

    @Override
    public int getItemCount() {
        return listEvent.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name, genre, local, date, hour;
        ImageButton btnEdit, btnRemove;
        Button btnInfo;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.tx_nameEvent);
            genre = itemView.findViewById(R.id.tx_genreEvent);
            local = itemView.findViewById(R.id.tx_localEvent);
            date = itemView.findViewById(R.id.tx_dateEvent);
            hour = itemView.findViewById(R.id.tx_hourEvent);
            btnEdit = itemView.findViewById(R.id.btn_close);
            btnRemove = itemView.findViewById(R.id.btn_remove);
            btnInfo = itemView.findViewById(R.id.btn_info);

            btnInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DetailsEventFragment dialog = new DetailsEventFragment(listEvent.get(getAdapterPosition()));
                    //dialog.show(getSupportFragmentManager(), dialog.getClass().getSimpleName());
                }
            });
        }
    }
}
