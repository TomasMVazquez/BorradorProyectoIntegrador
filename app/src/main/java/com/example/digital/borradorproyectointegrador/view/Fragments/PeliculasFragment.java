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
import com.example.digital.borradorproyectointegrador.controller.ControllerUsuarioPerfil;
import com.example.digital.borradorproyectointegrador.model.genero.Genero;
import com.example.digital.borradorproyectointegrador.model.pelicula.Peliculas;
import com.example.digital.borradorproyectointegrador.util.ResultListener;
import com.example.digital.borradorproyectointegrador.R;
import com.example.digital.borradorproyectointegrador.view.Adaptadores.PeliculaAdaptador;
import com.example.digital.borradorproyectointegrador.view.MainActivity;
import com.example.digital.borradorproyectointegrador.view.TrailerActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;


public class PeliculasFragment extends Fragment implements PeliculaAdaptador.AdapterPeliInterface {

    public static final String KEY_LISTA_FILTROS = "filtros";
    public static final String KEY_TAB = "tab";

    private OnFragmentInteractionListener mListener;
    private List<Integer> listaFiltros = new ArrayList<>();
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    public PeliculasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_peliculas, container, false);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        //Recycler view
        //Lista
        final RecyclerView recyclerViewPeli = view.findViewById(R.id.recylcerViewPeliculas);

        final ControllerPelicula controllerPelicula = new ControllerPelicula();
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
        recyclerView.setLayoutManager(glm);

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
        bundle.putInt(TrailerActivity.KEY_TIPO,1);
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
