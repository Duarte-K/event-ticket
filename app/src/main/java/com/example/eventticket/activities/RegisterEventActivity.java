package com.example.eventticket.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eventticket.R;
import com.example.eventticket.model.ArtistsModel;
import com.example.eventticket.model.EventModel;
import com.example.eventticket.service.ServiceGenerator;
import com.example.eventticket.service.ServiceRegisterEvent;
import com.example.eventticket.utils.MaskEditUtil;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterEventActivity extends AppCompatActivity {
    private String city, genre;
    private ImageView btnPhoto;
    private ArrayList<ArtistsModel> artistsList = new ArrayList<>();
    private EventModel eventModel = new EventModel();
    private Button btnRegister;
    private LinearLayout layout;
    private ImageButton btnAddArtist, btnRemoveArtist;
    private EditText name, desc, local, date, hour, art;

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_event);

        layout = findViewById(R.id.linearLayout_artists);
        btnRegister = findViewById(R.id.btn_RegisterEvent);
        btnAddArtist = findViewById(R.id.btn_addArtist);
        btnRemoveArtist = findViewById(R.id.btn_removeArtist);
        name = findViewById(R.id.et_NameEvent);
        desc = findViewById(R.id.et_DescriptionEvent);
        local = findViewById(R.id.et_LocateEvent);
        date = findViewById(R.id.et_DateEvent);
        hour = findViewById(R.id.et_HourEvent);
        btnPhoto = findViewById(R.id.btn_photo);



        //Spinner de gênero
        Spinner spinner_genre = findViewById(R.id.sp_genre);
        String[] events = getResources().getStringArray(R.array.list_genre);
        ArrayAdapter<String> adapterGenre = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, events);
        adapterGenre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if(spinner_genre != null) {
            spinner_genre.setAdapter(adapterGenre);
        }
        spinner_genre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    eventModel.setGenre("Casual");
                    Toast.makeText(getApplicationContext(), eventModel.getGenre(), Toast.LENGTH_SHORT).show();
                }else if(position == 1){
                    eventModel.setGenre("Formal");
                    Toast.makeText(getApplicationContext(), eventModel.getGenre(), Toast.LENGTH_SHORT).show();
                }else if(position == 2){
                    eventModel.setGenre("Festa");
                    Toast.makeText(getApplicationContext(), eventModel.getGenre(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Spinner de localização
        Spinner spinner_city = findViewById(R.id.sp_city);
        String[] cities = getResources().getStringArray(R.array.list_city);
        ArrayAdapter<String> adapterCity = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cities);
        adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_city.setAdapter(adapterCity);
        if(spinner_city != null) {
            spinner_city.setAdapter(adapterCity);
        }
        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    eventModel.setCity("Fortaleza");
                    Toast.makeText(getApplicationContext(), eventModel.getCity(), Toast.LENGTH_SHORT).show();
                }else if(position == 1){
                    eventModel.setCity("São Paulo");
                    Toast.makeText(getApplicationContext(), eventModel.getCity(), Toast.LENGTH_SHORT).show();
                }else if(position == 2){
                    eventModel.setCity("Rio de Janeiro");
                    Toast.makeText(getApplicationContext(), eventModel.getCity(), Toast.LENGTH_SHORT).show();
                }
                Log.i("spinners", "cidade: "+eventModel.getCity()+", genero: "+eventModel.getGenre());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Máscaras de data e hora
        date.addTextChangedListener(MaskEditUtil.mask(date, MaskEditUtil.FORMAT_DATE));
        hour.addTextChangedListener(MaskEditUtil.mask(hour, MaskEditUtil.FORMAT_HOUR));

                //Transformação de imagem para salvar no banco
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        imagem.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//        byte imagesByte[] = stream.toByteArray();
//
//              //Como Recuperar do banco
//        byte[] outImage = eventModel.getPicture();
//        ByteArrayInputStream imagemStream = new ByteArrayInputStream(outImage);
//        Bitmap imageBitmap = BitmapFactory.decodeStream(imagemStream);
//        imagem.setImageBitmap(imageBitmap);

        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        btnAddArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addArtist();
            }
        });

        btnRemoveArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.removeView(layout.getChildAt(0));
                for(int i=0;i<layout.getChildCount();i++) {

                    View itemsViewChild = layout.getChildAt(i);

                    EditText editTextItem = itemsViewChild.findViewById(R.id.et_Artist);
                    if(i >= 9){
                        editTextItem.setHint("Artista "+(i+1));
                    }else{
                        editTextItem.setHint("Artista 0"+(i+1));
                    }
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkConnected()){
                    checking();
                }else {
                    Toast.makeText(getApplicationContext(), "Sem conexão com a internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                try {
                    Uri imageUri = data.getData();
                    Bitmap photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    Bitmap photoRedim = Bitmap.createScaledBitmap(photo, 256, 256, true);
                    btnPhoto.setImageBitmap(photoRedim);
                    byte[] imageToByte;
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    photoRedim.compress(Bitmap.CompressFormat.PNG, 70, stream);
                    imageToByte = stream.toByteArray();
                }catch (Exception e){

                }
            }
        }
    }

    public void addArtist(){
        final View itemView = getLayoutInflater().inflate(R.layout.artist,null,false);

        EditText editText = itemView.findViewById(R.id.et_Artist);
        ImageButton imageBtnAddArtist =  itemView.findViewById(R.id.btn_addArtist);
        ImageButton imageBtnRemoveArtist = itemView.findViewById(R.id.btn_removeArtist);


        imageBtnAddArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addArtist();
            }
        });
        imageBtnRemoveArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.removeView(itemView);
                for(int i=0;i<layout.getChildCount();i++) {

                    View artistViewChild = layout.getChildAt(i);

                    EditText editTextItem = artistViewChild.findViewById(R.id.et_Artist);
                    if(i >= 9){
                        editTextItem.setHint("Artista "+(i+1));
                    }else{
                        editTextItem.setHint("Artista 0"+(i+1));
                    }
                }

            }
        });
        changeButton();
        layout.addView(itemView);
        for(int i=0;i<layout.getChildCount();i++) {

            View artistViewChild = layout.getChildAt(i);

            EditText editTextItem = artistViewChild.findViewById(R.id.et_Artist);
            if(i >= 9){
                editTextItem.setHint("Artista "+(i+1));
            }else{
                editTextItem.setHint("Artista 0"+(i+1));
            }
        }
    }

    public void changeButton(){
        int count = layout.getChildCount();
        for(int i=0;i<count;i++){
            View artistViewChild = layout.getChildAt(i);
            ImageButton imageBtnAddArtist = artistViewChild.findViewById(R.id.btn_addArtist);
            ImageButton imageBtnRemoveArtist = artistViewChild.findViewById(R.id.btn_removeArtist);
            imageBtnAddArtist.setVisibility(View.GONE);
            imageBtnRemoveArtist.setVisibility(View.VISIBLE);

        }

    }

    public void retrofitRegisterEvent(RequestBody objectJson){
        ServiceRegisterEvent service = ServiceGenerator.createService(ServiceRegisterEvent.class);

        Call<EventModel> call = service.registerEvent(objectJson);

        call.enqueue(new Callback<EventModel>() {
            @Override
            public void onResponse(Call<EventModel> call, Response<EventModel> response) {

                //Verifica se de sucesso na chamada.
                if (response.isSuccessful()) {

                    EventModel respostaServidor = response.body();

                    //verifica aqui se o corpo da resposta não é nulo
                    if (respostaServidor != null) {
                        Toast.makeText(getApplicationContext(), "mensagem: "+respostaServidor.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.i("RETORNO_SERVICE", "mensagem: "+respostaServidor.getMessage());
                        Log.i("RETORNO_BODY_SERVICE", "body: "+respostaServidor);
                        checkRegisteredEvent(respostaServidor.getMessage());
                        Toast.makeText(getApplicationContext(), "Evento cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                    } else {
                        //resposta nula
                        Toast.makeText(getApplicationContext(), "Erro: resposta nula", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"Erro ao registrar evento", Toast.LENGTH_LONG).show();
                    // segura os erros de requisição
                    ResponseBody errorBody = response.errorBody();
                    Log.e("Erro: ", " "+errorBody);

                }
            }

            //Metodo de falha na chamada
            @Override
            public void onFailure(Call<EventModel> call, Throwable t) {
                Log.e("Erro: ",t.getMessage());
                Toast.makeText(getApplicationContext(),"Erro na chamada ao servidor", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    public void checkRegisteredEvent(String msg){
        if(msg.equals("Evento cadastrado")){
            Toast.makeText(getApplicationContext(), "Evento Cadastrado com sucesso", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(), "Evento não foi cadastrado", Toast.LENGTH_SHORT).show();
        }
    }

    //Método para verificar se tem acesso a internet
    @SuppressLint("MissingPermission")
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public void checking(){
        String n = name.getText().toString();
        String de = desc.getText().toString();
        String l = local.getText().toString();
        String da = date.getText().toString();
        String h = hour.getText().toString();

        if(n.equals("")){
            name.setError("Preencha o campo para continuar");
        }else if(de.equals("")){
            desc.setError("Preencha o campo para continuar");
        }else if(l.equals("")){
            local.setError("Preencha o campo para continuar");
        }else if(da.equals("")){
            date.setError("Preencha o campo para continuar");
        }else if(h.equals("")){
            hour.setError("Preencha o campo para continuar");
        }else{
            eventModel.setName(n);
            eventModel.setDescription(de);
            eventModel.setLocal(l);
            eventModel.setPicture(imageViewToByte(btnPhoto));
            eventModel.setDate(da);
            eventModel.setHour(h);

            if (checkArtists()) {
                String[] itemsArtists = new String[artistsList.size()];
                int cont = 0;
                for (ArtistsModel artists:artistsList) {
                    itemsArtists[cont] = artists.getDescription();
                    cont++;
                }
            }

            //retrofitRegisterEvent(eventModel);
        }
    }

    public static byte[] imageViewToByte(ImageView image){
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress( Bitmap.CompressFormat.PNG, 100, stream );
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public Boolean checkArtists(){
        artistsList.clear();
        boolean result = true;

        for(int i=0;i<layout.getChildCount();i++) {

            View itemsViewChild = layout.getChildAt(i);

            EditText editTextItem = itemsViewChild.findViewById(R.id.et_Artist);

            ArtistsModel artist = new ArtistsModel();
            if (i == 0) {
                if(!editTextItem.getText().toString().equals("")){
                    artist.setDescription(editTextItem.getText().toString());
                    artistsList.add(artist);
                }else{
                    editTextItem.setError("Preencha o campo para continuar");
                    result = false;
                    break;
                }
            }else {
                if (!editTextItem.getText().toString().equals("")) {
                    artist.setDescription(editTextItem.getText().toString());
                    artistsList.add(artist);
                }

            }

        }

        return result;
    }
}