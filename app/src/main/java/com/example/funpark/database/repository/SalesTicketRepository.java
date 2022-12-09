package com.example.funpark.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.funpark.database.entity.SalesTicketEntity;
import com.example.funpark.database.firebase.SalesTicketListLiveData;
import com.example.funpark.database.firebase.SalesTicketLiveData;
import com.example.funpark.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Gestion de la relation avec la base de données pour les billets vendus
 */
public class SalesTicketRepository {
    private final String keyName = "salesTickets";

    private static SalesTicketRepository instance;

    private SalesTicketRepository() {

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

    public LiveData<SalesTicketEntity> getSalesTicket(final String id, Application application) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference(keyName)
                .child(id);
        return new SalesTicketLiveData(reference);
    }

    public LiveData<List<SalesTicketEntity>> getSalesTickets(Application application) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference(keyName);
        return new SalesTicketListLiveData(reference);
    }

    public void insert(final SalesTicketEntity salesTicket, OnAsyncEventListener callback,
                       Application application) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference(keyName);
        String key = reference.push().getKey();
        FirebaseDatabase.getInstance()
                .getReference(keyName)
                .child(key)
                .setValue(salesTicket, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final SalesTicketEntity salesTicket, OnAsyncEventListener callback,
                       Application application) {
        FirebaseDatabase.getInstance()
                .getReference(keyName)
                .child(salesTicket.getId())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

}
