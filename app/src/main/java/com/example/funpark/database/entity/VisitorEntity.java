package com.example.funpark.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.TypeConverters;

import com.example.funpark.util.DateConverter;

import java.util.Date;

@Entity(tableName = "visitors", primaryKeys = {"id"}, foreignKeys =
    @ForeignKey(
            entity = TicketTypeEntity.class,
            parentColumns = "id",
            childColumns = "ticketType"
    ))
@TypeConverters(DateConverter.class)
public class VisitorEntity {

    @NonNull
    private int id;

    private String lastName;
    private String firstName;
    private Date birthDate;
    private int ticketType;
    private Date visitDate;
    @Ignore
    public VisitorEntity(){
    }

    public VisitorEntity(@NonNull int id, String lastName, String firstName, Date birthDate, int ticketType, Date visitDate) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.ticketType = ticketType;
        this.visitDate = visitDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public int getTicketType() {
        return ticketType;
    }

    public void setTicketType(int ticketType) {
        this.ticketType = ticketType;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }
}
