package com.example.digital.borradorproyectointegrador.dao.dao_serie;

import android.content.Context;
import android.util.Log;

import com.example.digital.borradorproyectointegrador.R;
import com.example.digital.borradorproyectointegrador.dao.DaoHelper;
import com.example.digital.borradorproyectointegrador.model.serie.Serie;
import com.example.digital.borradorproyectointegrador.model.serie.SerieConteiner;
import com.example.digital.borradorproyectointegrador.util.ResultListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DAOSerie extends DaoHelper {

    private ServiceSerie serviceSerie;
    private List<Serie> serieListTodas = new ArrayList<>();

    public DAOSerie() {
        super("https://api.themoviedb.org/3/discover/");
        serviceSerie = retrofit.create(ServiceSerie.class);
    }

    public void buscarSeries(Context context, final ResultListener<List<Serie>> listResultListener){
        String api = context.getResources().getString(R.string.api_key);
        String language = context.getResources().getString(R.string.language);
        String sort = context.getResources().getString(R.string.sort_by);
        String timezone = context.getResources().getString(R.string.timezone);

        for (int i = 1; i < 5; i++) {
            Call<SerieConteiner> call = serviceSerie.getSerieConteiner(api,language,sort,String.valueOf(i),timezone);

            call.enqueue(new Callback<SerieConteiner>() {
                @Override
                public void onResponse(Call<SerieConteiner> call, Response<SerieConteiner> response) {
                    SerieConteiner serieConteiner = response.body();
                    List<Serie> serieList = serieConteiner.getResults();
                    serieListTodas.addAll(serieList);
                    listResultListener.finish(serieListTodas);
                }

                @Override
                public void onFailure(Call<SerieConteiner> call, Throwable t) {
                    Log.e("MIERRROR----------", t.toString());
                }
            });
        }


    }

}
