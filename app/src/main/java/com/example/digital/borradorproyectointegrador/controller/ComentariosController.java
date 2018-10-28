package com.example.digital.borradorproyectointegrador.controller;

import com.example.digital.borradorproyectointegrador.dao.DAOComentariosCompleto;
import com.example.digital.borradorproyectointegrador.model.ComentarioCompleto;

import java.util.ArrayList;
import java.util.List;

public class ComentariosController {

    List<ComentarioCompleto> comentarioCompletoList = new ArrayList<>();

    public List<ComentarioCompleto> entregarListaComentarios(){

        DAOComentariosCompleto daoComentariosCompleto = new DAOComentariosCompleto();

        comentarioCompletoList = daoComentariosCompleto.dameComentarios();

        return comentarioCompletoList;
    }

}
