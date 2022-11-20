package com.example.funpark.database.entity;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;

import com.example.funpark.util.IEntityBase;
import com.example.funpark.util.PreferenceHelper;

/**
 * Classe qui contient les types de ticket qu'on peut avoir par exemple pour adulte, enfant,...
 */
@Entity(tableName = "tickettypes", primaryKeys = {"id"})
public class TicketTypeEntity implements IEntityBase {

    @NonNull
    private int id;

    private String nameEn;

    private String nameFr;

    @Ignore
    public TicketTypeEntity() {
    }

    public TicketTypeEntity(@NonNull int id, String nameEn, String nameFr) {
        this.id = id;
        this.nameEn = nameEn;
        this.nameFr = nameFr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
