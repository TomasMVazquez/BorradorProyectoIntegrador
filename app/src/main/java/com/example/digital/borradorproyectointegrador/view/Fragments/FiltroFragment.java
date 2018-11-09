package com.example.digital.borradorproyectointegrador.view.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.digital.borradorproyectointegrador.R;
import com.example.digital.borradorproyectointegrador.controller.ControllerGeneros;
import com.example.digital.borradorproyectointegrador.model.genero.Genero;
import com.example.digital.borradorproyectointegrador.util.ResultListener;
import com.example.digital.borradorproyectointegrador.view.Adaptadores.AdaptadorGeneros;
import com.example.digital.borradorproyectointegrador.view.Filtrar_Generos;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FiltroFragment extends Fragment {

    private AdaptadorGeneros adaptadorGeneros;

    public FiltroFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filtro, container, false);
        adaptadorGeneros = new AdaptadorGeneros(new ArrayList<Genero>());

        ControllerGeneros controllerGeneros = new ControllerGeneros();
        controllerGeneros.entregarGeneros(view.getContext(), new ResultListener<List<Genero>>() {
            @Override
            public void finish(List<Genero> Resultado) {
                adaptadorGeneros.setGeneroList(Resultado);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.recyclerFiltros);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(llm);

        recyclerView.setAdapter(adaptadorGeneros);
        return view;
    }

}
