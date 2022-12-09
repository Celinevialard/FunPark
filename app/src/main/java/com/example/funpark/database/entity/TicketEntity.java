package com.example.funpark.database.entity;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;

import com.example.funpark.util.IEntityBase;
import com.example.funpark.util.PreferenceHelper;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/***
 * Classe qui contient les tickets qui peuvent être vendu
 */

public class TicketEntity implements IEntityBase {

    private String id;

    private String ticketNameEn;
    private String ticketNameFr;
    private double priceSummer;
    private double priceWinter;
    private int duration;
    private String ticketType;
    private String ticketTypeEn;
    private String ticketTypeFr;


    public TicketEntity() {
    }

    public TicketEntity(String ticketNameEn, String ticketNameFr, double priceSummer, double priceWinter, int duration, String ticketType, String ticketTypeEn, String ticketTypeFr) {
        this.ticketNameEn = ticketNameEn;
        this.ticketNameFr = ticketNameFr;
        this.priceSummer = priceSummer;
        this.priceWinter = priceWinter;
        this.duration = duration;
        this.ticketType = ticketType;
        this.ticketTypeEn = ticketTypeEn;
        this.ticketTypeFr = ticketTypeFr;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
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

    @Exclude
    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("ticketType",ticketType);
        result.put("ticketTypeFr",ticketTypeFr);
        result.put("ticketTypeEn",ticketTypeEn);
        result.put("ticketNameEn",ticketNameEn);
        result.put("ticketNameFr",ticketNameFr);
        result.put("priceSummer",priceSummer);
        result.put("priceWinter",priceWinter);
        result.put("duration",duration);

        return  result;
    }
}
