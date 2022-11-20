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

import com.example.funpark.database.pojo.SalesTicketWithTickets;
import com.example.funpark.database.repository.SalesTicketRepository;

/**
 * Classe pour gérer l'interaction entre l'affichage d'un billet vendu
 * et la récupération de ses données
 */

public class SalesTicketViewModel extends AndroidViewModel {

    private Application application;

    private SalesTicketRepository repository;

    // Objet qui réagit lors de la mise à jour de ses objets
    private final MediatorLiveData<SalesTicketWithTickets> observableSalesTicket;

    public SalesTicketViewModel(@NonNull Application application,
                           final int salesTicketId,
                           SalesTicketRepository salesTicketRepository) {
        super(application);

        this.application = application;

        repository = salesTicketRepository;

        observableSalesTicket = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableSalesTicket.setValue(null);

        LiveData<SalesTicketWithTickets> salesTicket = repository.getSalesTicket(salesTicketId, application);

        // observe the changes of the account entity from the database and forward them
        observableSalesTicket.addSource(salesTicket, observableSalesTicket::setValue);
    }

    /**
     * Classe factory qui permet de créer qu'une seul fois l'instance
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final int salesTicketId;

        private final SalesTicketRepository repository;

        public Factory(@NonNull Application application, int salesTicketId) {
            this.application = application;
            this.salesTicketId = salesTicketId;
            repository = ((BaseApp) application).getSalesTicketRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new SalesTicketViewModel(application, salesTicketId, repository);
        }
    }

    /**
     * Permet à l'UI d'atteindre le billet vendu.
     * @return le ticket du context
     */
    public LiveData<SalesTicketWithTickets> getSalesTicket() {
        return observableSalesTicket;
    }

}

