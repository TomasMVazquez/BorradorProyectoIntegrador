package com.example.digital.borradorproyectointegrador.view.Fragments;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.digital.borradorproyectointegrador.R;
import com.example.digital.borradorproyectointegrador.controller.ControllerGeneros;
import com.example.digital.borradorproyectointegrador.model.genero.Genero;
import com.example.digital.borradorproyectointegrador.util.ResultListener;
import com.example.digital.borradorproyectointegrador.view.Adaptadores.AdaptadorGeneros;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FiltroFragment extends DialogFragment implements AdaptadorGeneros.contadorFiltros {

    public static final String KEY_TAB="tab";

    private AdaptadorGeneros adaptadorGeneros;
    private Integer contadorFiltros = 0;

    public FiltroFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_filtro, container, false);

        adaptadorGeneros = new AdaptadorGeneros(this, new ArrayList<Genero>());

        Bundle bundle = getArguments();
        Integer tab = (Integer) bundle.getInt(KEY_TAB);
        ControllerGeneros controllerGeneros = new ControllerGeneros();
        contadorFiltros = 0;
        switch (tab){
            case 0:
                controllerGeneros.entregarGeneros(view.getContext(), new ResultListener<List<Genero>>() {
                    @Override
                    public void finish(List<Genero> Resultado) {
                        adaptadorGeneros.setGeneroList(Resultado);
                    }
                });
                break;
            case 1:
                controllerGeneros.entregarGenerosTV(view.getContext(), new ResultListener<List<Genero>>() {
                    @Override
                    public void finish(List<Genero> Resultado) {
                        adaptadorGeneros.setGeneroList(Resultado);
                    }
                });
                break;
            case 2:

                break;
        }

        RecyclerView recyclerView = view.findViewById(R.id.recyclerFiltros);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(llm);

        recyclerView.setAdapter(adaptadorGeneros);

        Button aplicar = view.findViewById(R.id.aplicarFiltros);

        aplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Integer> seleccionados = listaGeneros();
                //Implementar llamada al activity para pasar la lista de categorias
            }
        });

        return view;
    }

    @Override
    public void sumarContadorFiltros() {
        contadorFiltros = contadorFiltros + 1;

        if (contadorFiltros>=4) {
            adaptadorGeneros.finLugar();
        }
    }

    @Override
    public void restarContadorFiltros() {
        contadorFiltros = contadorFiltros - 1;
    }

    @Override
    public List<Integer> listaGeneros() {
        return adaptadorGeneros.darListaSeleccionados();
    }


}
