package com.example.funpark.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "tickettypes", primaryKeys = {"id"})
public class TicketTypeEntity{

    @NonNull
    private int id;

    private String name;

    @Ignore
    public TicketTypeEntity(){
    }

    public TicketTypeEntity(@NonNull int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
