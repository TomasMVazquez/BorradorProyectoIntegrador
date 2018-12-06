package com.example.digital.borradorproyectointegrador.dao.dao_peliculas;

import com.example.digital.borradorproyectointegrador.model.pelicula.Peliculas;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServiceUnaPelicula {

    @GET("{movie_id}?")
    Call<Peliculas> getPeliculas(@Path("movie_id") Integer id,
                                @Query("api_key")String api,
                                @Query("language")String language);

}
