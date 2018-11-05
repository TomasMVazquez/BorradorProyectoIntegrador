package com.example.digital.borradorproyectointegrador.dao;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by digitalhouse on 15/01/18.
 */


public class DaoHelper {
    protected Retrofit retrofit;

    public static final String API_KEY = "656020b1f06a98f4d73cadd7336e7790";
    public static final String LANGUAGE = "en-US";

    public DaoHelper(String base_url) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(base_url)
                        .addConverterFactory(
                                GsonConverterFactory.create()
                        );

        retrofit =
                builder
                        .client(
                                httpClient.build()
                        )
                        .build();
    }
}

