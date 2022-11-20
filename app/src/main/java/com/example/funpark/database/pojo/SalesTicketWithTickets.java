package com.example.funpark.database.pojo;

import android.content.Context;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.funpark.database.entity.SalesTicketEntity;
import com.example.funpark.database.entity.TicketEntity;
import com.example.funpark.util.IEntityBase;
import com.example.funpark.util.PreferenceHelper;

import java.util.List;

public class SalesTicketWithTickets implements IEntityBase {
    @Embedded
    public SalesTicketEntity salesTicket;

    @Relation(parentColumn = "ticket", entityColumn = "id", entity = TicketEntity.class)
    public TicketEntity ticket;

    @Override
    public String toString(Context context) {
        String language = PreferenceHelper.getLanguage(context);
        switch (language){
            case "en":
            default:
                return salesTicket.getFirstname()+" "+ticket.getTicketNameEn();
            case "fr":
                return salesTicket.getFirstname()+" "+ticket.getTicketNameFr();
        }
    }
}
