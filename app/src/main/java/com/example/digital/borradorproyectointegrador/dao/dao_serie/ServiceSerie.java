package com.example.digital.borradorproyectointegrador.dao.dao_serie;

import com.example.digital.borradorproyectointegrador.model.pelicula.PeliculaConteiner;
import com.example.digital.borradorproyectointegrador.model.serie.SerieConteiner;
import com.example.digital.borradorproyectointegrador.model.videos.VideoContainer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServiceSerie {

    @GET("tv?")
    Call<SerieConteiner> getSerieConteiner(@Query("api_key")String api,@Query("language")String language,@Query("sort_by")String sort,@Query("page")String page,@Query("timezone")String timezone);

}
