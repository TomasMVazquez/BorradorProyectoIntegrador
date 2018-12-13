package com.example.digital.borradorproyectointegrador.dao.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.digital.borradorproyectointegrador.model.serie.Serie;

import java.util.List;

@Dao
public interface DaoSerieDB {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertarSeries(List<Serie> series);


    @Query("SELECT * FROM Series")
    List<Serie> buscarSeries();

}
