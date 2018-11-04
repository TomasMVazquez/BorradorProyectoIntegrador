package com.example.digital.borradorproyectointegrador.dao.dao_serie;

import com.example.digital.borradorproyectointegrador.model.pelicula.PeliculaConteiner;
import com.example.digital.borradorproyectointegrador.model.serie.SerieConteiner;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceSerie {

    @GET("tv?api_key=656020b1f06a98f4d73cadd7336e7790&language=es&sort_by=popularity.desc&page=1&timezone=America%2FBuenos_Aires&include_null_first_air_dates=false")
    Call<SerieConteiner> getSerieConteiner();

}
