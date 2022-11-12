package com.example.funpark.database.async.ticket;

import android.app.Application;
import android.os.AsyncTask;

import com.example.funpark.BaseApp;
import com.example.funpark.database.entity.TicketEntity;
import com.example.funpark.util.OnAsyncEventListener;

public class CreateTicket extends AsyncTask<TicketEntity, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public CreateTicket(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(TicketEntity... params) {
        try{
            for(TicketEntity ticket : params)
                ((BaseApp) application).getDatabase().ticketDao()
                        .insert(ticket);
        } catch(Exception e){
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
