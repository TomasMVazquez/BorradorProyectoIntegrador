package com.example.digital.borradorproyectointegrador;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.RatingBar;

public class PeliculaSerie {

    //Atributos
    private Integer imagen;
    private String nombre;
    private RatingBar ratingBar;
    private Integer cantEstrellas;
    private Integer resumen;

    //Constructor

    public PeliculaSerie(Integer imagen, String nombre, Integer cantEstrellas, Integer resumen) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.cantEstrellas = cantEstrellas;
        this.resumen = resumen;
    }


//    public PeliculaSerie(ImageView imagen, Integer cantEstrellas) {
//        this.imagen = imagen;
//        this.cantEstrellas = cantEstrellas;
//        //ratingBar.setNumStars(cantEstrellas);
//    }



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
