package com.example.funpark.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.funpark.database.entity.TicketEntity;
@Dao
public interface TicketDao {

    @Query("SELECT * FROM tickets WHERE id=:id")
    LiveData<TicketEntity> getById(int id);

    @Query("SELECT * FROM tickets")
    LiveData<TicketEntity> getAll();

    @Query("DELETE FROM tickets")
    void deleteAll();

    /**
    //Return all tickets from a ticketType
    @Query("SELECT * FROM tickets,tickettypes WHERE tickettypes.id = tickets.ticketType ")
    void getTicketsForATicketType();
    **/
}

