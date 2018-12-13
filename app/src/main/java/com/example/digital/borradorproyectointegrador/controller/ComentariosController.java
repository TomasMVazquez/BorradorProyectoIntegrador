package com.example.digital.borradorproyectointegrador.controller;

import android.content.Context;

import com.example.digital.borradorproyectointegrador.dao.internet.dao_comentario.DAOComentario;
import com.example.digital.borradorproyectointegrador.model.comentario.Comentario;
import com.example.digital.borradorproyectointegrador.util.ResultListener;

import java.util.List;

public class ComentariosController {

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

        DAOComentario daoComentarioTrailer = new DAOComentario();

        daoComentarioTrailer.dameComentariosPeli(pelicula, context, new ResultListener<List<Comentario>>() {
            @Override
            public void finish(List<Comentario> Resultado) {
                listResultListener.finish(Resultado);
            }
        });

    }

    public void entregarMisComentarios(String user, Context context, final ResultListener<List<Comentario>> listResultListener){
        DAOComentario daoComentario = new DAOComentario();
        daoComentario.dameMisComentarios(user, context, new ResultListener<List<Comentario>>() {
            @Override
            public void finish(List<Comentario> Resultado) {
                listResultListener.finish(Resultado);
            }
        });
    }

}
