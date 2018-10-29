package com.example.digital.borradorproyectointegrador.controller;

import com.example.digital.borradorproyectointegrador.dao.DAOComentario;
import com.example.digital.borradorproyectointegrador.model.Comentario;

import java.util.ArrayList;
import java.util.List;

public class ComentariosController {

    List<Comentario> comentarioList = new ArrayList<>();
    List<Comentario> comentarioTrailerList = new ArrayList<>();

    public List<Comentario> entregarListaComentarios(){

        DAOComentario daoComentarios = new DAOComentario();

        comentarioList = daoComentarios.dameComentarios();

        return comentarioList;
    }

    public List<Comentario> entregarListaComentariosTrailer(String pelicula){

        comentarioTrailerList.clear();

        DAOComentario daoComentarioTrailer = new DAOComentario();

        for (Comentario comentarioTrailer : daoComentarioTrailer.dameComentarios()) {
            if (comentarioTrailer.getPeliculaComentada().equals(pelicula)){
                comentarioTrailerList.add(comentarioTrailer);
            }
        }

        return comentarioTrailerList;
    }

}
