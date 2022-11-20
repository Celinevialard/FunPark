package com.example.funpark.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.funpark.database.entity.TicketEntity;

import java.util.List;

/**
 * DAO qui permet de faire les actions nécessaires
 * dans la base de données pour les billets
 */
@Dao
public interface TicketDao {

    @Query("SELECT * FROM tickets WHERE id=:id")
    LiveData<TicketEntity> getById(int id);

    @Query("SELECT * FROM tickets")
    LiveData<List<TicketEntity>> getAll();

    @Query("DELETE FROM tickets")
    void deleteAll();

    @Insert
    long insert(TicketEntity ticket) throws SQLiteConstraintException;

    @Delete
    void delete(TicketEntity ticket);

    @Update
    void update(TicketEntity ticket);

}

