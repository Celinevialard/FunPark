package com.example.funpark.database.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.funpark.database.entity.SalesTicketEntity;
import com.example.funpark.database.entity.TicketEntity;

import java.util.List;

public class SalesTicketWithTickets {
    @Embedded
    public SalesTicketEntity salesTicket;

    @Relation(parentColumn = "ticket", entityColumn = "id", entity = TicketEntity.class)
    public TicketEntity ticket;

    @Override
    public String toString() {
        return salesTicket.getFirstname()+" "+ticket.getTicketNameEn();
    }
}
