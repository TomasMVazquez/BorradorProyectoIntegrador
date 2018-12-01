package com.example.digital.borradorproyectointegrador.model.pelicula;

import java.util.List;

public class Peliculas {

    private String title;
    private Integer id;
    private String poster_path;
    private List<Integer> genre_ids;
    private String overview;
    private String release_date;
    private Double vote_average;

    public Peliculas(String title, Integer id, String poster_path, String overview, String release_date, List<Integer> genre_ids, Double vote_average) {
        this.title = title;
        this.id = id;
        this.poster_path = poster_path;
        this.genre_ids = genre_ids;
        this.overview = overview;
        this.release_date = release_date;
        this.vote_average=vote_average;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public List<Integer> getGenre_ids() {
        return genre_ids;
    }

    public String getTitle() {
        return title;
    }

    public Integer getId() {
        return id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    @Override
    public String toString() {
        return title + '\'' ;
    }
}
