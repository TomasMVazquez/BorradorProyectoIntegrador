package com.example.digital.borradorproyectointegrador.dao.dao_generos;

import com.example.digital.borradorproyectointegrador.dao.DaoHelper;
import com.example.digital.borradorproyectointegrador.model.genero.Genero;
import com.example.digital.borradorproyectointegrador.model.genero.GeneroConteiner;
import com.example.digital.borradorproyectointegrador.util.ResultListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DAOGenero extends DaoHelper {

    private ServiceGenres serviceGenres;

    public DAOGenero() {
        super("https://api.themoviedb.org/3/genre/");
        serviceGenres = retrofit.create(ServiceGenres.class);
    }

    public void buscarPeliculas(final ResultListener<List<Genero>> listResultListener){

        Call<GeneroConteiner> call = serviceGenres.getGeneroContainer();

        call.enqueue(new Callback<GeneroConteiner>() {
            @Override
            public void onResponse(Call<GeneroConteiner> call, Response<GeneroConteiner> response) {

                GeneroConteiner generoConteiner = response.body();

                List<Genero> generos = generoConteiner.getGenres();

                listResultListener.finish(generos);

            }

            @Override
            public void onFailure(Call<GeneroConteiner> call, Throwable t) {

            }
        });

    }

    public void buscarSeries(final ResultListener<List<Genero>> listResultListener){

        Call<GeneroConteiner> call = serviceGenres.getGeneroTVContainer();

        call.enqueue(new Callback<GeneroConteiner>() {
            @Override
            public void onResponse(Call<GeneroConteiner> call, Response<GeneroConteiner> response) {

                GeneroConteiner generoTVConteiner = response.body();

                List<Genero> generos = generoTVConteiner.getGenres();

                listResultListener.finish(generos);

            }

            @Override
            public void onFailure(Call<GeneroConteiner> call, Throwable t) {

            }
        });

    }
}
