package com.example.digital.borradorproyectointegrador.dao.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.digital.borradorproyectointegrador.model.pelicula.Peliculas;

import java.util.List;

@Dao
public interface DaoPeliculaDB {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertarPeliculas(List<Peliculas> peliculas);


    @Query("SELECT * FROM Peliculas")
    List<Peliculas> buscarPeliculas();

}
