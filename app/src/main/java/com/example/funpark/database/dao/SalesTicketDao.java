package com.example.funpark.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.funpark.database.entity.SalesTicketEntity;
import com.example.funpark.database.pojo.SalesTicketWithTickets;

import java.util.List;

/**
 * DAO qui permet de faire les actions nécessaires
 * dans la base de données pour les billets vendus
 */

@Dao
public interface SalesTicketDao {

    @Query("SELECT * FROM salesTickets")
    LiveData<List<SalesTicketWithTickets>> getSalesTicketWithTickets();

    @Query("SELECT * FROM salesTickets WHERE id=:id")
    LiveData<SalesTicketWithTickets> getById(int id);

    @Query("DELETE FROM salesTickets")
    void deleteAll();

    @Insert
    long insert(SalesTicketEntity salesTicket) throws SQLiteConstraintException;

    @Delete
    void delete(SalesTicketEntity salesTicket);

}
