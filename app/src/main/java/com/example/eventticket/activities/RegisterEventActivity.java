package com.example.eventticket.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eventticket.R;

public class RegisterEventActivity extends AppCompatActivity {

    private LinearLayout layout;
    private ImageButton btnAddArtist, btnRemoveArtist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_event);

        layout = findViewById(R.id.linearLayout_artists);
        btnAddArtist = findViewById(R.id.btn_addArtist);
        btnRemoveArtist = findViewById(R.id.btn_removeArtist);

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
    }

    public void addArtist(){
        final View itemView = getLayoutInflater().inflate(R.layout.artist,null,false);

        EditText editText = itemView.findViewById(R.id.et_Artist);
        ImageButton imageBtnAddItem =  itemView.findViewById(R.id.btnAdd);
        ImageButton imageBtnRemoveItem = itemView.findViewById(R.id.btnRemove);


        imageBtnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addArtist();
            }
        });
        imageBtnRemoveItem.setOnClickListener(new View.OnClickListener() {
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
            ImageButton imageBtnAddItem = artistViewChild.findViewById(R.id.btnAdd);
            ImageButton imageBtnRemoveItem = artistViewChild.findViewById(R.id.btnRemove);
            imageBtnAddItem.setVisibility(View.GONE);
            imageBtnRemoveItem.setVisibility(View.VISIBLE);

        }

    }
}