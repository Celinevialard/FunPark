package com.example.funpark.viewmodel.salesTicket;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.funpark.BaseApp;
import com.example.funpark.database.entity.SalesTicketEntity;

import com.example.funpark.database.repository.SalesTicketRepository;

import com.example.funpark.util.OnAsyncEventListener;


import java.util.List;

public class SalesTicketListViewModel extends AndroidViewModel {

    private Application application;

    private SalesTicketRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<SalesTicketEntity>> observableSalesTickets;

    public SalesTicketListViewModel(@NonNull Application application,
                               SalesTicketRepository salesTicketRepository) {
        super(application);

        this.application = application;

        repository = salesTicketRepository;

        observableSalesTickets = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableSalesTickets.setValue(null);

        LiveData<List<SalesTicketEntity>> salesTickets =
                salesTicketRepository.getSalesTickets(application);

        // observe the changes of the entities from the database and forward them
        observableSalesTickets.addSource(salesTickets, observableSalesTickets::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final SalesTicketRepository salesTicketRepository;

        public Factory(@NonNull Application application) {
            this.application = application;
            salesTicketRepository = ((BaseApp) application).getSalesTicketRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new SalesTicketListViewModel(application, salesTicketRepository);
        }
    }

    /**
     * Expose the LiveData VisitorAccounts query so the UI can observe it.
     */
    public LiveData<List<SalesTicketEntity>> getSalesTickets() {
        return observableSalesTickets;
    }

    /**
     * Expose the LiveData AccountEntities query so the UI can observe it.
     */

    public void deleteSalesTicket(SalesTicketEntity salesTicket, OnAsyncEventListener callback) {
        repository.delete(salesTicket, callback, application);
    }

}

