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

import com.example.funpark.util.OnAsyncEventListener;


import java.util.List;

/**
 * Classe pour gérer l'interaction entre l'affichage de la liste des tickets vendus
 * et la récupération de ses données
 */
public class SalesTicketListViewModel extends AndroidViewModel {

    private Application application;

    private SalesTicketRepository repository;

    // Objet qui réagit lors de la mise à jour de ses objets
    private final MediatorLiveData<List<SalesTicketWithTickets>> observableSalesTicketsWithTickets;

    public SalesTicketListViewModel(@NonNull Application application,
                               SalesTicketRepository salesTicketRepository) {
        super(application);

        this.application = application;

        repository = salesTicketRepository;

        observableSalesTicketsWithTickets = new MediatorLiveData<>();
        // initialisé à zéro en attendant
        observableSalesTicketsWithTickets.setValue(null);

        LiveData<List<SalesTicketWithTickets>> salesTickets =
                salesTicketRepository.getSalesTicketsWithTickets(application);

        // observation des modifications de la base de données et les transmet
        observableSalesTicketsWithTickets.addSource(salesTickets, observableSalesTicketsWithTickets::setValue);
    }

    /**
     * Classe factory qui permet de créer qu'une seul fois l'instance
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
     * Permet à l'UI d'atteindre la liste des billets vendus.
     * @return
     */
    public LiveData<List<SalesTicketWithTickets>> getSalesTickets() {
        return observableSalesTicketsWithTickets;
    }

}

