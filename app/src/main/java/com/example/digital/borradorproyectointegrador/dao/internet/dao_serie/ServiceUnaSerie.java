package com.example.digital.borradorproyectointegrador.dao.internet.dao_serie;

import com.example.digital.borradorproyectointegrador.model.serie.Serie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServiceUnaSerie {

    @GET("tv/{tv_id}?")
    Call<Serie> getSerie(@Path("tv_id") Integer id,
                          @Query("api_key") String api_key,
                          @Query("language") String language);

}
