package com.example.digital.borradorproyectointegrador.controller;

import android.content.Context;

import com.example.digital.borradorproyectointegrador.dao.dao_comentario.DAOComentario;
import com.example.digital.borradorproyectointegrador.model.comentario.Comentario;
import com.example.digital.borradorproyectointegrador.util.ResultListener;

import java.util.ArrayList;
import java.util.List;

public class ComentariosController {

    private List<Comentario> comentarioList = new ArrayList<>();

    public void entregarListaComentarios(Context context, final ResultListener<List<Comentario>> listResultListener){

        DAOComentario daoComentarios = new DAOComentario();

        daoComentarios.dameComentarios(context, new ResultListener<List<Comentario>>() {
            @Override
            public void finish(List<Comentario> Resultado) {
                listResultListener.finish(Resultado);
            }
        });
    }

    public void entregarListaComentariosTrailer(String pelicula, Context context, final ResultListener<List<Comentario>> listResultListener){

        comentarioList.clear();

        DAOComentario daoComentarioTrailer = new DAOComentario();

        daoComentarioTrailer.dameComentariosPeli(pelicula, context, new ResultListener<List<Comentario>>() {
            @Override
            public void finish(List<Comentario> Resultado) {
                listResultListener.finish(Resultado);
            }
        });

    }

}
