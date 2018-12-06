package com.example.digital.borradorproyectointegrador.model.comentario;

import android.net.Uri;

public class Comentario {

    //Atributos
    private Integer idPelioSerie;
    private Integer tipo;
    private String peliculaComentada;
    private String ivImagenPeliculaComentario;
    private String ivImagenUsuarioComentario;
    private String tvUsuarioComentario;
    private Integer cantidadEstrellasAPelicula;
    private String tvComentarioComentario;

    //Constructor Completo
    public Comentario(Integer idPelioSerie,Integer tipo,String peliculaComentada, String ivImagenPeliculaComentario, String ivImagenUsuarioComentario, String tvUsuarioComentario, Integer cantidadEstrellasAPelicula, String tvComentarioComentario) {
        this.idPelioSerie = idPelioSerie;
        this.tipo = tipo;
        this.peliculaComentada = peliculaComentada;
        this.ivImagenPeliculaComentario = ivImagenPeliculaComentario;
        this.ivImagenUsuarioComentario = ivImagenUsuarioComentario;
        this.tvUsuarioComentario = tvUsuarioComentario;
        this.cantidadEstrellasAPelicula = cantidadEstrellasAPelicula;
        this.tvComentarioComentario = tvComentarioComentario;
    }
    //ConstructorTrailer
    public Comentario(String peliculaComentada,Integer idPelioSerie,Integer tipo, String ivImagenUsuarioComentario, String tvUsuarioComentario, Integer cantidadEstrellasAPelicula, String tvComentarioComentario) {
        this.idPelioSerie = idPelioSerie;
        this.tipo = tipo;
        this.peliculaComentada = peliculaComentada;
        this.ivImagenUsuarioComentario = ivImagenUsuarioComentario;
        this.tvUsuarioComentario = tvUsuarioComentario;
        this.cantidadEstrellasAPelicula = cantidadEstrellasAPelicula;
        this.tvComentarioComentario = tvComentarioComentario;
    }

    //Getter


    public Integer getIdPelioSerie() {
        return idPelioSerie;
    }

    public Integer getTipo() {
        return tipo;
    }

    public String getPeliculaComentada() {
        return peliculaComentada;
    }

    public String getIvImagenPeliculaComentario() {
        return ivImagenPeliculaComentario;
    }

    public String getIvImagenUsuarioComentario() {
        return ivImagenUsuarioComentario;
    }

    public String getTvUsuarioComentario() {
        return tvUsuarioComentario;
    }

    public Integer getCantidadEstrellasAPelicula() {
        return cantidadEstrellasAPelicula;
    }

    public String getTvComentarioComentario() {
        return tvComentarioComentario;
    }

    //tostring

    @Override
    public String toString() {
        return "Comentario{" +
                "peliculaComentada='" + peliculaComentada + '\'' +
                ", ivImagenPeliculaComentario=" + ivImagenPeliculaComentario +
                ", ivImagenUsuarioComentario=" + ivImagenUsuarioComentario +
                ", tvUsuarioComentario='" + tvUsuarioComentario + '\'' +
                ", cantidadEstrellasAPelicula=" + cantidadEstrellasAPelicula +
                ", tvComentarioComentario='" + tvComentarioComentario + '\'' +
                '}';
    }
}
