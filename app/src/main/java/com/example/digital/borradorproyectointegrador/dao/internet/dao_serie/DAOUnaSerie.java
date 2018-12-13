package com.example.digital.borradorproyectointegrador.dao.internet.dao_serie;

import android.content.Context;

import com.example.digital.borradorproyectointegrador.R;
import com.example.digital.borradorproyectointegrador.dao.internet.DaoHelper;
import com.example.digital.borradorproyectointegrador.model.serie.Serie;
import com.example.digital.borradorproyectointegrador.util.ResultListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DAOUnaSerie extends DaoHelper {

    private ServiceUnaSerie serviceUnaSerie;
    private Call<Serie> call;

    public DAOUnaSerie() {
        super("https://api.themoviedb.org/3/");
        serviceUnaSerie = retrofit.create(ServiceUnaSerie.class);
    }

    public void dameUnaSerie(Integer id, Context context, final ResultListener<Serie> serieResultListener){
        String api = context.getResources().getString(R.string.api_key);
        String language = context.getResources().getString(R.string.language);

        call = serviceUnaSerie.getSerie(id,api,language);

        call.enqueue(new Callback<Serie>() {
            @Override
            public void onResponse(Call<Serie> call, Response<Serie> response) {
                Serie serie = response.body();
                serieResultListener.finish(serie);
            }

            @Override
            public void onFailure(Call<Serie> call, Throwable t) {

            }
        });

    }


}
