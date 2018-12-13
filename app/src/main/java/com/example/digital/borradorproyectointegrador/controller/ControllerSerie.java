package com.example.digital.borradorproyectointegrador.controller;

import android.content.Context;
import android.widget.Toast;

import com.example.digital.borradorproyectointegrador.dao.database.DaoPeliculaDB;
import com.example.digital.borradorproyectointegrador.dao.database.DaoSerieDB;
import com.example.digital.borradorproyectointegrador.dao.database.DatabaseHelper;
import com.example.digital.borradorproyectointegrador.dao.database.MyDatabase;
import com.example.digital.borradorproyectointegrador.dao.internet.dao_serie.DAOSerie;
import com.example.digital.borradorproyectointegrador.dao.internet.dao_serie.DAOUnaSerie;
import com.example.digital.borradorproyectointegrador.model.pelicula.Peliculas;
import com.example.digital.borradorproyectointegrador.model.serie.Serie;
import com.example.digital.borradorproyectointegrador.util.ResultListener;
import com.example.digital.borradorproyectointegrador.util.Util;

import java.util.ArrayList;
import java.util.List;

public class ControllerSerie {

    private DaoSerieDB daoSerieDB;

    public ControllerSerie(Context context) {
        MyDatabase database = DatabaseHelper.getInstance(context.getApplicationContext());

        daoSerieDB = database.getDaoSerieDB();
    }

    public void entregarSerie(Context context, final ResultListener<List<Serie>> listResultListener){

        if (Util.hayInternet(context)){

            DAOSerie daoSerie = new DAOSerie();
            daoSerie.buscarSeries(context,new ResultListener<List<Serie>>() {
                @Override
                public void finish(List<Serie> Resultado) {
                    listResultListener.finish(Resultado);
                }
            });

        }else {
            List<Serie> series = daoSerieDB.buscarSeries();
            listResultListener.finish(series);
            Toast.makeText(context, "NO HAY INTERNET", Toast.LENGTH_SHORT).show();
        }

    }

    public void entregarSerieGeneros(Context context, final Integer genero, final ResultListener<List<Serie>> listResultListener){
        final List<Serie> serieGenero = new ArrayList<>();

        if (Util.hayInternet(context)){
            DAOSerie daoSerie = new DAOSerie();
            daoSerie.buscarSeries(context,new ResultListener<List<Serie>>() {
                @Override
                public void finish(List<Serie> Resultado) {
                    for (Serie serie:Resultado) {
                        if (serie.getGenre_ids().contains(genero)){
                            serieGenero.add(serie);
                        }
                    }
                    listResultListener.finish(serieGenero);
                }
            });

        }else {
            Toast.makeText(context, "NO HAY INTERNET", Toast.LENGTH_SHORT).show();
        }

    }

    public void entregarUnaSerie(Integer id, Context context, final ResultListener<Serie> resultListener){
        if (Util.hayInternet(context)){

            DAOUnaSerie daoUnaSerie = new DAOUnaSerie();
            daoUnaSerie.dameUnaSerie(id, context, new ResultListener<Serie>() {
                @Override
                public void finish(Serie Resultado) {
                    resultListener.finish(Resultado);
                }
            });

        }

    }

}
