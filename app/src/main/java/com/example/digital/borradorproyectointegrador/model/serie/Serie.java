package com.example.digital.borradorproyectointegrador.model.serie;

import java.util.List;

public class Serie {

    //Atributos
    private Integer tipo = 2;
    private String name;
    private Integer id;
    private String poster_path;
    private List<Integer> genre_ids;
    private String overview;
    private String first_air_date;
    private Double vote_average;
    private String homepage;
    private String in_production;
    private String number_of_episodes;
    private String number_of_seasons;
    private String original_language;
    private String status;


    //Constructor


    public Serie(Integer tipo, String name, Integer id, String poster_path, List<Integer> genre_ids, String overview, String first_air_date, Double vote_average, String homepage, String in_production, String number_of_episodes, String number_of_seasons, String original_language, String status) {
        this.tipo = 2;
        this.name = name;
        this.id = id;
        this.poster_path = poster_path;
        this.genre_ids = genre_ids;
        this.overview = overview;
        this.first_air_date = first_air_date;
        this.vote_average = vote_average;
        this.homepage = homepage;
        this.in_production = in_production;
        this.number_of_episodes = number_of_episodes;
        this.number_of_seasons = number_of_seasons;
        this.original_language = original_language;
        this.status = status;
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

    public Integer getTipo() {
        return tipo;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getIn_production() {
        return in_production;
    }

    public String getNumber_of_episodes() {
        return number_of_episodes;
    }

    public String getNumber_of_seasons() {
        return number_of_seasons;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getStatus() {
        return status;
    }

    //toString
    @Override
    public String toString() {
        return "Serie{" +
                "title='" + name + '\'' +
                '}';
    }
}
