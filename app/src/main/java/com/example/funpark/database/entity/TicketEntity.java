package com.example.funpark.database.entity;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;

import com.example.funpark.util.IEntityBase;
import com.example.funpark.util.PreferenceHelper;

/***
 * Classe qui contient les tickets qui peuvent être vendu
 */
@Entity(tableName = "tickets", primaryKeys = {"id"}, foreignKeys =
@ForeignKey(
        entity = TicketTypeEntity.class,
        parentColumns = "id",
        childColumns = "ticketType",
        onDelete = ForeignKey.CASCADE
))
public class TicketEntity implements IEntityBase {

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

    public TicketEntity(@NonNull int id, String ticketNameEn, String ticketNameFr, double priceSummer, double priceWinter, int duration, int ticketType) {
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

    @Override
    public String toString(Context context) {
        String language = PreferenceHelper.getLanguage(context);
        switch (language) {
            case "en":
            default:
                return ticketNameEn;
            case "fr":
                return ticketNameFr;
        }
    }
}
