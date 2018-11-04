package com.example.digital.borradorproyectointegrador.model;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.RatingBar;

public class PeliculaSerie {

    //Atributos
    private Integer imagen;

    private String nombre;
    private RatingBar ratingBar;
    private Integer cantEstrellas;
    private String videoId;
    private Integer resumen;


    //Constructor

    public PeliculaSerie(Integer imagen, String nombre, Integer cantEstrellas, String videoId, Integer resumen) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.cantEstrellas = cantEstrellas;
        this.videoId = videoId;
        this.resumen = resumen;
    }


    //Getter


    public Integer getResumen() {
        return resumen;
    }

    public Integer getImagen() {
        return imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public RatingBar getRatingBar() {
        return ratingBar;
    }

    public Integer getCantEstrellas() {
        return cantEstrellas;
    }

    public String getVideoId() {
        return videoId;
    }

    //ToString
    @Override
    public String toString() {
        return "PeliculaSerie{" +
                "imagen=" + imagen +
                ", ratingBar=" + ratingBar +
                ", cantEstrellas=" + cantEstrellas +
                '}';
    }



}
