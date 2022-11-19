package com.example.funpark.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.TypeConverters;

import com.example.funpark.util.DateConverter;

import java.util.Date;

@Entity(tableName = "salesTickets", primaryKeys = {"id"}, foreignKeys =
@ForeignKey(
        entity = TicketEntity.class,
        parentColumns = "id",
        childColumns = "ticket",
        onDelete = ForeignKey.CASCADE
))
@TypeConverters(DateConverter.class)
public class SalesTicketEntity {

    @NonNull
    private int id;

    private String lastname;
    private String firstname;
    private Date birthDate;
    private int ticket;

    @Ignore
    public SalesTicketEntity() {
    }

    public SalesTicketEntity(@NonNull int id, String lastname, String firstname, Date birthDate, int ticket) {
        this.id = id;
        this.lastname = lastname;
        this.firstname = firstname;
        this.birthDate = birthDate;
        this.ticket = ticket;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public int getTicket() {
        return ticket;
    }

    public void setTicket(int ticket) {
        this.ticket = ticket;
    }
}
