package com.example.digital.borradorproyectointegrador.model.comentario;

import java.util.List;

public class ComentarioConteiner {

    //atributos
    private List<Comentario> comentarios;

    //constructor
    public ComentarioConteiner(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    //getter
    public List<Comentario> getComentarios() {
        return comentarios;
    }

    //tostring
    @Override
    public String toString() {
        return "ComentarioConteiner{" +
                "comentarios=" + comentarios +
                '}';
    }
}
