package com.example.eventticket.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eventticket.R;

public class HomeAdminActivity extends AppCompatActivity {
    private Button btnRegisterEvent, btnSeeEvent, btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

         btnRegisterEvent = findViewById(R.id.btn_registerEvent);
         btnSeeEvent = findViewById(R.id.btn_seeEvent);
         btnLogout = findViewById(R.id.btn_logoutAdm);

         btnRegisterEvent.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(getApplicationContext(), RegisterEventActivity.class);
                 startActivity(intent);
             }
         });

        btnSeeEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}