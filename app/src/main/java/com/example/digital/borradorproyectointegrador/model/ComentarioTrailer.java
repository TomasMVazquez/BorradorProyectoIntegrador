package com.example.digital.borradorproyectointegrador.model;

public class ComentarioTrailer {

    //Atributos
    private String peliculaComentada;
    private Integer ivImagenUsuarioComentario;
    private String tvUsuarioComentario;
    private Integer cantidadEstrellasAPelicula;
    private String tvComentarioComentario;

    //Construcotr
    public ComentarioTrailer(String peliculaComentada,Integer ivImagenUsuarioComentario, String tvUsuarioComentario, Integer cantidadEstrellasAPelicula, String tvComentarioComentario) {
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
        return "ComentarioTrailer{" +
                "peliculaComentada='" + peliculaComentada + '\'' +
                ", ivImagenUsuarioComentario=" + ivImagenUsuarioComentario +
                ", tvUsuarioComentario='" + tvUsuarioComentario + '\'' +
                ", cantidadEstrellasAPelicula=" + cantidadEstrellasAPelicula +
                ", tvComentarioComentario='" + tvComentarioComentario + '\'' +
                '}';
    }
}
