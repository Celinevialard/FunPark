package com.example.funpark.database.repository;

import android.app.Application;
import android.util.Pair;

import androidx.lifecycle.LiveData;

import com.example.funpark.BaseApp;
import com.example.funpark.database.async.ticket.CreateTicket;
import com.example.funpark.database.async.ticket.DeleteTicket;
import com.example.funpark.database.async.ticket.UpdateTicket;
import com.example.funpark.database.entity.TicketEntity;
import com.example.funpark.util.OnAsyncEventListener;

import java.util.List;

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

    public LiveData<TicketEntity> getTicket(final int ticketId, Application application) {
        return ((BaseApp) application).getDatabase().ticketDao().getById(ticketId);
    }

    public LiveData<List<TicketEntity>> getTickets(Application application) {
        return ((BaseApp) application).getDatabase().ticketDao().getAll();
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
