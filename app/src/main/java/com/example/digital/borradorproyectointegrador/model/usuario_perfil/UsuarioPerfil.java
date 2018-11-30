package com.example.digital.borradorproyectointegrador.model.usuario_perfil;

import com.example.digital.borradorproyectointegrador.model.comentario.Comentario;

import java.util.List;

public class UsuarioPerfil {

    //atributos
    private String imagenPerfil;
    private String nombre;
    private Integer cantidadCompartidos;
    private Integer cantidadComentarios;
    private List<Integer> peliculasFavoritas;
    private List<Integer> seriesFavoritas;
    private List<String> idMisComentarios;

    //constructor
    public UsuarioPerfil(String imagenPerfil, String nombre, Integer cantidadCompartidos, Integer cantidadComentarios, List<Integer> peliculasFavoritas, List<Integer> seriesFavoritas, List<String> idMisComentarios) {
        this.imagenPerfil = imagenPerfil;
        this.nombre = nombre;
        this.cantidadCompartidos = cantidadCompartidos;
        this.cantidadComentarios = cantidadComentarios;
        this.peliculasFavoritas = peliculasFavoritas;
        this.seriesFavoritas = seriesFavoritas;
        this.idMisComentarios = idMisComentarios;
    }

    //Getter
    public String getImagenPerfil() {
        return imagenPerfil;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getCantidadCompartidos() {
        return cantidadCompartidos;
    }

    public Integer getCantidadComentarios() {
        return cantidadComentarios;
    }

    public List<Integer> getPeliculasFavoritas() {
        return peliculasFavoritas;
    }

    public List<Integer> getSeriesFavoritas() {
        return seriesFavoritas;
    }

    public List<String> getIdMisComentarios() {
        return idMisComentarios;
    }

    //toString
    @Override
    public String toString() {
        return "UsuarioPerfil{" +
                "nombre='" + nombre + '\'' +
                ", cantidadCompartidos=" + cantidadCompartidos +
                ", cantidadComentarios=" + cantidadComentarios +
                ", peliculasFavoritas=" + peliculasFavoritas +
                ", seriesFavoritas=" + seriesFavoritas +
                ", idMisComentarios=" + idMisComentarios +
                '}';
    }
}
