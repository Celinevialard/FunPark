package com.example.funpark.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Query;

import com.example.funpark.database.entity.TicketTypeEntity;

public interface TicketTypeDao {

    @Query("SELECT * FROM tickettypes WHERE id=:id")
    LiveData<TicketTypeEntity> getById(int id);

    @Query("SELECT * FROM tickettypes")
    LiveData<TicketTypeEntity> getAll();
}
