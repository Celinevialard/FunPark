package com.example.funpark.adapter;

import java.util.Date;

/***
 * classe pour utiliser les dates sans se soucier des contraintes de la classe de base
 */
public class DateAdapter extends Date {

    public DateAdapter(Date date) {
        super(date.getYear(), date.getMonth(), date.getDate());
    }
    public DateAdapter(int year, int month, int date) {
        super(year, month, date);
    }

    public DateAdapter(String date) {
        super();
        setDate(Integer.parseInt(date.substring(8,10)));
        setMonth(Integer.parseInt(date.substring(5,7)));
        setYear(Integer.parseInt(date.substring(0,4)));
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
        String dateString = this.getYear() + "-";
        if (this.getMonth() < 10)
            dateString += "0";
        dateString += this.getMonth() + "-";
        if (this.getDate() < 10)
            dateString += "0";
        dateString += this.getDate() ;

        return dateString;
    }
}
