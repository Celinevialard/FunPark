package com.example.funpark.database.entity;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.TypeConverters;

import com.example.funpark.util.DateConverter;
import com.example.funpark.util.IEntityBase;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe qui contient les visiteurs -> la date de leur visite
 * ainsi que d'autre information pour faire des statistiques
 */
@Entity(tableName = "visitors", primaryKeys = {"id"}, foreignKeys =
@ForeignKey(
        entity = TicketTypeEntity.class,
        parentColumns = "id",
        childColumns = "ticketType"
))
@TypeConverters(DateConverter.class)
public class VisitorEntity implements IEntityBase {

    @NonNull
    private int id;

    private String lastName;
    private String firstName;
    private Date birthDate;
    private int ticketType;
    private Date visitDate;

    @Ignore
    public VisitorEntity() {
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

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
        return lastName + " " + firstName + " " + format.format(visitDate);
    }

    @Override
    public String toString(Context context) {
        return toString();
    }
}
