package com.example.funpark;

import android.app.Application;

import com.example.funpark.database.repository.SalesTicketRepository;
import com.example.funpark.database.repository.TicketRepository;
import com.example.funpark.database.repository.TicketTypeRepository;
import com.example.funpark.database.repository.VisitorRepository;


/**
 * Android Application class. Used for accessing singletons.
 */
public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public VisitorRepository getVisitorRepository() {
        return VisitorRepository.getInstance();
    }

    public TicketRepository getTicketRepository() {
        return TicketRepository.getInstance();
    }

    public TicketTypeRepository getTicketTypeRepository() {return TicketTypeRepository.getInstance();}

    public SalesTicketRepository getSalesTicketRepository() {return SalesTicketRepository.getInstance();}
}