package com.example.funpark.database.entity;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.TypeConverters;

import com.example.funpark.util.DateConverter;
import com.example.funpark.util.IEntityBase;
import com.google.firebase.database.Exclude;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe qui contient les visiteurs -> la date de leur visite
 * ainsi que d'autres informations pour faire des statistiques
 */

public class VisitorEntity implements IEntityBase {

    @NonNull
    private String id;

    private String lastName;
    private String firstName;
    private String birthDate;
    private String visitDate;
    private String ticketType;
    private String ticketTypeEn;
    private String ticketTypeFr;

    @Ignore
    public VisitorEntity() {
    }

    public VisitorEntity(String lastName, String firstName, String birthDate, String ticketType, String visitDate) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.ticketType = ticketType;
        this.visitDate = visitDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    @Override
    public String toString() {
        return lastName + " " + firstName + " " + visitDate;
    }

    @Override
    public String toString(Context context) {
        return toString();
    }

    @Exclude
    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("ticket",ticketType);
        result.put("ticketTypeFr",ticketTypeFr);
        result.put("ticketTypeEn",ticketTypeEn);
        result.put("birthDate",birthDate);
        result.put("firstname",firstName);
        result.put("lastname",lastName);

        return  result;
    }
}
