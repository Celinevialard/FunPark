package com.example.funpark.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.*;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.funpark.database.dao.SalesTicketDao;
import com.example.funpark.database.dao.TicketDao;
import com.example.funpark.database.dao.TicketTypeDao;
import com.example.funpark.database.dao.VisitorDao;
import com.example.funpark.database.entity.SalesTicketEntity;
import com.example.funpark.database.entity.TicketEntity;
import com.example.funpark.database.entity.TicketTypeEntity;
import com.example.funpark.database.entity.VisitorEntity;

import java.util.concurrent.Executors;

@Database(entities = {TicketTypeEntity.class, VisitorEntity.class, TicketEntity.class, SalesTicketEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = "AppDatabase";

    private static AppDatabase instance;

    private static final String DATABASE_NAME = "funpark-database";

    public abstract TicketTypeDao ticketTypeDao();
    public abstract VisitorDao visitorDao();
    public abstract TicketDao ticketDao();
    public abstract SalesTicketDao salesTicketDao();

    private final MutableLiveData<Boolean> isDatabaseCreated = new MutableLiveData<>();

    public static AppDatabase getInstance(final Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = buildDatabase(context.getApplicationContext());
                    instance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    /**
     * Build the database. {@link Builder#build()} only sets up the database configuration and
     * creates a new instance of the database.
     * The SQLite database is only created when it's accessed for the first time.
     */
    private static AppDatabase buildDatabase(final Context appContext) {
        Log.i(TAG, "Database will be initialized.");
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadExecutor().execute(() -> {
                            AppDatabase database = AppDatabase.getInstance(appContext);
                            initializeDemoData(database);
                            // notify that the database was created and it's ready to be used
                            database.setDatabaseCreated();
                        });
                    }
                }).build();
    }

    public static void initializeDemoData(final AppDatabase database) {
        Executors.newSingleThreadExecutor().execute(() -> {
            database.runInTransaction(() -> {
                Log.i(TAG, "Wipe database.");
                //Delete les visiteurs avant le ticket type
                database.visitorDao().deleteAll();
                database.ticketTypeDao().deleteAll();
                //Hardcoder le delete ticket mais expliquer qu'il sera supprimer avec deletecascade dans tickettype
                database.ticketDao().deleteAll();
                database.salesTicketDao().deleteAll();
                DatabaseInitializer.populateDatabase(database);
            });
        });
    }

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            Log.i(TAG, "Database initialized.");
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated(){
        isDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return isDatabaseCreated;
    }
}
