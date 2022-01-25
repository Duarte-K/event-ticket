package com.example.eventticket.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eventticket.R;
import com.example.eventticket.utils.MaskEditUtil;

public class RegisterEventActivity extends AppCompatActivity {

    private Button btnRegister;
    private LinearLayout layout;
    private ImageButton btnAddArtist, btnRemoveArtist;
    private EditText name, desc, local, date, hour, artist;

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
        artist = findViewById(R.id.et_Artist);

        //Spinner de gênero
        Spinner spinner_genre = findViewById(R.id.sp_genre);
        String[] events = getResources().getStringArray(R.array.list_genre);
        ArrayAdapter<String> adapterGenre = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, events);
        adapterGenre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_genre.setAdapter(adapterGenre);

        //Spinner de localização
        Spinner spinner_city = findViewById(R.id.sp_city);
        String[] cities = getResources().getStringArray(R.array.list_city);
        ArrayAdapter<String> adapterCity = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_city.setAdapter(adapterCity);

        //Máscaras de data e hora
        date.addTextChangedListener(MaskEditUtil.mask(date, MaskEditUtil.FORMAT_DATE));
        hour.addTextChangedListener(MaskEditUtil.mask(hour, MaskEditUtil.FORMAT_HOUR));

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

            }
        });
    }

    public void addArtist(){
        final View itemView = getLayoutInflater().inflate(R.layout.artist,null,false);

        EditText editText = itemView.findViewById(R.id.et_Artist);
        ImageButton imageBtnAddArtist =  itemView.findViewById(R.id.btnAdd);
        ImageButton imageBtnRemoveArtist = itemView.findViewById(R.id.btnRemove);


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
}