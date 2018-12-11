package com.example.digital.borradorproyectointegrador.view.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.example.digital.borradorproyectointegrador.R;
import com.example.digital.borradorproyectointegrador.controller.ControllerGeneros;
import com.example.digital.borradorproyectointegrador.model.genero.Genero;
import com.example.digital.borradorproyectointegrador.util.ResultListener;
import com.example.digital.borradorproyectointegrador.view.Adaptadores.AdaptadorFiltros;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FiltroFragment extends DialogFragment implements AdaptadorFiltros.FiltrosInterface {

    public static final String KEY_TAB="tab";

    private AdaptadorFiltros adaptadorFiltros;

    private Integer tabFragment=null;

    public FiltroFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_filtro, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        adaptadorFiltros = new AdaptadorFiltros(new ArrayList<Genero>(),this);

        Bundle bundle = getArguments();
        final Integer tab = (Integer) bundle.getInt(KEY_TAB);

        ControllerGeneros controllerGeneros = new ControllerGeneros();

        switch (tab){
            case 0:
                controllerGeneros.entregarGeneros(view.getContext(), new ResultListener<List<Genero>>() {
                    @Override
                    public void finish(List<Genero> Resultado) {
                        adaptadorFiltros.setGeneroList(Resultado);
                        tabFragment = tab;
                    }
                });
                break;
            case 1:
                controllerGeneros.entregarGenerosTV(view.getContext(), new ResultListener<List<Genero>>() {
                    @Override
                    public void finish(List<Genero> Resultado) {
                        adaptadorFiltros.setGeneroList(Resultado);
                        tabFragment = tab;
                    }
                });
                break;
            case 2:

                tabFragment = tab;
                break;
        }

        RecyclerView recyclerView = view.findViewById(R.id.recyclerFiltros);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(llm);

        recyclerView.setAdapter(adaptadorFiltros);

        Button aplicar = view.findViewById(R.id.aplicarFiltros);


        aplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                List<Integer> seleccionados = listaGeneros();
//                //Implementar llamada al activity para pasar la lista de categorias
                FragmentInterface fragmentInterface = (FragmentInterface) getContext();
                fragmentInterface.mostrarTodos(tab);
                getActivity().getSupportFragmentManager().beginTransaction().remove(FiltroFragment.this).commit();
            }
        });

        return view;
    }

    @Override
    public void mostrarFiltros(Integer integer,String nombre) {
        FragmentInterface fragmentInterface = (FragmentInterface) getActivity();
        fragmentInterface.dameListaFiltro(integer,tabFragment,nombre);
        getActivity().getSupportFragmentManager().beginTransaction().remove(FiltroFragment.this).commit();
    }

    public interface FragmentInterface{
        public void dameListaFiltro(Integer filtro, Integer tab, String nombre);
        public void mostrarTodos(Integer tab);
    }



}
