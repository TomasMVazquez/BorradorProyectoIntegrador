package com.example.digital.borradorproyectointegrador.controller;

import android.content.Context;

import com.example.digital.borradorproyectointegrador.dao.internet.dao_generos.DAOGenero;
import com.example.digital.borradorproyectointegrador.model.genero.Genero;
import com.example.digital.borradorproyectointegrador.util.ResultListener;
import com.example.digital.borradorproyectointegrador.util.Util;


import java.util.List;

public class ControllerGeneros {

    public void entregarGeneros(Context context, final ResultListener<List<Genero>> listResultListener){

        if (Util.hayInternet(context)) {

            DAOGenero daoGenero = new DAOGenero();

            daoGenero.buscarPeliculas(new ResultListener<List<Genero>>() {
                @Override
                public void finish(List<Genero> Resultado) {
                    listResultListener.finish(Resultado);
                }
            });
        }

    }

    public void entregarGenerosTV(Context context, final ResultListener<List<Genero>> listResultListener){

        if (Util.hayInternet(context)) {

            DAOGenero daoGenero = new DAOGenero();

            daoGenero.buscarSeries(new ResultListener<List<Genero>>() {
                @Override
                public void finish(List<Genero> Resultado) {
                    listResultListener.finish(Resultado);
                }
            });
        }

    }

}
