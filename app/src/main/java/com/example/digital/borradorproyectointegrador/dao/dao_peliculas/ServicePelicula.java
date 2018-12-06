package com.example.digital.borradorproyectointegrador.dao.dao_peliculas;

import com.example.digital.borradorproyectointegrador.model.pelicula.PeliculaConteiner;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServicePelicula {

    @GET("movie?")
    Call<PeliculaConteiner> getPeliculaContainer(@Query("api_key")String api,
                                                 @Query("language")String language,
                                                 @Query("sort_by")String sort,
                                                 @Query("include_adult")Boolean adult,
                                                 @Query("include_video")Boolean video,
                                                 @Query("page")String page);

}
