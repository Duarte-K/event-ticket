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
import com.example.eventticket.control.SessionControl;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageView logout;
    private SessionControl sessionControl;
    private TextView hello;
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


        //Config RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setAdapter();

        //Spinner de Filtro
        Spinner spinner_filter = findViewById(R.id.sp_search);
        String[] filters = getResources().getStringArray(R.array.list_search);
        ArrayAdapter<String> adapterFilter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adapterFilter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_filter.setAdapter(adapterFilter);

        spinner_filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AdapterView.OnItemClickListener filt = spinner_filter.getOnItemClickListener();
                if(filt.equals("Todos")){
                    Toast.makeText(getApplicationContext(), "Todos", Toast.LENGTH_SHORT).show();
                }else if (filt.equals("Cidade")){
                    Toast.makeText(getApplicationContext(), "cidade", Toast.LENGTH_SHORT).show();
                }else if(filt.equals("Gênero")){
                    Toast.makeText(getApplicationContext(), "gênero", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "data", Toast.LENGTH_SHORT).show();
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