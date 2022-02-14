package com.example.eventticket.activities;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventticket.R;
import com.example.eventticket.adapter.AdapterEvent;
import com.example.eventticket.control.SessionControl;
import com.example.eventticket.model.EventModel;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageView logout;
    private SessionControl sessionControl;
    private TextView hello;
    private ArrayList<EventModel>listEvent = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sessionControl = new SessionControl(getApplicationContext());
        sessionControl.checkSession();

        logout = findViewById(R.id.img_logout);
        hello = findViewById(R.id.tx_welcome);
        hello.setText("Olá "+sessionControl.userSession());
        recyclerView = findViewById(R.id.recycler_events);

        //Config Adapter
        AdapterEvent adapterEvent = new AdapterEvent(listEvent);

        //Config RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterEvent);

        //Spinner de Filtro
        Spinner spinner_filter = findViewById(R.id.sp_search);
        String[] filters = getResources().getStringArray(R.array.list_search);
        ArrayAdapter<String> adapterFilter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, filters);
        adapterFilter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_filter.setAdapter(adapterFilter);

        //Spinner de local
        Spinner spin_local = findViewById(R.id.spin_local);
        String[] locais = getResources().getStringArray(R.array.list_city);
        ArrayAdapter<String> adapterLocal = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locais);
        adapterLocal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_local.setAdapter(adapterLocal);

        //Spinner de gênero
        Spinner spin_genre = findViewById(R.id.spin_genre);
        String[] generos = getResources().getStringArray(R.array.list_genre);
        ArrayAdapter<String> adapterGenero = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, generos);
        adapterGenero.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_genre.setAdapter(adapterGenero);

        //Spinner de data
        Spinner spin_date = findViewById(R.id.spin_date);
        ArrayAdapter<String> adapterDate = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adapterDate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_date.setAdapter(adapterDate);

        spinner_filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    Toast.makeText(getApplicationContext(), "Todos", Toast.LENGTH_SHORT).show();
                    spin_local.setVisibility(View.GONE);
                    spin_date.setVisibility(View.GONE);
                    spin_genre.setVisibility(View.GONE);
                }else if (position == 1){
                    Toast.makeText(getApplicationContext(), "Cidade", Toast.LENGTH_SHORT).show();
                    spin_local.setVisibility(View.VISIBLE);
                    spin_date.setVisibility(View.GONE);
                    spin_genre.setVisibility(View.GONE);
                }else if(position == 2){
                    Toast.makeText(getApplicationContext(), "Gênero", Toast.LENGTH_SHORT).show();
                    spin_genre.setVisibility(View.VISIBLE);
                    spin_local.setVisibility(View.GONE);
                    spin_date.setVisibility(View.GONE);
                }else{
                    Toast.makeText(getApplicationContext(), "Data", Toast.LENGTH_SHORT).show();
                    spin_date.setVisibility(View.VISIBLE);
                    spin_local.setVisibility(View.GONE);
                    spin_genre.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionControl.logout();
            }
        });
    }
}