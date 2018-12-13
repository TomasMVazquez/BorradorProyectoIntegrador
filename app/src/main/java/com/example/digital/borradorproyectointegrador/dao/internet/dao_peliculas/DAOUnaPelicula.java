package com.example.digital.borradorproyectointegrador.dao.internet.dao_peliculas;

import android.content.Context;

import com.example.digital.borradorproyectointegrador.R;
import com.example.digital.borradorproyectointegrador.dao.internet.DaoHelper;
import com.example.digital.borradorproyectointegrador.model.pelicula.Peliculas;
import com.example.digital.borradorproyectointegrador.util.ResultListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DAOUnaPelicula extends DaoHelper {

    private ServiceUnaPelicula serviceUnaPelicula;
    private Call<Peliculas> call;

    public DAOUnaPelicula() {
        super("https://api.themoviedb.org/3/movie/");
        serviceUnaPelicula = retrofit.create(ServiceUnaPelicula.class);
    }

    public void buscarPeliculas(Integer id,Context context, final ResultListener<Peliculas> listResultListener){
        String api = context.getResources().getString(R.string.api_key);
        String language = context.getResources().getString(R.string.language);

        call = serviceUnaPelicula.getPeliculas(id,api,language);

        call.enqueue(new Callback<Peliculas>() {
            @Override
            public void onResponse(Call<Peliculas> call, Response<Peliculas> response) {
                Peliculas unaPeli = response.body();
                listResultListener.finish(unaPeli);
            }

            @Override
            public void onFailure(Call<Peliculas> call, Throwable t) {

            }
        });

    }

}
