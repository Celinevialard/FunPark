package com.example.funpark.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;

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

    private String ticketName;
    private double priceSummer;
    private double priceWinter;
    private int duration;
    private int ticketType;

    @Ignore
    public TicketEntity() {
    }

    public TicketEntity(@NonNull int id, String ticketName, double priceSummer, double priceWinter, int duration, int ticketType) {
        this.id = id;
        this.ticketName = ticketName;
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

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
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
}
