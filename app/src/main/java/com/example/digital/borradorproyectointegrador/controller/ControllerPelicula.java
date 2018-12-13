package com.example.digital.borradorproyectointegrador.controller;

import android.content.Context;
import android.widget.Toast;

import com.example.digital.borradorproyectointegrador.dao.database.DaoPeliculaDB;
import com.example.digital.borradorproyectointegrador.dao.database.DatabaseHelper;
import com.example.digital.borradorproyectointegrador.dao.database.MyDatabase;
import com.example.digital.borradorproyectointegrador.dao.internet.dao_peliculas.DAOPelicula;
import com.example.digital.borradorproyectointegrador.dao.internet.dao_peliculas.DAOUnaPelicula;
import com.example.digital.borradorproyectointegrador.model.pelicula.Peliculas;
import com.example.digital.borradorproyectointegrador.util.ResultListener;
import com.example.digital.borradorproyectointegrador.util.Util;


import java.util.ArrayList;
import java.util.List;

public class ControllerPelicula {
    private DaoPeliculaDB daoPeliculaDB;

    public ControllerPelicula(Context context) {
        MyDatabase database = DatabaseHelper.getInstance(context.getApplicationContext());

        daoPeliculaDB = database.getDaoPeliculaDB();
    }

    public void entregarPeliculas(Context context, final ResultListener<List<Peliculas>> listResultListener){

        if (Util.hayInternet(context)){

            DAOPelicula daoPelicula = new DAOPelicula();
            daoPelicula.buscarPeliculas(context,new ResultListener<List<Peliculas>>() {
                @Override
                public void finish(List<Peliculas> Resultado) {
                    listResultListener.finish(Resultado);
                }
            });

        }else {
            List<Peliculas> peliculas = daoPeliculaDB.buscarPeliculas();
            listResultListener.finish(peliculas);
            Toast.makeText(context, "NO HAY INTERNET", Toast.LENGTH_SHORT).show();
        }

    }

    public void entregarPeliculasGeneros(Context context, final Integer genero, final ResultListener<List<Peliculas>> listResultListener){
        final List<Peliculas> peliculaGenero = new ArrayList<>();

        if (Util.hayInternet(context)){

            DAOPelicula daoPelicula = new DAOPelicula();
            daoPelicula.buscarPeliculas(context,new ResultListener<List<Peliculas>>() {
                @Override
                public void finish(List<Peliculas> Resultado) {
                    for (Peliculas peli:Resultado) {
                        if (peli.getGenre_ids().contains(genero)){
                            peliculaGenero.add(peli);
                        }
                    }
                    listResultListener.finish(peliculaGenero);
                }
            });

        }else {
            Toast.makeText(context, "NO HAY INTERNET", Toast.LENGTH_SHORT).show();
        }
    }

    public void entregarUnaPelicula(Integer id, Context context, final ResultListener<Peliculas> listResultListener){

        if (Util.hayInternet(context)){
            DAOUnaPelicula daoUnaPelicula = new DAOUnaPelicula();
            daoUnaPelicula.buscarPeliculas(id, context, new ResultListener<Peliculas>() {
                @Override
                public void finish(Peliculas Resultado) {
                    listResultListener.finish(Resultado);
                }
            });
        }else {
            Toast.makeText(context, "NO HAY INTERNET", Toast.LENGTH_SHORT).show();
        }


    }

}
