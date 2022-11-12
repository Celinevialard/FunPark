package com.example.funpark.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "tickets", primaryKeys = {"id"})
public class TicketEntity {

    @NonNull
    private int id;

    private String ticketName;
    private double price;
    private int duration;
    private int ticketType;

    @Ignore
    public TicketEntity() {
    }

    public TicketEntity(int id, String ticketName, double price, int duration, int ticketType) {
        this.id = id;
        this.ticketName = ticketName;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
