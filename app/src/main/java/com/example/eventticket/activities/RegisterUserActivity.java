package com.example.eventticket.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eventticket.R;
import com.example.eventticket.model.UserModel;
import com.example.eventticket.service.ServiceGenerator;
import com.example.eventticket.service.ServiceRegisterUser;
import com.google.gson.Gson;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterUserActivity extends AppCompatActivity {
    private UserModel userModel;
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
                check();
            }
        });
    }

    public void check(){
        String us = user.getText().toString();
        String em = email.getText().toString();
        String pass = password.getText().toString();

        if(us.equals("")){
            user.setError("Preencha o campo para continuar");
        }else if(em.equals("")){
            email.setError("Preencha o campo para continuar");
        }else if(pass.equals("")){
            password.setError("Preencha o campo para continuar");
        }else{
            userModel.setAccess("usuário");
            userModel.setName(us);
            userModel.setEmail(em);
            userModel.setPassword(pass);
            final String jsonUser = new Gson().toJson(userModel); //tranforma o user da sessão em json
            RequestBody objectUserJson = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonUser); //Transforma o json em um requestbody
            retrofitRegisterUser(objectUserJson);
        }
    }

    public void retrofitRegisterUser(RequestBody objectJson){
        ServiceRegisterUser service = ServiceGenerator.createService(ServiceRegisterUser.class);

        Call<UserModel> call = service.registerUser(objectJson);

        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {

                //Verifica se de sucesso na chamada.
                if (response.isSuccessful()) {

                    UserModel respostaServidor = response.body();

                    //verifica aqui se o corpo da resposta não é nulo
                    if (respostaServidor != null) {
                        Log.i("RETORNO_SERVICE", "messagem: "+respostaServidor.getMessage());
                        checkRegisteredUser(respostaServidor.getMessage());
                    } else {
                        //resposta nula
                        Toast.makeText(getApplicationContext(), "Erro: resposta nula", Toast.LENGTH_LONG).show();
                    }
                } else {

                    Toast.makeText(getApplicationContext(),"Erro ao registrar venda", Toast.LENGTH_LONG).show();
                    // segura os erros de requisição
                    ResponseBody errorBody = response.errorBody();
                    Log.e("Erro: ", " "+errorBody);


                }
            }

            //Metodo de falha na chamada
            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.e("Erro: ",t.getMessage());
                Toast.makeText(getApplicationContext(),"Erro na chamada ao servidor", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void checkRegisteredUser(String msg) {
        if (msg.equals("Usuário cadastrado")) {
            Toast.makeText(getApplicationContext(), "Usuário Cadastrado com sucesso", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Erro:Usuário não cadastrado ", Toast.LENGTH_SHORT).show();
        }
    }
}

