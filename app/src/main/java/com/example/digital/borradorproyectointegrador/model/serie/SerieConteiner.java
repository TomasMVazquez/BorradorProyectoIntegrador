package com.example.digital.borradorproyectointegrador.model.serie;

import java.util.List;

public class SerieConteiner {


    //Atributos
    private List<Serie> results;
    private Integer total_pages;

    public SerieConteiner(List<Serie> results, Integer total_pages) {
        this.results = results;
        this.total_pages = total_pages;
    }

    public List<Serie> getResults() {
        return results;
    }

    public Integer getTotal_pages() {
        return total_pages;
    }

    @Override
    public String toString() {
        return "SerieConteiner{" +
                "results=" + results +
                '}';
    }
}
