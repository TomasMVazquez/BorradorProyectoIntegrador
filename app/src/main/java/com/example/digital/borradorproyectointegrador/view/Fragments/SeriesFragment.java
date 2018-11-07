package com.example.digital.borradorproyectointegrador.view.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
        final RecyclerView recyclerViewPrimero = view.findViewById(R.id.recylcerViewSeriesPrimero);
        final RecyclerView recyclerViewSegundo = view.findViewById(R.id.recylcerViewSeriesSegundo);
        final RecyclerView recyclerViewTercero = view.findViewById(R.id.recylcerViewSeriesTercero);
        final RecyclerView recyclerViewCuarto = view.findViewById(R.id.recylcerViewSeriesCuarto);
        final RecyclerView recyclerViewQuinto = view.findViewById(R.id.recylcerViewSeriesQuinto);


        // Lista de Generos de series: https://api.themoviedb.org/3/genre/tv/list?api_key=656020b1f06a98f4d73cadd7336e7790&language=en-US

        ControllerSerie controllerSerie = new ControllerSerie();
        controllerSerie.entregarSerie(view.getContext(), new ResultListener<List<Serie>>() {
            @Override
            public void finish(List<Serie> Resultado) {
                cargarRecycler(view.getContext(),recyclerViewPrimero,Resultado, SeriesFragment.this);
            }
        });
        controllerSerie.entregarSerieGeneros(view.getContext(), 10759, new ResultListener<List<Serie>>() {
            @Override
            public void finish(List<Serie> Resultado) {
                cargarRecycler(view.getContext(),recyclerViewSegundo,Resultado,SeriesFragment.this);
            }
        });
        controllerSerie.entregarSerieGeneros(view.getContext(), 16, new ResultListener<List<Serie>>() {
            @Override
            public void finish(List<Serie> Resultado) {
                cargarRecycler(view.getContext(),recyclerViewTercero,Resultado,SeriesFragment.this);
            }
        });
        controllerSerie.entregarSerieGeneros(view.getContext(), 80, new ResultListener<List<Serie>>() {
            @Override
            public void finish(List<Serie> Resultado) {
                cargarRecycler(view.getContext(),recyclerViewCuarto,Resultado,SeriesFragment.this);
            }
        });
        controllerSerie.entregarSerieGeneros(view.getContext(), 35, new ResultListener<List<Serie>>() {
            @Override
            public void finish(List<Serie> Resultado) {
                cargarRecycler(view.getContext(),recyclerViewQuinto,Resultado,SeriesFragment.this);
            }
        });


        return view;
    }

    @Override
    public void irTrailer(Serie serie) {
        Intent intent = new Intent(getActivity(), TrailerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(TrailerActivity.KEY_NOMBRE, serie.getName());
        bundle.putInt(String.valueOf(TrailerActivity.KEY_ID), serie.getId());
        bundle.putString(TrailerActivity.KEY_RESUMEN, serie.getOverview());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void cargarRecycler(Context context, RecyclerView recyclerView, List<Serie> series, SerieAdaptador.AdapterSerieInterface escuchador){
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(llm);

        SerieAdaptador serieAdaptador = new SerieAdaptador(context,series,escuchador);
        recyclerView.setAdapter(serieAdaptador);
    }
}
