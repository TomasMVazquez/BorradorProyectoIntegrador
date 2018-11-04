package com.example.digital.borradorproyectointegrador.model.pelicula;

import java.util.List;

public class PeliculaConteiner {

    private List<Peliculas> results;
    private Integer total_pages;

    public PeliculaConteiner(List<Peliculas> results, Integer total_pages) {
        this.results = results;
        this.total_pages=total_pages;
    }

    public Integer getTotal_pages() {
        return total_pages;
    }

    public List<Peliculas> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "PeliculaConteiner{" +
                "results=" + results +
                '}';
    }
}
