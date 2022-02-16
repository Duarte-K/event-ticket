package com.example.eventticket.activities;


import android.os.Bundle;
import android.util.Log;
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
import com.example.eventticket.service.ServiceFilterGenre;
import com.example.eventticket.service.ServiceGenerator;
import com.example.eventticket.service.ServiceListEvent;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageView logout;
    private SessionControl sessionControl;
    private TextView hello;
    private EventModel event = new EventModel();
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


        //Config RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setAdapter(adapterEvent);

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
                    ListEvent();
                }else if (position == 1){
                    Toast.makeText(getApplicationContext(), "Cidade", Toast.LENGTH_SHORT).show();
                    spin_local.setVisibility(View.VISIBLE);
                    spin_date.setVisibility(View.GONE);
                    spin_genre.setVisibility(View.GONE);
                    //String cidade = event.getCity();
                    //FilterCity(cidade);
                }else if(position == 2){
                    Toast.makeText(getApplicationContext(), "Gênero", Toast.LENGTH_SHORT).show();
                    spin_genre.setVisibility(View.VISIBLE);
                    spin_local.setVisibility(View.GONE);
                    spin_date.setVisibility(View.GONE);
                    String genero = event.getGenre();
                    FilterGenre(genero);
                }else{
                    Toast.makeText(getApplicationContext(), "Data", Toast.LENGTH_SHORT).show();
                    spin_date.setVisibility(View.VISIBLE);
                    spin_local.setVisibility(View.GONE);
                    spin_genre.setVisibility(View.GONE);
                    //String data = event.getDate();
                    //FilterCity(data);
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

    public void ListEvent(){
        ServiceListEvent service = ServiceGenerator.createService(ServiceListEvent.class);

        Call<ArrayList<EventModel>> call = service.listEvent();

        call.enqueue(new Callback<ArrayList<EventModel>>() {
            @Override
            public void onResponse(Call<ArrayList<EventModel>> call, Response<ArrayList<EventModel>> response) {
                if (response.isSuccessful()) {

                    ArrayList<EventModel> listEvent = response.body();

                    //verifica aqui se o corpo da resposta não é nulo
                    if (listEvent != null) {
                        AdapterEvent adapterEvent = new AdapterEvent(listEvent);
                        recyclerView.setAdapter(adapterEvent);

                    } else {
                        Toast.makeText(getApplicationContext(), "Resposta nula do servidor", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    ResponseBody errorBody = response.errorBody();
                    Toast.makeText(getApplicationContext(), "Resposta não foi sucesso", Toast.LENGTH_SHORT).show();

                    // segura os erros de requisição
                    System.out.print(errorBody);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<EventModel>> call, Throwable t) {
                Log.e("Erro", "Erro: " + t.getMessage());
                Toast.makeText(getApplicationContext(), "Erro na chamada ao servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void FilterGenre(String genre){
        ServiceFilterGenre service = ServiceGenerator.createService(ServiceFilterGenre.class);

        Call<ArrayList<EventModel>> call = service.filtergenre(genre);

        call.enqueue(new Callback<ArrayList<EventModel>>() {
            @Override
            public void onResponse(Call<ArrayList<EventModel>> call, Response<ArrayList<EventModel>> response) {
                //Verifica se de sucesso na chamada.
                if (response.isSuccessful()) {

                    ArrayList<EventModel> genres = response.body();

                    //verifica aqui se o corpo da resposta não é nulo
                    if (genres != null) {
                        AdapterEvent adapterEvent = new AdapterEvent(genres);
                        recyclerView.setAdapter(adapterEvent);
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

            @Override
            public void onFailure(Call<ArrayList<EventModel>> call, Throwable t) {
                Log.e("Erro", "Erro: " + t.getMessage());
                Toast.makeText(getApplicationContext(), "Erro na chamada ao servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}