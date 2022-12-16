package com.example.funpark.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.funpark.database.entity.VisitorEntity;
import com.example.funpark.database.firebase.VisitorListLiveData;
import com.example.funpark.database.firebase.VisitorLiveData;
import com.example.funpark.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Gestion de la relation avec la base de données pour les visiteurs
 */
public class VisitorRepository {

    private final String keyName = "visitors";
    private static VisitorRepository instance;

    private VisitorRepository() {
    }

    public static VisitorRepository getInstance() {
        if (instance == null) {
            synchronized (VisitorRepository.class) {
                if (instance == null) {
                    instance = new VisitorRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<VisitorEntity> getVisitor(final String id, Application application) {
        if (id == null) {
            return new VisitorLiveData(new VisitorEntity());
        }
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference(keyName)
                .child(id);

        return new VisitorLiveData(reference);
    }

    public LiveData<List<VisitorEntity>> getVisitors(Application application) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference(keyName);
        return new VisitorListLiveData(reference);
    }

    public void insert(final VisitorEntity visitor, OnAsyncEventListener callback,
                       Application application) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference(keyName);
        String key = reference.push().getKey();
        FirebaseDatabase.getInstance()
                .getReference(keyName)
                .child(key)
                .setValue(visitor, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final VisitorEntity visitor, OnAsyncEventListener callback,
                       Application application) {
        FirebaseDatabase.getInstance()
                .getReference(keyName)
                .child(visitor.getId())
                .updateChildren(visitor.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final VisitorEntity visitor, OnAsyncEventListener callback,
                       Application application) {
        FirebaseDatabase.getInstance()
                .getReference(keyName)
                .child(visitor.getId())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
}
