package com.example.funpark.adapter;

import java.util.Date;

/***
 * class pour utiliser les dates snas ce soucier des contraintes de la classe de base
 */
public class DateAdapter extends Date {

    public DateAdapter(Date date) {
        super(date.getYear(), date.getMonth(), date.getDate());
    }

    @Override
    public void setYear(int year) {
        super.setYear(year - 1900);
    }

    @Override
    public void setMonth(int month) {
        super.setMonth(month - 1);
    }

    @Override
    public int getYear() {
        return super.getYear() + 1900;
    }

    @Override
    public int getMonth() {
        return super.getMonth() + 1;
    }

    @Override
    public String toString() {
        String dateString = "";
        if (this.getDate() < 10)
            dateString += "0";
        dateString += this.getDate() + ".";
        if (this.getMonth() < 10)
            dateString += "0";
        dateString += this.getMonth() + "." + this.getYear();
        return dateString;
    }
}
