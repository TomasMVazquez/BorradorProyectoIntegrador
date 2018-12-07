package com.example.digital.borradorproyectointegrador.model.usuario_perfil;

import com.example.digital.borradorproyectointegrador.model.comentario.Comentario;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

import java.util.List;

public class UsuarioPerfil {

    //atributos
    private String uId;
    private String email;
    private String imagenPerfil;
    private String nombre;
    private Integer cantidadMeGusta;
    private Integer cantidadCompartidos;
    private Integer cantidadComentarios;
    private List<Integer> peliculasFavoritas;
    private List<Integer> seriesFavoritas;
    private List<Comentario> idMisComentarios;

    //constructor

    public UsuarioPerfil() {
    }

    public UsuarioPerfil(String uId, String email, String imagenPerfil, String nombre, Integer cantidadMeGusta , Integer cantidadCompartidos, Integer cantidadComentarios, List<Integer> peliculasFavoritas, List<Integer> seriesFavoritas, List<Comentario> idMisComentarios) {
        this.uId = uId;
        this.email=email;
        this.imagenPerfil = imagenPerfil;
        this.nombre = nombre;
        this.cantidadMeGusta =cantidadMeGusta;
        this.cantidadCompartidos = cantidadCompartidos;
        this.cantidadComentarios = cantidadComentarios;
        this.peliculasFavoritas = peliculasFavoritas;
        this.seriesFavoritas = seriesFavoritas;
        this.idMisComentarios = idMisComentarios;
    }

    //Getter

    public String getuId() {
        return uId;
    }

    public String getEmail() {
        return email;
    }

    public String getImagenPerfil() {
        return imagenPerfil;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getCantidadMeGusta() {
        return cantidadMeGusta;
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

    public List<Comentario> getIdMisComentarios() {
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
