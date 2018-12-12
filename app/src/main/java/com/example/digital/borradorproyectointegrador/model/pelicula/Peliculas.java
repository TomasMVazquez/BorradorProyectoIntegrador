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
    private String original_language;
    private String revenue;
    private String runtime;
    private String status;
    private String tagline;

    public Peliculas(String title, Integer id, String poster_path, List<Integer> genre_ids, String overview, String release_date, Double vote_average, String original_language, String revenue, String runtime, String status, String tagline) {
        this.title = title;
        this.id = id;
        this.poster_path = poster_path;
        this.genre_ids = genre_ids;
        this.overview = overview;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.original_language = original_language;
        this.revenue = revenue;
        this.runtime = runtime;
        this.status = status;
        this.tagline = tagline;
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

    public String getOriginal_language() {
        return original_language;
    }

    public String getRevenue() {
        return revenue;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getStatus() {
        return status;
    }

    public String getTagline() {
        return tagline;
    }

    @Override
    public String toString() {
        return title + '\'' ;
    }
}
