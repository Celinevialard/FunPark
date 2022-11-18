package com.example.funpark.database.async.salesTicket;

import android.app.Application;
import android.os.AsyncTask;

import com.example.funpark.BaseApp;
import com.example.funpark.database.entity.SalesTicketEntity;

import com.example.funpark.util.OnAsyncEventListener;

public class CreateSalesTicket extends AsyncTask<SalesTicketEntity, Void, Void> {


    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public CreateSalesTicket(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(SalesTicketEntity... params) {
        try{
            for(SalesTicketEntity salesTicket : params)
                ((BaseApp) application).getDatabase().salesTicketDao()
                        .insert(salesTicket);
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