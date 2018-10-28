package com.example.digital.borradorproyectointegrador.controller;

import com.example.digital.borradorproyectointegrador.dao.DAOComentarioTrailer;
import com.example.digital.borradorproyectointegrador.dao.DAOComentariosCompleto;
import com.example.digital.borradorproyectointegrador.model.ComentarioCompleto;
import com.example.digital.borradorproyectointegrador.model.ComentarioTrailer;
import com.example.digital.borradorproyectointegrador.model.PeliculaSerie;

import java.util.ArrayList;
import java.util.List;

public class ComentariosController {

    List<ComentarioCompleto> comentarioCompletoList = new ArrayList<>();
    List<ComentarioTrailer> comentarioTrailerList = new ArrayList<>();

    public List<ComentarioCompleto> entregarListaComentarios(){

        DAOComentariosCompleto daoComentariosCompleto = new DAOComentariosCompleto();

        comentarioCompletoList = daoComentariosCompleto.dameComentarios();

        return comentarioCompletoList;
    }

    public List<ComentarioTrailer> entregarListaComentariosTrailer(String pelicula){

        comentarioTrailerList.clear();

        DAOComentarioTrailer daoComentarioTrailer = new DAOComentarioTrailer();

        for (ComentarioTrailer comentarioTrailer : daoComentarioTrailer.dameComentariosTrailer()) {
            if (comentarioTrailer.getPeliculaComentada().equals(pelicula)){
                comentarioTrailerList.add(comentarioTrailer);
            }
        }

        return comentarioTrailerList;
    }

}
