package com.example.funpark.adapter;

import java.util.Date;

/***
 * classe pour utiliser les dates sans se soucier des contraintes de la classe de base
 */
public class DateAdapter extends Date {

    public DateAdapter(Date date) {
        super(date.getYear(), date.getMonth(), date.getDate());
    }

    public DateAdapter(String date) {

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
