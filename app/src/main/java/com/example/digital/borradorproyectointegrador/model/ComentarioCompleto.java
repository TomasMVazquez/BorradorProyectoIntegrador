package com.example.digital.borradorproyectointegrador.model;

public class ComentarioCompleto {

    //Atributos
    private Integer ivImagenPeliculaComentario;
    private Integer ivImagenUsuarioComentario;
    private String tvUsuarioComentario;
    private Integer cantidadEstrellasAPelicula;
    private String tvComentarioComentario;

    //Constructor
    public ComentarioCompleto(Integer ivImagenPeliculaComentario, Integer ivImagenUsuarioComentario, String tvUsuarioComentario, Integer cantidadEstrellasAPelicula, String tvComentarioComentario) {
        this.ivImagenPeliculaComentario = ivImagenPeliculaComentario;
        this.ivImagenUsuarioComentario = ivImagenUsuarioComentario;
        this.tvUsuarioComentario = tvUsuarioComentario;
        this.cantidadEstrellasAPelicula = cantidadEstrellasAPelicula;
        this.tvComentarioComentario = tvComentarioComentario;
    }

    //Getter
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
        return "ComentarioCompleto{" +
                "ivImagenPeliculaComentario=" + ivImagenPeliculaComentario +
                ", ivImagenUsuarioComentario=" + ivImagenUsuarioComentario +
                ", tvUsuarioComentario='" + tvUsuarioComentario + '\'' +
                ", cantidadEstrellasAPelicula=" + cantidadEstrellasAPelicula +
                ", tvComentarioComentario='" + tvComentarioComentario + '\'' +
                '}';
    }
}
