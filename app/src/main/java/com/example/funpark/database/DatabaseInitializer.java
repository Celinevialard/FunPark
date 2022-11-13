package com.example.funpark.database;

import android.os.AsyncTask;
import android.util.Log;

import com.example.funpark.database.entity.TicketEntity;
import com.example.funpark.database.entity.TicketTypeEntity;
import com.example.funpark.database.entity.VisitorEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatabaseInitializer {

    public static final String TAG = "DatabaseInitializer";

    public static void populateDatabase(final AppDatabase db) {
        Log.i(TAG, "Inserting demo data.");
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    private static void addTicketType(final AppDatabase db, final int id, final String nameEn, final String nameFr) {
        TicketTypeEntity ticketType = new TicketTypeEntity(id, nameEn, nameFr);
        db.ticketTypeDao().insert(ticketType);
    }

    private static void addVisitor(final AppDatabase db, final int id, final String lastname, final String firstName,
                                   final Date birthdate,final int ticketType, final Date visitDate) {
        VisitorEntity visitor = new VisitorEntity(id, lastname, firstName, birthdate,ticketType, visitDate);
        db.visitorDao().insert(visitor);
    }

    private static void addTicket(final AppDatabase db, final int id,final String ticketName, final double priceSummer, final double priceWinter,
                                   final int duration, final int ticketType ) {
        TicketEntity ticket = new TicketEntity(id, ticketName, priceSummer, priceWinter, duration, ticketType);
        db.ticketDao().insert(ticket);
    }


    private static void populateWithTestData(AppDatabase db) {
        db.visitorDao().deleteAll();
        db.ticketTypeDao().deleteAll();
        db.ticketDao().deleteAll();


        addTicketType(db,1,"Adult","Adulte");
        addTicketType(db,2,"Senior","Sénior");
        addTicketType(db,3,"Student","Etudiant");
        addTicketType(db,4,"Kid","Enfant");
        addTicketType(db,5,"Baby","Bébé");

        try {
            // Let's ensure that the ticketType are already stored in the database before we continue.
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        addVisitor(db,1,"Celine", "Vialard", new Date(1998- 1900,01-1,12),3,new Date(2022- 1900,11-1,12));
        addVisitor(db,2,"Donald", "Trump",new Date(1912- 1900,04-1,22),2,new Date(2022- 1900,11-1,12));
        addVisitor(db,3,"Lionel", "Messi",new Date(1982- 1900,12-1,22),1,new Date(2022- 1900,11-1,12));
        addVisitor(db,4,"Mateo", "Messi",new Date(2010- 1900,2-1,15),4,new Date(2022- 1900,11-1,12));
        addVisitor(db,5,"John", "Musk",new Date(2022- 1900,4-1,2),5,new Date(2022- 1900,11-1,12));

        try {
            // Let's ensure that the visitors are already stored in the database before we continue.
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        addTicket(db,1,"Pass 1 journée Adulte",35,30,1,1);
        addTicket(db,2,"Pass 1 journée Senior",28,23,1,2);
        addTicket(db,3,"Pass 1 journée Student",28,23,1,3);
        addTicket(db,4,"Pass 1 journée Kid",22,17,1,4);
        addTicket(db,5,"Pass 1 journée Baby",15,10,1,5);

        addTicket(db,6,"Pass 2 journées Adulte",69,59,2,1);
        addTicket(db,7,"Pass 2 journées Senior",55,45,2,2);
        addTicket(db,8,"Pass 2 journées Student",55,45,2,3);
        addTicket(db,9,"Pass 2 journées Kid",43,33,2,4);
        addTicket(db,10,"Pass 2 journées Baby",29,19,2,5);

        addTicket(db,11,"Pass 5 journées Adulte",160,135,5,1);
        addTicket(db,12,"Pass 5 journées Senior",125,100,5,2);
        addTicket(db,13,"Pass 5 journées Student",125,100,5,3);
        addTicket(db,14,"Pass 5 journées Kid",95,70,5,4);
        addTicket(db,15,"Pass 5 journées Baby",60,35,5,5);

        addTicket(db,11,"Pass mensuel Adulte",350,300,30,1);
        addTicket(db,12,"Pass mensuel Senior",280,230,30,2);
        addTicket(db,13,"Pass mensuel Student",280,230,30,3);
        addTicket(db,14,"Pass mensuel Kid",220,170,30,4);
        addTicket(db,15,"Pass mensuel Baby",150,100,30,5);

        addTicket(db,11,"Pass annuel Adulte",1750,1500,360,1);
        addTicket(db,12,"Pass annuel Senior",1400,1150,360,2);
        addTicket(db,13,"Pass annuel Student",1400,1150,360,3);
        addTicket(db,14,"Pass annuel Kid",1100,850,360,4);
        addTicket(db,15,"Pass annuel Baby",750,500,360,5);
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase database;

        PopulateDbAsync(AppDatabase db) {
            database = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(database);
            return null;
        }

    }
}
