package com.example.funpark.viewmodel.ticket;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.funpark.BaseApp;
import com.example.funpark.database.entity.TicketEntity;
import com.example.funpark.database.repository.TicketRepository;
import com.example.funpark.util.OnAsyncEventListener;

import java.util.List;

public class TicketListViewModel extends AndroidViewModel {

    private Application application;

    private TicketRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<TicketEntity>> observableTickets;

    public TicketListViewModel(@NonNull Application application,
                               TicketRepository ticketRepository) {
        super(application);

        this.application = application;

        repository = ticketRepository;

        observableTickets = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableTickets.setValue(null);

        LiveData<List<TicketEntity>> tickets =
                ticketRepository.getTickets(application);

        // observe the changes of the entities from the database and forward them
        observableTickets.addSource(tickets, observableTickets::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final TicketRepository ticketRepository;

        public Factory(@NonNull Application application) {
            this.application = application;
            ticketRepository = ((BaseApp) application).getTicketRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new TicketListViewModel(application, ticketRepository);
        }
    }

    /**
     * Expose the LiveData VisitorAccounts query so the UI can observe it.
     */
    public LiveData<List<TicketEntity>> getTickets() {
        return observableTickets;
    }

    /**
     * Expose the LiveData AccountEntities query so the UI can observe it.
     */

    public void deleteTicket(TicketEntity ticket, OnAsyncEventListener callback) {
        repository.delete(ticket, callback, application);
    }

}

