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

/**
 * Classe pour gérer l'interaction entre l'affichage de la liste des billets
 * et la récupération de ses données
 */
public class TicketListViewModel extends AndroidViewModel {

    private Application application;

    private TicketRepository repository;

    // Objet qui réagit lors de la mise à jour de ses objets
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
     * Classe factory qui permet de crée qu'une seul fois l'instance
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
     * Permet à l'UI d'atteindre la liste des tickets.
     * @return la liste des tickets
     */
    public LiveData<List<TicketEntity>> getTickets() {
        return observableTickets;
    }

    /**
     * Permet de supprimer un ticket
     * @param ticket
     * @param callback
     */
    public void deleteTicket(TicketEntity ticket, OnAsyncEventListener callback) {
        repository.delete(ticket, callback, application);
    }

}

