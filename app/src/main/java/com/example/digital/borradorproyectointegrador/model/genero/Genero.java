package com.example.digital.borradorproyectointegrador.model.genero;

public class Genero {

    //Atributos
    private Integer id;
    private String name;
    private boolean selected = false;

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

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    //toString

    @Override
    public String toString() {
        return name + '\'' ;
    }
}
