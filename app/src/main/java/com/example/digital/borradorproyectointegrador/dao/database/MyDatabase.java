package com.example.digital.borradorproyectointegrador.dao.database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.digital.borradorproyectointegrador.model.pelicula.Peliculas;
import com.example.digital.borradorproyectointegrador.model.serie.Serie;

@Database(entities = {Peliculas.class, Serie.class}, version = 1, exportSchema = false)

public abstract class MyDatabase extends RoomDatabase {
    public abstract DaoPeliculaDB getDaoPeliculaDB();
    public abstract DaoSerieDB getDaoSerieDB();
}
