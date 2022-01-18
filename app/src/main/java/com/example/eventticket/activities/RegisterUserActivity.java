package com.example.eventticket.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eventticket.R;

public class RegisterUserActivity extends AppCompatActivity {

    private EditText user, email, password;
    private Button btnRegisterUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        btnRegisterUser = findViewById(R.id.btn_registerUser);
        user = findViewById(R.id.et_userRegister);
        email = findViewById(R.id.et_emailRegister);
        password = findViewById(R.id.et_passwordRegister);

        btnRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public boolean check(){
        String us = user.getText().toString();
        String em = email.getText().toString();
        String pass = password.getText().toString();

        if(us.equals("")){
            user.setError("Preencha o campo para continuar");
            return false;
        }else if(em.equals("")){
            email.setError("Preencha o campo para continuar");
            return false;
        }else if(pass.equals("")){
            password.setError("Preencha o campo para continuar");
            return false;
        }
        return true;
    }
}