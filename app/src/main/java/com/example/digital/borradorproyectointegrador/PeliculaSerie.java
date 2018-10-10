package com.example.digital.borradorproyectointegrador;

import android.widget.ImageView;
import android.widget.RatingBar;

public class PeliculaSerie {

    //Atributos
    private ImageView imagen;
    private RatingBar ratingBar;
    private Integer cantEstrellas;

    //Constructor
    public PeliculaSerie(ImageView imagen, Integer cantEstrellas) {
        this.imagen = imagen;
        this.cantEstrellas = cantEstrellas;
        //ratingBar.setNumStars(cantEstrellas);
    }

    //Getter
    public ImageView getImagen() {
        return imagen;
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
