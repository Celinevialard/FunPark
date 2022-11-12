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


public class VisitorListViewModel extends AndroidViewModel {

    private Application application;

    private VisitorRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<VisitorEntity>> observableVisitors;

    public VisitorListViewModel(@NonNull Application application,
                                VisitorRepository repository) {
        super(application);

        this.application = application;

        this.repository = repository;

        observableVisitors = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableVisitors.setValue(null);

        LiveData<List<VisitorEntity>> visitors =
                repository.getVisitors(application);

        // observe the changes of the entities from the database and forward them
        observableVisitors.addSource(visitors, observableVisitors::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
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
     * Expose the LiveData ClientAccounts query so the UI can observe it.
     */
    public LiveData<List<VisitorEntity>> getVisitors() {
        return observableVisitors;
    }

    /**
     * Expose the LiveData AccountEntities query so the UI can observe it.
     */
    public void deleteAccount(VisitorEntity visitor, OnAsyncEventListener callback) {
        repository.delete(visitor, callback, application);
    }

}
