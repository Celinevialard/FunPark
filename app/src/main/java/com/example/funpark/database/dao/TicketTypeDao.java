package com.example.funpark.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.funpark.database.entity.TicketTypeEntity;

import java.util.List;

/**
 * DAO qui permet de faire les actions nécessaire
 * dans la base de donnée pour les types de billets
 */
@Dao
public interface TicketTypeDao {

    @Query("SELECT * FROM tickettypes")
    LiveData<List<TicketTypeEntity>> getAll();

    @Query("DELETE FROM tickettypes")
    void deleteAll();

    @Insert
    long insert(TicketTypeEntity ticketType);
}
