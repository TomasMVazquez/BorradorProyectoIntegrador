package com.example.digital.borradorproyectointegrador.model.genero;

import java.util.List;

public class GeneroConteiner {

    //Atributos
    private List<Genero> genres;

    //constructor
    public GeneroConteiner(List<Genero> genres) {
        this.genres = genres;
    }

    //getter
    public List<Genero> getGenres() {
        return genres;
    }


    //tostring
    @Override
    public String toString() {
        return "GeneroConteiner{" +
                "genres=" + genres +
                '}';
    }
}
