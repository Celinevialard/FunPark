package com.example.funpark.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.funpark.database.entity.TicketEntity;
import com.example.funpark.database.entity.TicketTypeEntity;
@Dao
public interface TicketTypeDao {

    @Query("SELECT * FROM tickettypes WHERE id=:id")
    LiveData<TicketTypeEntity> getById(int id);

    @Query("SELECT * FROM tickettypes")
    LiveData<TicketTypeEntity> getAll();

    @Query("DELETE FROM tickettypes")
    void deleteAll();

    @Insert
    long insert(TicketTypeEntity ticketType);
}
