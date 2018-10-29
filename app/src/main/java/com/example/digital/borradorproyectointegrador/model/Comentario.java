package com.example.digital.borradorproyectointegrador.model;

public class Comentario {

    //Atributos
    private String peliculaComentada;
    private Integer ivImagenPeliculaComentario;
    private Integer ivImagenUsuarioComentario;
    private String tvUsuarioComentario;
    private Integer cantidadEstrellasAPelicula;
    private String tvComentarioComentario;

    //Constructor Completo
    public Comentario(String peliculaComentada, Integer ivImagenPeliculaComentario, Integer ivImagenUsuarioComentario, String tvUsuarioComentario, Integer cantidadEstrellasAPelicula, String tvComentarioComentario) {
        this.peliculaComentada = peliculaComentada;
        this.ivImagenPeliculaComentario = ivImagenPeliculaComentario;
        this.ivImagenUsuarioComentario = ivImagenUsuarioComentario;
        this.tvUsuarioComentario = tvUsuarioComentario;
        this.cantidadEstrellasAPelicula = cantidadEstrellasAPelicula;
        this.tvComentarioComentario = tvComentarioComentario;
    }
    //ConstructorTrailer
    public Comentario(String peliculaComentada, Integer ivImagenUsuarioComentario, String tvUsuarioComentario, Integer cantidadEstrellasAPelicula, String tvComentarioComentario) {
        this.peliculaComentada = peliculaComentada;
        this.ivImagenUsuarioComentario = ivImagenUsuarioComentario;
        this.tvUsuarioComentario = tvUsuarioComentario;
        this.cantidadEstrellasAPelicula = cantidadEstrellasAPelicula;
        this.tvComentarioComentario = tvComentarioComentario;
    }

    //Getter
    public String getPeliculaComentada() {
        return peliculaComentada;
    }

    public Integer getIvImagenPeliculaComentario() {
        return ivImagenPeliculaComentario;
    }

    public Integer getIvImagenUsuarioComentario() {
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
