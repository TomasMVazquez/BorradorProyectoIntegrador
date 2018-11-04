package com.example.digital.borradorproyectointegrador.model.serie;

import java.util.List;

public class Serie {

    //Atributos
    private String name;
    private Integer id;
    private String poster_path;
    private List<Integer> genre_ids;
    private String overview;
    private String first_air_date;
    private Double vote_average;

    //Constructor
    public Serie(String name, Integer id, String poster_path, List<Integer> genre_ids, String overview, String first_air_date, Double vote_average) {
        this.name = name;
        this.id = id;
        this.poster_path = poster_path;
        this.genre_ids = genre_ids;
        this.overview = overview;
        this.first_air_date = first_air_date;
        this.vote_average = vote_average;
    }

    //Getter
    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public List<Integer> getGenre_ids() {
        return genre_ids;
    }

    public String getOverview() {
        return overview;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public Double getVote_average() {
        return vote_average;
    }

    //toString
    @Override
    public String toString() {
        return "Serie{" +
                "title='" + name + '\'' +
                '}';
    }
}
