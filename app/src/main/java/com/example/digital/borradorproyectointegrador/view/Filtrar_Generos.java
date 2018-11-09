package com.example.digital.borradorproyectointegrador.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.digital.borradorproyectointegrador.R;
import com.example.digital.borradorproyectointegrador.controller.ControllerGeneros;
import com.example.digital.borradorproyectointegrador.model.genero.Genero;
import com.example.digital.borradorproyectointegrador.util.ResultListener;
import com.example.digital.borradorproyectointegrador.view.Adaptadores.AdaptadorGeneros;
import com.example.digital.borradorproyectointegrador.view.Adaptadores.PeliculaAdaptador;

import java.util.ArrayList;
import java.util.List;

public class Filtrar_Generos extends AppCompatActivity {

    private AdaptadorGeneros adaptadorGeneros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtrar__generos);

        adaptadorGeneros = new AdaptadorGeneros(new ArrayList<Genero>());

        ControllerGeneros controllerGeneros = new ControllerGeneros();
        controllerGeneros.entregarGeneros(this, new ResultListener<List<Genero>>() {
            @Override
            public void finish(List<Genero> Resultado) {
                adaptadorGeneros.setGeneroList(Resultado);
                Toast.makeText(Filtrar_Generos.this, Resultado.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerFiltros);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(llm);

        recyclerView.setAdapter(adaptadorGeneros);

    }
}
