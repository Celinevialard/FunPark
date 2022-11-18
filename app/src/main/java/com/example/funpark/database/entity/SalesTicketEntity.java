package com.example.funpark.database.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;

import java.util.Date;

@Entity(tableName = "salesTickets", primaryKeys = {"id"}, foreignKeys =
@ForeignKey(
        entity = TicketEntity.class,
        parentColumns = "id",
        childColumns = "ticket",
        onDelete = ForeignKey.CASCADE
))
public class SalesTicketEntity {

    private int id;

    private String firstname;
    private String lastname;
    private Date birthDate;
    private int ticket;

    @Ignore
    public SalesTicketEntity() {
    }

    public SalesTicketEntity(int id, String firstname, String lastname, Date birthDate, int ticket) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthDate = birthDate;
        this.ticket = ticket;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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
