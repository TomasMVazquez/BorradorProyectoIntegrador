package com.example.digital.borradorproyectointegrador.view.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

    public PeliculasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_peliculas, container, false);

        ControllerGeneros controllerGeneros = new ControllerGeneros();
        controllerGeneros.entregarGeneros(view.getContext(), new ResultListener<List<Genero>>() {
            @Override
            public void finish(List<Genero> Resultado) {
                generoList=Resultado;
            }
        });

        //Recycler view
        //Lista
        final RecyclerView recyclerViewPrimero = view.findViewById(R.id.recylcerViewPrimero);
        final RecyclerView recyclerViewSegundo = view.findViewById(R.id.recylcerViewSegundo);
        final RecyclerView recyclerViewTercero = view.findViewById(R.id.recylcerViewTercero);
        final RecyclerView recyclerViewCuarto = view.findViewById(R.id.recylcerViewCuarto);
        final RecyclerView recyclerViewQuinto = view.findViewById(R.id.recylcerViewQuinto);


        ControllerPelicula controllerPelicula = new ControllerPelicula();
        controllerPelicula.entregarPeliculas(view.getContext(), new ResultListener<List<Peliculas>>() {
            @Override
            public void finish(List<Peliculas> Resultado) {
                TextView tvPrimero=view.findViewById(R.id.tvCategoriaPrimero);
                tvPrimero.setText("Favoritos");
                cargarRecycler(view.getContext(),recyclerViewPrimero,Resultado, PeliculasFragment.this);
            }
        });

        controllerPelicula.entregarPeliculasGeneros(view.getContext(), getListaFiltros().get(0), new ResultListener<List<Peliculas>>() {
            @Override
            public void finish(List<Peliculas> Resultado) {
                TextView tvSegundo=view.findViewById(R.id.tvCategoriaSegundo);
                for (Genero genero: generoList){
                    if (genero.getId()==getListaFiltros().get(0)){
                        tvSegundo.setText(genero.getName());
                    }
                }

                cargarRecycler(view.getContext(),recyclerViewSegundo,Resultado,PeliculasFragment.this);
            }
        });
        controllerPelicula.entregarPeliculasGeneros(view.getContext(), getListaFiltros().get(1), new ResultListener<List<Peliculas>>() {
            @Override
            public void finish(List<Peliculas> Resultado) {
                TextView tvTercero =view.findViewById(R.id.tvCategoriaTercero);
                for (Genero genero: generoList){
                    if (genero.getId()==getListaFiltros().get(1)){
                        tvTercero.setText(genero.getName());
                    }
                }

                cargarRecycler(view.getContext(),recyclerViewTercero,Resultado,PeliculasFragment.this);
            }
        });
        controllerPelicula.entregarPeliculasGeneros(view.getContext(), getListaFiltros().get(2), new ResultListener<List<Peliculas>>() {
            @Override
            public void finish(List<Peliculas> Resultado) {
                TextView tvCuarto =view.findViewById(R.id.tvCategoriaCuarto);
                for (Genero genero: generoList){
                    if (genero.getId()==getListaFiltros().get(2)){
                        tvCuarto.setText(genero.getName());
                    }
                }

                cargarRecycler(view.getContext(),recyclerViewCuarto,Resultado,PeliculasFragment.this);
            }
        });
        controllerPelicula.entregarPeliculasGeneros(view.getContext(), getListaFiltros().get(3), new ResultListener<List<Peliculas>>() {
            @Override
            public void finish(List<Peliculas> Resultado) {
                TextView tvQuinto =view.findViewById(R.id.tvCategoriaQuinto);
                for (Genero genero: generoList){
                    if (genero.getId()== getListaFiltros().get(3)){
                        tvQuinto.setText(genero.getName());
                    }
                }

                cargarRecycler(view.getContext(),recyclerViewQuinto,Resultado,PeliculasFragment.this);
            }
        });

        return view;
    }

    public void cargarRecycler(Context context, RecyclerView recyclerView,List<Peliculas> peliculas, PeliculaAdaptador.AdapterPeliInterface escuchador){
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(llm);

        PeliculaAdaptador peliculaAdaptador = new PeliculaAdaptador(context,peliculas,escuchador);
        recyclerView.setAdapter(peliculaAdaptador);
    }

    public List<Integer> getListaFiltros() {
        Bundle bundleFiltros = getArguments();

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
