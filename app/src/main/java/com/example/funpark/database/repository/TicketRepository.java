package com.example.funpark.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.funpark.BaseApp;
import com.example.funpark.database.async.ticket.CreateTicket;
import com.example.funpark.database.async.ticket.DeleteTicket;
import com.example.funpark.database.async.ticket.UpdateTicket;
import com.example.funpark.database.entity.TicketEntity;
import com.example.funpark.database.firebase.TicketListLiveData;
import com.example.funpark.database.firebase.TicketLiveData;
import com.example.funpark.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Gestion de la relation avec la base de données pour les billets
 */
public class TicketRepository {

    private static TicketRepository instance;

    private TicketRepository() {

    }

    public static TicketRepository getInstance() {
        if (instance == null) {
            synchronized (TicketRepository.class) {
                if (instance == null) {
                    instance = new TicketRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<TicketEntity> getTicket(final String id, Application application) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("tickets");
        return new TicketLiveData(reference);
    }

    public LiveData<List<TicketEntity>> getTickets(Application application) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("tickets");
        return new TicketListLiveData(reference);
         }

    public void insert(final TicketEntity ticket, OnAsyncEventListener callback,
                       Application application) {
        new CreateTicket(application, callback).execute(ticket);
    }

    public void update(final TicketEntity ticket, OnAsyncEventListener callback,
                       Application application) {
        new UpdateTicket(application, callback).execute(ticket);
    }

    public void delete(final TicketEntity ticket, OnAsyncEventListener callback,
                       Application application) {
        new DeleteTicket(application, callback).execute(ticket);
    }

}
