package com.example.digital.borradorproyectointegrador.view.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.digital.borradorproyectointegrador.R;
import com.example.digital.borradorproyectointegrador.controller.ControllerPelicula;
import com.example.digital.borradorproyectointegrador.controller.ControllerSerie;
import com.example.digital.borradorproyectointegrador.model.pelicula.Peliculas;
import com.example.digital.borradorproyectointegrador.model.serie.Serie;
import com.example.digital.borradorproyectointegrador.util.ResultListener;
import com.example.digital.borradorproyectointegrador.view.Adaptadores.PeliculaAdaptador;
import com.example.digital.borradorproyectointegrador.view.Adaptadores.SerieAdaptador;
import com.example.digital.borradorproyectointegrador.view.AgregarComentarioActivity;
import com.example.digital.borradorproyectointegrador.view.TrailerActivity;

import java.util.List;


public class SeriesFragment extends Fragment implements SerieAdaptador.AdapterSerieInterface {


    public SeriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_series, container, false);

        //Recycler view
        //Lista
        final RecyclerView recyclerViewSeri = view.findViewById(R.id.recylcerViewSeries);

        ControllerSerie controllerSerie = new ControllerSerie();

        controllerSerie.entregarSerie(view.getContext(), new ResultListener<List<Serie>>() {
            @Override
            public void finish(List<Serie> Resultado) {
                cargarRecyclerGrid(view.getContext(),recyclerViewSeri,Resultado,SeriesFragment.this);
            }
        });


        return view;
    }

    @Override
    public void irTrailer(Serie serie) {
        Intent intent = new Intent(getActivity(), TrailerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(TrailerActivity.KEY_TIPO,2);
        bundle.putString(TrailerActivity.KEY_NOMBRE, serie.getName());
        bundle.putInt(String.valueOf(TrailerActivity.KEY_ID), serie.getId());
        bundle.putString(TrailerActivity.KEY_RESUMEN, serie.getOverview());
        bundle.putString(TrailerActivity.KEY_POSTER_PATH,serie.getPoster_path());
        intent.putExtras(bundle);
        startActivity(intent);
    }


    public void cargarRecyclerGrid(Context context, RecyclerView recyclerView,List<Serie> series, SerieAdaptador.AdapterSerieInterface escuchador){
        recyclerView.setHasFixedSize(true);

        GridLayoutManager glm = new GridLayoutManager(context,3,1,false);
        //LinearLayoutManager llm = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(glm);

        SerieAdaptador serieAdaptador = new SerieAdaptador(context,series,escuchador);
        recyclerView.setAdapter(serieAdaptador);
    }


    public void cargarRecycler(Context context, RecyclerView recyclerView, List<Serie> series, SerieAdaptador.AdapterSerieInterface escuchador){
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(llm);

        SerieAdaptador serieAdaptador = new SerieAdaptador(context,series,escuchador);
        recyclerView.setAdapter(serieAdaptador);
    }
}
