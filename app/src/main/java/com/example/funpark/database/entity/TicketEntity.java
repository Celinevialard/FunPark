package com.example.funpark.database.entity;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;

import com.example.funpark.ui.SettingsActivity;

@Entity(tableName = "tickets", primaryKeys = {"id"}, foreignKeys =
    @ForeignKey(
            entity = TicketTypeEntity.class,
            parentColumns = "id",
            childColumns = "ticketType",
            onDelete = ForeignKey.CASCADE
    ))
public class TicketEntity {

    @NonNull
    private int id;

    private String ticketNameEn;
    private String ticketNameFr;
    private double priceSummer;
    private double priceWinter;
    private int duration;
    private int ticketType;

    @Ignore
    public TicketEntity() {
    }

    public TicketEntity(@NonNull int id, String ticketNameEn,String ticketNameFr, double priceSummer, double priceWinter, int duration, int ticketType) {
        this.id = id;
        this.ticketNameEn = ticketNameEn;
        this.ticketNameFr = ticketNameFr;
        this.priceSummer = priceSummer;
        this.priceWinter = priceWinter;
        this.duration = duration;
        this.ticketType = ticketType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTicketNameEn() {
        return ticketNameEn;
    }

    public void setTicketNameEn(String ticketNameEn) {
        this.ticketNameEn = ticketNameEn;
    }

    public String getTicketNameFr() {
        return ticketNameFr;
    }

    public void setTicketNameFr(String ticketNameFr) {
        this.ticketNameFr = ticketNameFr;
    }

    public double getPriceSummer() {
        return priceSummer;
    }

    public void setPriceSummer(double priceSummer) {
        this.priceSummer = priceSummer;
    }

    public double getPriceWinter() {
        return priceWinter;
    }

    public void setPriceWinter(double priceWinter) {
        this.priceWinter = priceWinter;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getTicketType() {
        return ticketType;
    }

    public void setTicketType(int ticketType) {
        this.ticketType = ticketType;
    }

    public String toString(Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String language = sharedPref.getString(SettingsActivity.KEY_PREF_LANGUAGE, "-1");
        switch (language){
            case "en":
            default:
                return  ticketNameEn;
            case "fr":
                return ticketNameFr;
        }
    }
}
