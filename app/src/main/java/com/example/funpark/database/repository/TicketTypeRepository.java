package com.example.funpark.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.funpark.database.entity.TicketTypeEntity;
import com.example.funpark.database.firebase.TicketTypeListLiveData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("ticketTypes");

        return new TicketTypeListLiveData(reference);
    }


}
