package com.example.funpark.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.funpark.BaseApp;
import com.example.funpark.database.async.visitor.*;
import com.example.funpark.database.entity.VisitorEntity;
import com.example.funpark.util.OnAsyncEventListener;

import java.util.List;


public class VisitorRepository {

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

    public LiveData<VisitorEntity> getVisitor(final int id, Application application) {
        return ((BaseApp) application).getDatabase().visitorDao().getById(id);
    }

    public LiveData<List<VisitorEntity>> getVisitors(Application application) {
        return ((BaseApp) application).getDatabase().visitorDao().getAll();
    }

    public void insert(final VisitorEntity visitor, OnAsyncEventListener callback,
                       Application application) {
        new CreateVisitor(application, callback).execute(visitor);
    }

    public void update(final VisitorEntity visitor, OnAsyncEventListener callback,
                       Application application) {
        new UpdateVisitor(application, callback).execute(visitor);
    }

    public void delete(final VisitorEntity visitor, OnAsyncEventListener callback,
                       Application application) {
        new DeleteVisitor(application, callback).execute(visitor);
    }
}
