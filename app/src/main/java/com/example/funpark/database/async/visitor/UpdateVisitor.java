package com.example.funpark.database.async.visitor;

import android.app.Application;
import android.os.AsyncTask;

import com.example.funpark.BaseApp;
import com.example.funpark.database.entity.VisitorEntity;
import com.example.funpark.util.OnAsyncEventListener;

public class UpdateVisitor extends AsyncTask<VisitorEntity, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public UpdateVisitor(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(VisitorEntity... params) {
        try {
            for (VisitorEntity visitor : params)
                ((BaseApp) application).getDatabase().visitorDao()
                        .update(visitor);
        } catch (Exception e) {
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (callback != null) {
            if (exception == null) {
                callback.onSuccess();
            } else {
                callback.onFailure(exception);
            }
        }
    }
}
