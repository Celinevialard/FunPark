package com.example.funpark.viewmodel.visitor;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.funpark.BaseApp;
import com.example.funpark.database.entity.VisitorEntity;
import com.example.funpark.database.repository.VisitorRepository;
import com.example.funpark.util.OnAsyncEventListener;

import java.util.List;

/**
 * Classe pour gérer l'interaction entre l'affichage de la liste des visiteurs
 * et la récupération de ses données
 */
public class VisitorListViewModel extends AndroidViewModel {

    private Application application;

    private VisitorRepository repository;

    // Objet qui réagit lors de la mise à jour de ses objets
    private final MediatorLiveData<List<VisitorEntity>> observableVisitors;

    public VisitorListViewModel(@NonNull Application application,
                                VisitorRepository repository) {
        super(application);

        this.application = application;

        this.repository = repository;

        observableVisitors = new MediatorLiveData<>();
        //initialisé à zéro en attendant
        observableVisitors.setValue(null);

        LiveData<List<VisitorEntity>> visitors =
                repository.getVisitors(application);

        //observation des modifications de la base de données et les transmet
        observableVisitors.addSource(visitors, observableVisitors::setValue);
    }

    /**
     * Classe factory qui permet de créer qu'une seul fois l'instance
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        
        private final VisitorRepository visitorRepository;


        public Factory(@NonNull Application application) {
            this.application = application;
            visitorRepository = ((BaseApp) application).getVisitorRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new VisitorListViewModel(application, visitorRepository);
        }
    }

    /**
     * Permet à l'UI d'atteindre la liste des visiteurs.
     * @return
     */
    public LiveData<List<VisitorEntity>> getVisitors() {
        return observableVisitors;
    }

    /**
     * Permet de supprimer un visiteur
     * @param visitor
     * @param callback
     */
    public void deleteVisitor(VisitorEntity visitor, OnAsyncEventListener callback) {
        repository.delete(visitor, callback, application);
    }

}
