package com.example.eventticket.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eventticket.R;
import com.example.eventticket.model.SessionModel;
import com.example.eventticket.model.UserModel;
import com.example.eventticket.service.ServiceGenerator;
import com.example.eventticket.service.ServiceRegisterUser;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterUserActivity extends AppCompatActivity {
    private UserModel userModel;
    private EditText user, email, password;
    private Button btnRegisterUser;
    private SessionModel session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        btnRegisterUser = findViewById(R.id.btn_registerUser);
        user = findViewById(R.id.et_userRegister);
        email = findViewById(R.id.et_emailRegister);
        password = findViewById(R.id.et_passwordRegister);

        session = new SessionModel(getApplicationContext());

        //Se já existir uma sessão, vai automaticamente para tela home
        if(!session.getUserName().equals("") ) {
            if(session.getUserName().equals("admin")){
                Intent intent = new Intent(getApplicationContext(), HomeAdminActivity.class);
                startActivity(intent);
                finish();
            }else{
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }
        btnRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkConnected()){
                    check();
                }else{
                    Toast.makeText(getApplicationContext(), "Sem conexão com a internet", Toast.LENGTH_SHORT).show();
                }
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
        }else if(!isValidEmail(em)){
            email.setError("Digite um email válido");
        }else if(pass.equals("")){
            password.setError("Preencha o campo para continuar");
        }else{
            userModel = new UserModel();
            userModel.setAccess("usuario");
            userModel.setName(us);
            userModel.setEmail(em);
            userModel.setPassword(pass);
            //Log.i("Valores", "os valores são: "+ userModel.getAccess()+","+userModel.getName()+","+userModel.getEmail()+","+userModel.getPassword());
            //final String jsonUser = new Gson().toJson(userModel); //tranforma o objeto em json
            //Log.i("JSON",jsonUser);
            //RequestBody objectUserJson = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonUser); //Transforma o json em um requestbody
            retrofitRegisterUser(userModel);
        }
    }

    public void retrofitRegisterUser(UserModel user){
        ServiceRegisterUser service = ServiceGenerator.createService(ServiceRegisterUser.class);

        Call<UserModel> call = service.registerUser(user);

        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {

                //Verifica se de sucesso na chamada.
                if (response.isSuccessful()) {

                    UserModel respostaServidor = response.body();

                    //verifica aqui se o corpo da resposta não é nulo
                    if (respostaServidor != null) {
                        Log.i("Valores", "os valores são: "+ userModel.getAccess()+","+userModel.getName()+","+userModel.getEmail()+","+userModel.getPassword());
                        session.setUserName(userModel.getName());
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);

                    } else {
                        //resposta nula
                        Toast.makeText(getApplicationContext(), "Erro: resposta nula", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"Erro ao cadastrar usuário", Toast.LENGTH_LONG).show();
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

    //Método para verificar se tem acesso a internet
    @SuppressLint("MissingPermission")
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    //Método para checar se o email é válido
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}




