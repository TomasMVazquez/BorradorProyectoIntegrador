package com.example.digital.borradorproyectointegrador;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements PeliculasFragment.OnFragmentInteractionListener {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Llamar a la action bar para mostrar
        android.support.v7.widget.Toolbar actionBar = findViewById(R.id.actionBar);
        setSupportActionBar(actionBar);

        //Llamar al FragmentPeliculas
        PeliculasFragment peliculasFragment = new PeliculasFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contenedorMain,peliculasFragment);
        fragmentTransaction.commit();

    }


    @Override
    public void onFragmentInteraction() {
        Intent intent = new Intent(this,LogIn.class);

        //Bundle bundle = new Bundle();
        //bundle.putString(MensajeFragment.KEY_MENSAJE, mensaje);
        //intent.putExtras(bundle);

        startActivity(intent);
    }
}
