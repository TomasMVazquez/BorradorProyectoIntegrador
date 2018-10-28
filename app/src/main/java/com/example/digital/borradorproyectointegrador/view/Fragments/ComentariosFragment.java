package com.example.digital.borradorproyectointegrador.view.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.digital.borradorproyectointegrador.R;
import com.example.digital.borradorproyectointegrador.controller.ComentariosController;
import com.example.digital.borradorproyectointegrador.view.Adaptadores.AdaptadorRecyclerComentariosCompletos;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComentariosFragment extends Fragment {


    public ComentariosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comentarios, container, false);

        //datos
        ComentariosController comentariosController = new ComentariosController();

        //Recycler
        RecyclerView recyclerViewComentario = view.findViewById(R.id.recyclerComentarios);
        recyclerViewComentario.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerViewComentario.setLayoutManager(llm);

        AdaptadorRecyclerComentariosCompletos adaptadorRecyclerComentariosCompletos = new AdaptadorRecyclerComentariosCompletos(comentariosController.entregarListaComentarios());
        recyclerViewComentario.setAdapter(adaptadorRecyclerComentariosCompletos);

        return view;
    }

}
