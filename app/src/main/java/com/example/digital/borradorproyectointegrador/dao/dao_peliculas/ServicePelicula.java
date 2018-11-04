package com.example.digital.borradorproyectointegrador.dao.dao_peliculas;

import com.example.digital.borradorproyectointegrador.model.pelicula.PeliculaConteiner;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServicePelicula {

    @GET("movie?api_key=656020b1f06a98f4d73cadd7336e7790&language=es&sort_by=popularity.desc&include_adult=false&include_video=true")
    Call<PeliculaConteiner> getPeliculaContainer();
}
