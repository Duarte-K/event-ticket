package com.example.eventticket.activities;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventticket.R;
import com.example.eventticket.control.SessionControl;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageView logout;
    private SessionControl sessionControl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sessionControl = new SessionControl(getApplicationContext());
        sessionControl.checkSession();

        logout = findViewById(R.id.img_logout);

        //Config RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setAdapter();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionControl.logout();
            }
        });
    }
}