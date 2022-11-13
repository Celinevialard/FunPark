package com.example.funpark.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.funpark.BaseApp;
import com.example.funpark.database.async.ticket.CreateTicket;
import com.example.funpark.database.async.ticket.DeleteTicket;
import com.example.funpark.database.async.ticket.UpdateTicket;
import com.example.funpark.database.entity.TicketEntity;
import com.example.funpark.database.entity.TicketTypeEntity;
import com.example.funpark.util.OnAsyncEventListener;

import java.util.List;

public class TicketTypeRepository {

    private static TicketTypeRepository instance;

    private TicketTypeRepository() {

    }

    public static TicketTypeRepository getInstance() {
        if (instance == null) {
            synchronized (TicketTypeRepository.class) {
                if (instance == null) {
                    instance = new TicketTypeRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<TicketTypeEntity> getTicketType(final int id, Application application) {
        return ((BaseApp) application).getDatabase().ticketTypeDao().getById(id);
    }

    public LiveData<List<TicketTypeEntity>> getTicketTypes(Application application) {
        return ((BaseApp) application).getDatabase().ticketTypeDao().getAll();
    }
}
