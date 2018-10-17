package com.example.digital.borradorproyectointegrador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

public class TrailerActivity extends AppCompatActivity {
    public static final String KEY_IMAGE = "image";
    public static final String KEY_RATING_BAR = "rating bar";
    public static final String KEY_CANT_ESTRELLAS = "cantEstrellas";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);


        // COMUNICACION ENTRE FRAGMENT/ACTIVITY
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        // DATOS
        String imageData = bundle.getString(KEY_IMAGE);
        Integer cantEstrellasData = bundle.getInt(KEY_CANT_ESTRELLAS);


        // COMPONENTES
        RatingBar ratingBar = findViewById(R.id.rbShowRoom);
        TextView textView = findViewById(R.id.textViewTituloTrailer);

        // Seteo
        ratingBar.setRating(cantEstrellasData);
        textView.setText(imageData);


    }
}
