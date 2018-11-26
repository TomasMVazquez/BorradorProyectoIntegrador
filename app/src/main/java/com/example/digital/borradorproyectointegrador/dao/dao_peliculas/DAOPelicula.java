package com.example.digital.borradorproyectointegrador.dao.dao_peliculas;

import android.util.Log;

import com.example.digital.borradorproyectointegrador.dao.DaoHelper;
import com.example.digital.borradorproyectointegrador.model.pelicula.PeliculaConteiner;
import com.example.digital.borradorproyectointegrador.model.pelicula.Peliculas;
import com.example.digital.borradorproyectointegrador.util.ResultListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DAOPelicula extends DaoHelper {

    private ServicePelicula servicePelicula;
    private Call<PeliculaConteiner> call;
    private List<Peliculas> peliculasTodas = new ArrayList<>();

    public DAOPelicula() {
        super("https://api.themoviedb.org/3/discover/");
        servicePelicula = retrofit.create(ServicePelicula.class);
    }

    public void buscarPeliculas(final ResultListener<List<Peliculas>> listResultListener){

        for (int i = 1; i < 5; i++) {
          call = servicePelicula.getPeliculaContainer(String.valueOf(i));
          call.enqueue(new Callback<PeliculaConteiner>() {
              @Override
              public void onResponse(Call<PeliculaConteiner> call, Response<PeliculaConteiner> response) {
                  PeliculaConteiner peliculaConteiner = response.body();
                  List<Peliculas> peliculas = peliculaConteiner.getResults();
                  peliculasTodas.addAll(peliculas);
                  listResultListener.finish(peliculasTodas);
              }

              @Override
              public void onFailure(Call<PeliculaConteiner> call, Throwable t) {

              }
          });
        }

    }

}
