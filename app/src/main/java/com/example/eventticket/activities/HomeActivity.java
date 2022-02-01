package com.example.eventticket.activities;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
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

        //Config RecyclerView
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        //recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setAdapter();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionControl.logout();
            }
        });
    }
}