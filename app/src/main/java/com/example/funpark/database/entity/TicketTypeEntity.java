package com.example.funpark.database.entity;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;

import com.example.funpark.util.IEntityBase;
import com.example.funpark.util.PreferenceHelper;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe qui contient les types de ticket qu'on peut avoir par exemple pour adulte, enfant,...
 */
public class TicketTypeEntity implements IEntityBase {

    private String id;

    private String nameEn;

    private String nameFr;

    public TicketTypeEntity() {
    }

    public TicketTypeEntity(String nameEn, String nameFr) {
        this.nameEn = nameEn;
        this.nameFr = nameFr;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameFr() {
        return nameFr;
    }

    public void setNameFr(String nameFr) {
        this.nameFr = nameFr;
    }

    @Override
    public String toString(Context context) {
        String language = PreferenceHelper.getLanguage(context);
        switch (language) {
            case "en":
            default:
                return nameEn;
            case "fr":
                return nameFr;
        }
    }

    @Exclude
    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("nameEn",nameEn);
        result.put("nameFr",nameFr);

        return  result;
    }
}
