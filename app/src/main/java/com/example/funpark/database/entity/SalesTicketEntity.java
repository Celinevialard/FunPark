package com.example.funpark.database.entity;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.TypeConverters;

import com.example.funpark.util.DateConverter;
import com.example.funpark.util.IEntityBase;
import com.example.funpark.util.PreferenceHelper;
import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/***
 * classe qui contient les tickets achetés par un utilisateur du park
 */
public class SalesTicketEntity implements IEntityBase {

    @NonNull
    private String id;

    private String lastname;
    private String firstname;
    private String birthDate;
    private double price;
    private int duration;
    private String ticket;
    private String ticketTypeEn;
    private String ticketTypeFr;
    private String ticketNameEn;
    private String ticketNameFr;

    @Ignore
    public SalesTicketEntity() {
    }

    public SalesTicketEntity(String lastname, String firstname, String birthDate, String ticket) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.birthDate = birthDate;
        this.ticket = ticket;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
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

    public String getTicketTypeEn() {
        return ticketTypeEn;
    }

    public String getTicketTypeFr() {
        return ticketTypeFr;
    }

    public String getTicketNameEn() {
        return ticketNameEn;
    }

    public String getTicketNameFr() {
        return ticketNameFr;
    }

    public void setTicketTypeEn(String ticketTypeEn) {
        this.ticketTypeEn = ticketTypeEn;
    }

    public void setTicketTypeFr(String ticketTypeFr) {
        this.ticketTypeFr = ticketTypeFr;
    }

    public void setTicketNameEn(String ticketNameEn) {
        this.ticketNameEn = ticketNameEn;
    }

    public void setTicketNameFr(String ticketNameFr) {
        this.ticketNameFr = ticketNameFr;
    }

    public String getTicketTypeName(Context context) {
        String language = PreferenceHelper.getLanguage(context);
        switch (language) {
            case "en":
            default:
                return ticketNameEn;
            case "fr":
                return ticketNameFr;
        }
    }


    public String getTicketName(Context context) {
        String language = PreferenceHelper.getLanguage(context);
        switch (language) {
            case "en":
            default:
                return ticketNameEn;
            case "fr":
                return ticketNameFr;
        }
    }

    @Exclude
    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("ticket",ticket);
        result.put("ticketTypeFr",ticketTypeFr);
        result.put("ticketTypeEn",ticketTypeEn);
        result.put("ticketNameEn",ticketNameEn);
        result.put("ticketNameFr",ticketNameFr);
        result.put("price",price);
        result.put("duration",duration);
        result.put("birthDate",birthDate);
        result.put("firstname",firstname);
        result.put("lastname",lastname);

        return  result;
    }

    @Override
    public String toString(Context context) {
        return getFirstname() + " " + getTicketName(context);
    }
}
