package com.example.funpark.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

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
    private final String keyName = "tickets";

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
        if (id == null) {
            return new TicketLiveData(new TicketEntity());
        }
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference(keyName)
                .child(id);
        return new TicketLiveData(reference);
    }

    public LiveData<List<TicketEntity>> getTickets(Application application) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference(keyName);
        return new TicketListLiveData(reference);
    }

    public void insert(final TicketEntity ticket, OnAsyncEventListener callback,
                       Application application) {
        FirebaseDatabase.getInstance()
                .getReference(keyName)
                .child(ticket.getTicketType() + ticket.getDuration() + "day")
                .setValue(ticket, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final TicketEntity ticket, OnAsyncEventListener callback,
                       Application application) {
        FirebaseDatabase.getInstance()
                .getReference(keyName)
                .child(ticket.getId())
                .updateChildren(ticket.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final TicketEntity ticket, OnAsyncEventListener callback,
                       Application application) {
        FirebaseDatabase.getInstance()
                .getReference(keyName)
                .child(ticket.getId())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

}
