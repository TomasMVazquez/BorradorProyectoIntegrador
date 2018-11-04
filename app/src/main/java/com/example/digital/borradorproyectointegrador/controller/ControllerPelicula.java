package com.example.digital.borradorproyectointegrador.controller;

import android.content.Context;
import android.widget.Toast;

import com.example.digital.borradorproyectointegrador.dao.peliculas.DAOPelicula;
import com.example.digital.borradorproyectointegrador.model.pelicula.Peliculas;
import com.example.digital.borradorproyectointegrador.util.ResultListener;
import com.example.digital.borradorproyectointegrador.util.Util;


import java.util.ArrayList;
import java.util.List;

public class ControllerPelicula {

    public void entregarPeliculas(Context context, final ResultListener<List<Peliculas>> listResultListener){

        if (Util.hayInternet(context)){

            DAOPelicula daoPelicula = new DAOPelicula();

            daoPelicula.buscarPeliculas(new ResultListener<List<Peliculas>>() {
                @Override
                public void finish(List<Peliculas> Resultado) {
                    listResultListener.finish(Resultado);
                }
            });

        }else {
            Toast.makeText(context, "NO HAY INTERNET", Toast.LENGTH_SHORT).show();
        }

    }

    public void entregarPeliculasGeneros(Context context, final Integer genero, final ResultListener<List<Peliculas>> listResultListener){
        final List<Peliculas> peliculaGenero = new ArrayList<>();

        if (Util.hayInternet(context)){

            DAOPelicula daoPelicula = new DAOPelicula();

            daoPelicula.buscarPeliculas(new ResultListener<List<Peliculas>>() {
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

}
