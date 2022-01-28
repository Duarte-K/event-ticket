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
import com.example.eventticket.model.SessionModel;
import com.example.eventticket.model.UserModel;
import com.example.eventticket.service.ServiceAuthenticate;
import com.example.eventticket.service.ServiceGenerator;
import com.google.gson.Gson;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin, btnRegister;
    private SessionModel session;
    private EditText user,password;
    public static final String TAG = "Tag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new SessionModel(getApplicationContext());

        if(!session.getUserName().equals("") ) {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            finish();
        }

        user = findViewById(R.id.et_UserLogin);
        password = findViewById(R.id.et_passwordLogin);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterUserActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check();
                Intent intent = new Intent(getApplicationContext(), HomeAdminActivity.class);
                startActivity(intent);
            }
        });
    }

    public void check(){
        String paramUser = user.getText().toString();
        String paramPassword = password.getText().toString();

        if (paramUser == null || paramUser.equals("")) {
            user.setError("Preencha o campo parar continuar");
        } else {
            if (paramPassword == null || paramPassword.equals("")) {
                password.setError("Preencha o campo parar continuar");
            } else {
                UserModel user = new UserModel();
                user.setName(paramUser);
                user.setPassword(paramPassword);
                final String jsonUser = new Gson().toJson(user); //tranforma o objeto User em json
                Log.i("JSON_LOGIN", jsonUser);
                RequestBody objectJson = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonUser); //Transforma o json em um requestbody
                retrofitSearchUser(objectJson,paramUser); //metodo que chama a api de autenticação

            }
        }
    }

    public void retrofitSearchUser(RequestBody objectJson, String user){
        ServiceAuthenticate service = ServiceGenerator.createService(ServiceAuthenticate.class);

        Call<UserModel> call = service.authenticateUser(objectJson); //chama o metodo de buscar os usuarios. userInput,passwordInput, , String passwordInput

        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {

                //Verifica se de sucesso na chamada.
                if (response.isSuccessful()) {

                    UserModel respostaServidor = response.body();

                    //verifica aqui se o corpo da resposta não é nulo
                    if (respostaServidor != null) {
                        //Passar os dados para o adapter e setta a recyclerView.

                        userCheckExist(respostaServidor.getMessage(),user);
                    } else {
                        //resposta nula significa que o usuario não tem repositorios
                        Toast.makeText(getApplicationContext(),"Erro: resposta nula", Toast.LENGTH_LONG).show();

                    }

                } else {

                    Toast.makeText(getApplicationContext(),"Erro ao buscar usuário", Toast.LENGTH_LONG).show();
                    // segura os erros de requisição
                    ResponseBody errorBody = response.errorBody();
                    Log.e(TAG, "Error: "+ errorBody);

                }
            }

            //Metodo de falha na chamada
            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.e(TAG, "Erro: "+t.getMessage());
                Toast.makeText(getApplicationContext(),"Erro na chamada ao servidor", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    //checka a menssagem de retorno da api, se encontrar usuário atribui uma sessão e libera para a HomeActivity
    public void userCheckExist(String message, String paramUser){
        Log.i("Resposta Login", " "+message);
        if(message.equals("deu certo")){
            session.setUserName(paramUser);
            if(session.getUserName().equals("usuário")) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }else{
                Intent intent = new Intent(getApplicationContext(), HomeAdminActivity.class);
                startActivity(intent);
                finish();
            }
        }else{
            Toast.makeText(getApplicationContext(), "Erro:usuario ou senha incorretos ", Toast.LENGTH_SHORT).show();
        }
    }
}