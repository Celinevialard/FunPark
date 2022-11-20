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
import com.example.funpark.database.entity.TicketTypeEntity;
import com.example.funpark.database.entity.VisitorEntity;
import com.example.funpark.database.repository.TicketRepository;
import com.example.funpark.database.repository.TicketTypeRepository;
import com.example.funpark.util.OnAsyncEventListener;

import java.util.List;

/**
 * Classe pour gérer l'interaction entre l'affichage d'un billet
 * et la récupération de ses données
 */
public class TicketViewModel  extends AndroidViewModel {

    private Application application;

    private TicketRepository repository;
    private final TicketTypeRepository repositoryTicketType;

    // Objet qui réagit lors de la mise à jour de ses objets
    private final MediatorLiveData<TicketEntity> observableTicket;

    public TicketViewModel(@NonNull Application application,
                                final int ticketId,
                                TicketRepository ticketRepository, TicketTypeRepository ticketTypeRepository) {
        super(application);

        this.application = application;

        repository = ticketRepository;
        repositoryTicketType = ticketTypeRepository;

        observableTicket = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableTicket.setValue(null);

        LiveData<TicketEntity> ticket = repository.getTicket(ticketId, application);

        // observe the changes of the account entity from the database and forward them
        observableTicket.addSource(ticket, observableTicket::setValue);
    }

    /**
     * Classe factory qui permet de crée qu'une seul fois l'instance
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final int ticketId;

        private final TicketRepository repository;
        private final TicketTypeRepository repositoryTicketType;

        public Factory(@NonNull Application application, int ticketId) {
            this.application = application;
            this.ticketId = ticketId;
            repository = ((BaseApp) application).getTicketRepository();
            repositoryTicketType = ((BaseApp)application).getTicketTypeRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new TicketViewModel(application, ticketId, repository, repositoryTicketType);
        }
    }

    /**
     * Permet à l'UI d'atteindre le ticket.
     * @return le ticket du context
     */
    public LiveData<TicketEntity> getTicket() {
        return observableTicket;
    }

    /**
     * Permet à l'UI de demander la création d'un ticket.
     * @param ticket
     * @param callback
     */
    public void createTicket(TicketEntity ticket, OnAsyncEventListener callback) {
        repository.insert(ticket, callback, application);
    }

    /**
     * Permet à l'UI de demander la mise à jour d'un ticket.
     * @param ticket
     * @param callback
     */
    public void updateTicket(TicketEntity ticket, OnAsyncEventListener callback) {
        repository.update(ticket, callback, application);
    }

    /**
     * Permet de supprimer un ticket
     * @param ticket
     * @param callback
     */
    public void deleteTicket(TicketEntity ticket, OnAsyncEventListener callback) {
        repository.delete(ticket, callback, application);
    }

    /**
     * Permet à l'UI d'atteindre la liste des types tickets pour le spinner
     * @return
     */
    public LiveData<List<TicketTypeEntity>> getTicketTypes() {
        return repositoryTicketType.getTicketTypes(application);
    }
}
