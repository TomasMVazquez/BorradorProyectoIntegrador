package com.example.digital.borradorproyectointegrador.dao.dao_generos;

import com.example.digital.borradorproyectointegrador.model.genero.GeneroConteiner;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceGenres {
    @GET("movie/list?api_key=656020b1f06a98f4d73cadd7336e7790&language=es")
    Call<GeneroConteiner> getGeneroContainer();

    @GET("tv/list?api_key=656020b1f06a98f4d73cadd7336e7790&language=es")
    Call<GeneroConteiner> getGeneroTVContainer();

}

