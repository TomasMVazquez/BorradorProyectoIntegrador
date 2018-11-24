package com.example.digital.borradorproyectointegrador.view.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.digital.borradorproyectointegrador.controller.ControllerGeneros;
import com.example.digital.borradorproyectointegrador.controller.ControllerPelicula;
import com.example.digital.borradorproyectointegrador.model.genero.Genero;
import com.example.digital.borradorproyectointegrador.model.pelicula.Peliculas;
import com.example.digital.borradorproyectointegrador.util.ResultListener;
import com.example.digital.borradorproyectointegrador.R;
import com.example.digital.borradorproyectointegrador.view.Adaptadores.PeliculaAdaptador;
import com.example.digital.borradorproyectointegrador.view.MainActivity;
import com.example.digital.borradorproyectointegrador.view.TrailerActivity;

import java.util.ArrayList;
import java.util.List;


public class PeliculasFragment extends Fragment implements PeliculaAdaptador.AdapterPeliInterface {

    public static final String KEY_LISTA_FILTROS = "filtros";
    public static final String KEY_TAB = "tab";

    private OnFragmentInteractionListener mListener;
    private List<Integer> listaFiltros = new ArrayList<>();
    private List<Genero> generoList;
    private Bundle bundleFiltros;

    public PeliculasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_peliculas, container, false);

        //Recycler view
        //Lista
        final RecyclerView recyclerViewFav = view.findViewById(R.id.recylcerViewFavoritosPeliculas);
        final RecyclerView recyclerViewPeli = view.findViewById(R.id.recylcerViewPeliculas);

        ControllerPelicula controllerPelicula = new ControllerPelicula();

        controllerPelicula.entregarPeliculas(view.getContext(), new ResultListener<List<Peliculas>>() {
            @Override
            public void finish(List<Peliculas> Resultado) {
                cargarRecyclerGrid(view.getContext(),recyclerViewPeli,Resultado,PeliculasFragment.this);
            }
        });

        return view;
    }



    public void cargarRecyclerGrid(Context context, RecyclerView recyclerView,List<Peliculas> peliculas, PeliculaAdaptador.AdapterPeliInterface escuchador){
        recyclerView.setHasFixedSize(true);

        GridLayoutManager glm = new GridLayoutManager(context,3,1,false);
        //LinearLayoutManager llm = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(glm);

        PeliculaAdaptador peliculaAdaptador = new PeliculaAdaptador(context,peliculas,escuchador);
        recyclerView.setAdapter(peliculaAdaptador);
    }


    public void cargarRecycler(Context context, RecyclerView recyclerView,List<Peliculas> peliculas, PeliculaAdaptador.AdapterPeliInterface escuchador){
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(llm);

        PeliculaAdaptador peliculaAdaptador = new PeliculaAdaptador(context,peliculas,escuchador);
        recyclerView.setAdapter(peliculaAdaptador);
    }

    public List<Integer> getListaFiltros(Bundle bundleFiltros) {
        if (bundleFiltros!=null){
            Integer tab = bundleFiltros.getInt(KEY_TAB);
            List<Integer> seleccionados = bundleFiltros.getIntegerArrayList(KEY_LISTA_FILTROS);

            if (tab == 0) {
                if (seleccionados.size() < 4) {
                    switch (seleccionados.size()){
                        case 1:
                            listaFiltros.add(28);
                            listaFiltros.add(18);
                            listaFiltros.add(35);
                            break;
                        case 2:
                            listaFiltros.add(28);
                            listaFiltros.add(18);
                            break;
                        case 3:
                            listaFiltros.add(28);
                            break;
                    }
                } else {
                    listaFiltros = seleccionados;
                }
            }else {
                listaFiltros.add(28);
                listaFiltros.add(18);
                listaFiltros.add(12);
                listaFiltros.add(35);
            }
        }else {
            listaFiltros.add(28);
            listaFiltros.add(18);
            listaFiltros.add(12);
            listaFiltros.add(35);
        }

        return listaFiltros;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void irTrailer(Peliculas peliculas) {
        Intent intent = new Intent(getActivity(), TrailerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(TrailerActivity.KEY_NOMBRE, peliculas.getTitle());
        bundle.putInt(String.valueOf(TrailerActivity.KEY_ID), peliculas.getId());
        bundle.putString(TrailerActivity.KEY_RESUMEN, peliculas.getOverview());
        intent.putExtras(bundle);
        startActivity(intent);
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction();
    }


}
