package com.example.digital.borradorproyectointegrador;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class TrailerActivity extends AppCompatActivity {
    public static final String KEY_IMAGE = "image";
    public static final String KEY_RATING_BAR = "rating bar";
    public static final String KEY_CANT_ESTRELLAS = "cantEstrellas";
    public static final String KEY_NOMBRE = "nombre";
    public static final String KEY_RESUMEN = "resumen";



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);

        //Llamar a la action bar para mostrar
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbarTrailer);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(null);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }





        // COMUNICACION ENTRE FRAGMENT/ACTIVITY
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        // DATOS
        String imageData = bundle.getString(KEY_IMAGE);
        Integer cantEstrellasData = bundle.getInt(KEY_CANT_ESTRELLAS);
        String nombre = bundle.getString(KEY_NOMBRE);
        Integer resumen = bundle.getInt(KEY_RESUMEN);


        // COMPONENTES
        RatingBar ratingBar = findViewById(R.id.rbShowRoom);
        TextView textViewNombre = findViewById(R.id.textViewTituloTrailer);
        TextView textViewResumen = findViewById(R.id.textViewResumenDetalle);


        // Seteo
        ratingBar.setRating(cantEstrellasData);
        textViewNombre.setText(nombre);
        textViewResumen.setText(resumen);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() ==android.R.id.home)
            finish();

        switch (item.getItemId()){
            case R.id.itemAccount:
                Intent intentAccount = new Intent(TrailerActivity.this, LogIn.class);
                startActivity(intentAccount);
                Toast.makeText(this, "Item 1 Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.itemSearch:

                Toast.makeText(this, "Item Search Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item1:
                Toast.makeText(this, "Item 1 Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2:
                Toast.makeText(this, "Item 2 Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item3:
                Toast.makeText(this, "Item 3 Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subItem1:
                Toast.makeText(this, "Sub Item 1 Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subItem2:
                Toast.makeText(this, "Sub Item 2 Selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }




}
