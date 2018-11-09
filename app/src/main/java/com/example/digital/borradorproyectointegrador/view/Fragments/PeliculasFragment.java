package com.example.digital.borradorproyectointegrador.view.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.digital.borradorproyectointegrador.controller.ControllerGeneros;
import com.example.digital.borradorproyectointegrador.controller.ControllerPelicula;
import com.example.digital.borradorproyectointegrador.model.genero.Genero;
import com.example.digital.borradorproyectointegrador.model.pelicula.Peliculas;
import com.example.digital.borradorproyectointegrador.util.ResultListener;
import com.example.digital.borradorproyectointegrador.R;
import com.example.digital.borradorproyectointegrador.view.Adaptadores.PeliculaAdaptador;
import com.example.digital.borradorproyectointegrador.view.TrailerActivity;

import java.util.List;


public class PeliculasFragment extends Fragment implements PeliculaAdaptador.AdapterPeliInterface {

    private OnFragmentInteractionListener mListener;

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
        final RecyclerView recyclerViewPrimero = view.findViewById(R.id.recylcerViewPrimero);
        final RecyclerView recyclerViewSegundo = view.findViewById(R.id.recylcerViewSegundo);
        final RecyclerView recyclerViewTercero = view.findViewById(R.id.recylcerViewTercero);
        final RecyclerView recyclerViewCuarto = view.findViewById(R.id.recylcerViewCuarto);
        final RecyclerView recyclerViewQuinto = view.findViewById(R.id.recylcerViewQuinto);

        // Lista de Generos de peliculas: https://api.themoviedb.org/3/genre/movie/list?api_key=656020b1f06a98f4d73cadd7336e7790&language=en-US

        ControllerPelicula controllerPelicula = new ControllerPelicula();
        controllerPelicula.entregarPeliculas(view.getContext(), new ResultListener<List<Peliculas>>() {
            @Override
            public void finish(List<Peliculas> Resultado) {
                TextView tvPrimero=view.findViewById(R.id.tvCategoriaPrimero);
                tvPrimero.setText("Favoritos");
                cargarRecycler(view.getContext(),recyclerViewPrimero,Resultado, PeliculasFragment.this);
            }
        });

        controllerPelicula.entregarPeliculasGeneros(view.getContext(), 28, new ResultListener<List<Peliculas>>() {
            @Override
            public void finish(List<Peliculas> Resultado) {
                TextView tvSegundo=view.findViewById(R.id.tvCategoriaSegundo);
                tvSegundo.setText("Accion");

                cargarRecycler(view.getContext(),recyclerViewSegundo,Resultado,PeliculasFragment.this);
            }
        });
        controllerPelicula.entregarPeliculasGeneros(view.getContext(), 18, new ResultListener<List<Peliculas>>() {
            @Override
            public void finish(List<Peliculas> Resultado) {
                TextView tvTercero =view.findViewById(R.id.tvCategoriaTercero);
                tvTercero.setText("Drama");

                cargarRecycler(view.getContext(),recyclerViewTercero,Resultado,PeliculasFragment.this);
            }
        });
        controllerPelicula.entregarPeliculasGeneros(view.getContext(), 878, new ResultListener<List<Peliculas>>() {
            @Override
            public void finish(List<Peliculas> Resultado) {

                TextView tvCuarto =view.findViewById(R.id.tvCategoriaCuarto);
                tvCuarto.setText("Sci-Fi");

                cargarRecycler(view.getContext(),recyclerViewCuarto,Resultado,PeliculasFragment.this);
            }
        });
        controllerPelicula.entregarPeliculasGeneros(view.getContext(), 35, new ResultListener<List<Peliculas>>() {
            @Override
            public void finish(List<Peliculas> Resultado) {
                TextView tvQuinto =view.findViewById(R.id.tvCategoriaQuinto);
                tvQuinto.setText("Comedia");

                cargarRecycler(view.getContext(),recyclerViewQuinto,Resultado,PeliculasFragment.this);
            }
        });


        final FloatingActionButton fabPeli = view.findViewById(R.id.fabFiltroPeli);
        ControllerGeneros controllerGeneros = new ControllerGeneros();
        controllerGeneros.entregarGeneros(view.getContext(), new ResultListener<List<Genero>>() {
            @Override
            public void finish(final List<Genero> Resultado) {
                fabPeli.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(view.getContext(), Resultado.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
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
