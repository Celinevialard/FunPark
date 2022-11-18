package com.example.funpark.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.funpark.database.entity.SalesTicketEntity;
import com.example.funpark.database.entity.TicketEntity;

import java.util.List;

public interface SalesTicketDao {

    @Query("SELECT * FROM salesTickets WHERE id=:id")
    LiveData<SalesTicketEntity> getById(int id);

    @Query("SELECT * FROM salesTickets")
    LiveData<List<SalesTicketEntity>> getAll();

    @Query("DELETE FROM salesTickets")
    void deleteAll();

    @Insert
    long insert(SalesTicketEntity salesTicket) throws SQLiteConstraintException;

    @Delete
    void delete(SalesTicketEntity salesTicket);

    @Update
    void update(SalesTicketEntity salesTicket);
}
