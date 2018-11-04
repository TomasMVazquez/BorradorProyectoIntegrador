package com.example.digital.borradorproyectointegrador.dao.peliculas;

import android.util.Log;

import com.example.digital.borradorproyectointegrador.dao.DaoHelper;
import com.example.digital.borradorproyectointegrador.model.pelicula.PeliculaConteiner;
import com.example.digital.borradorproyectointegrador.model.pelicula.Peliculas;
import com.example.digital.borradorproyectointegrador.util.ResultListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DAOPelicula extends DaoHelper {

    private ServicePelicula servicePelicula;

    public DAOPelicula() {
        super("https://api.themoviedb.org/3/discover/");
        servicePelicula = retrofit.create(ServicePelicula.class);
    }

    public void buscarPeliculas(final ResultListener<List<Peliculas>> listResultListener){

        Call<PeliculaConteiner> call = servicePelicula.getPeliculaContainer();

        call.enqueue(new Callback<PeliculaConteiner>() {
            @Override
            public void onResponse(Call<PeliculaConteiner> call, Response<PeliculaConteiner> response) {
                PeliculaConteiner peliculaConteiner = response.body();

                List<Peliculas> peliculas = peliculaConteiner.getResults();

                listResultListener.finish(peliculas);

            }

            @Override
            public void onFailure(Call<PeliculaConteiner> call, Throwable t) {
                Log.e("MIERRROR----------", t.toString());
            }
        });

    }

}
