package com.example.funpark.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.funpark.BaseApp;
import com.example.funpark.database.entity.TicketTypeEntity;

import java.util.List;

/**
 * Gestion de la relation avec la base de données pour les types de billets
 */
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

    public LiveData<List<TicketTypeEntity>> getTicketTypes(Application application) {
        return ((BaseApp) application).getDatabase().ticketTypeDao().getAll();
    }
}
