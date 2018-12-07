package com.example.digital.borradorproyectointegrador.view.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.digital.borradorproyectointegrador.R;
import com.example.digital.borradorproyectointegrador.controller.ComentariosController;
import com.example.digital.borradorproyectointegrador.model.comentario.Comentario;
import com.example.digital.borradorproyectointegrador.util.ResultListener;
import com.example.digital.borradorproyectointegrador.view.Adaptadores.AdaptadorRecyclerComentarioTrailer;
import com.example.digital.borradorproyectointegrador.view.Adaptadores.AdaptadorRecyclerComentariosCompletos;
import com.example.digital.borradorproyectointegrador.view.PerfilUsuarioActivity;
import com.example.digital.borradorproyectointegrador.view.TrailerActivity;

import java.util.ArrayList;
import java.util.List;

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
        //adaptador
        final AdaptadorRecyclerComentariosCompletos adaptadorRecyclerComentariosCompletos = new AdaptadorRecyclerComentariosCompletos(view.getContext(), new AdaptadorRecyclerComentarioTrailer.ComentarioInterface() {
            @Override
            public void irPerfil(Comentario comentario) {
                String user = comentario.getUserId();
                Intent intent = new Intent(getActivity(),PerfilUsuarioActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(PerfilUsuarioActivity.KEY_USER,user);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }, new ArrayList<Comentario>());

        //datos
        ComentariosController comentariosController = new ComentariosController();

        //Recycler
        final RecyclerView recyclerViewComentario = view.findViewById(R.id.recyclerComentarios);
        recyclerViewComentario.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerViewComentario.setLayoutManager(llm);

        comentariosController.entregarListaComentarios(view.getContext(), new ResultListener<List<Comentario>>() {
            @Override
            public void finish(List<Comentario> Resultado) {
                adaptadorRecyclerComentariosCompletos.setComentarioList(Resultado);
            }
        });

        recyclerViewComentario.setAdapter(adaptadorRecyclerComentariosCompletos);
        return view;
    }

}
