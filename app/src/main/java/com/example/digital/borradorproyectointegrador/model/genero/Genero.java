package com.example.digital.borradorproyectointegrador.model.genero;

public class Genero {

    //Atributos
    private Integer id;
    private String name;

    //Constructor
    public Genero(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    //getter
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    //toString

    @Override
    public String toString() {
        return name + '\'' ;
    }
}
