package com.example.funpark.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.funpark.BaseApp;
import com.example.funpark.database.async.salesTicket.CreateSalesTicket;
import com.example.funpark.database.async.salesTicket.DeleteSalesTicket;

import com.example.funpark.database.entity.SalesTicketEntity;

import com.example.funpark.util.OnAsyncEventListener;

import java.util.List;

public class SalesTicketRepository {

    private static SalesTicketRepository instance;

    private SalesTicketRepository(){

    }

    public static SalesTicketRepository getInstance() {
        if (instance == null) {
            synchronized (SalesTicketRepository.class) {
                if (instance == null) {
                    instance = new SalesTicketRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<SalesTicketEntity> getSalesTicket(final int id, Application application) {
        return ((BaseApp) application).getDatabase().salesTicketDao().getById(id);
    }

    public LiveData<List<SalesTicketEntity>> getSalesTickets(Application application) {
        return ((BaseApp)application).getDatabase().salesTicketDao().getAll();
    }

    public void insert(final SalesTicketEntity salesTicket, OnAsyncEventListener callback,
                       Application application) {
        new CreateSalesTicket(application, callback).execute(salesTicket);
    }

    public void delete(final SalesTicketEntity salesTicket, OnAsyncEventListener callback,
                       Application application) {
        new DeleteSalesTicket(application, callback).execute(salesTicket);
    }

}