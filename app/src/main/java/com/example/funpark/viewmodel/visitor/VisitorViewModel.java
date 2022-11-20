package com.example.funpark.viewmodel.visitor;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.funpark.BaseApp;
import com.example.funpark.database.entity.TicketTypeEntity;
import com.example.funpark.database.entity.VisitorEntity;
import com.example.funpark.database.repository.TicketTypeRepository;
import com.example.funpark.database.repository.VisitorRepository;
import com.example.funpark.util.OnAsyncEventListener;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe pour gérer l'interaction entre l'affichage d'un visiteur
 * et la récupération de ses données
 */
public class VisitorViewModel extends AndroidViewModel {

    private Application application;

    private VisitorRepository repository;
    private final TicketTypeRepository repositoryTicketType;


    // Objet qui réagit lors de la mise à jour de ses objets
    private final MediatorLiveData<VisitorEntity> observableVisitor;

    public VisitorViewModel(@NonNull Application application,
                            final int visitorId,
                            VisitorRepository visitorRepository, TicketTypeRepository repositoryTicketType) {
        super(application);

        this.application = application;

        repository = visitorRepository;
        this.repositoryTicketType = repositoryTicketType;

        observableVisitor = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableVisitor.setValue(null);

        LiveData<VisitorEntity> visitor = repository.getVisitor(visitorId, application);

        // observe the changes of the account entity from the database and forward them
        observableVisitor.addSource(visitor, observableVisitor::setValue);
    }

    /**
     * Classe factory qui permet de crée qu'une seul fois l'instance
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final int visitorId;

        private final VisitorRepository repository;
        private final TicketTypeRepository repositoryTicketType;

        public Factory(@NonNull Application application, int visitorId) {
            this.application = application;
            this.visitorId = visitorId;
            repository = ((BaseApp) application).getVisitorRepository();
            repositoryTicketType = ((BaseApp) application).getTicketTypeRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new VisitorViewModel(application, visitorId, repository, repositoryTicketType);
        }
    }

    /**
     * Permet à l'UI d'atteindre le visiteur.
     * @return
     */
    public LiveData<VisitorEntity> getVisitor() {
        return observableVisitor;
    }

    /**
     * Permet à l'UI de demander la création d'un visiteur.
     * @param visitor
     * @param callback
     */
    public void createVisitor(VisitorEntity visitor, OnAsyncEventListener callback) {
        repository.insert(visitor, callback, application);
    }

    /**
     * Permet à l'UI de demander la mise à jour d'un visiteur
     * @param visitor
     * @param callback
     */
    public void updateVisitor(VisitorEntity visitor, OnAsyncEventListener callback) {
        repository.update(visitor, callback, application);
    }

    /**
     * Permet de supprimer un visiteur
     * @param visitor
     * @param callback
     */
    public void deleteVisitor(VisitorEntity visitor, OnAsyncEventListener callback) {
        repository.delete(visitor, callback, application);
    }

    /**
     * Permet à l'UI d'atteindre la liste des types tickets pour le spinner
     * @return
     */
    public LiveData<List<TicketTypeEntity>> getTicketTypes() {
        return repositoryTicketType.getTicketTypes(application);
    }
}
