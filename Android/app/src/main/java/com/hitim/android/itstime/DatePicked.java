package com.hitim.android.itstime;


import androidx.annotation.NonNull;

public class DatePicked {
    public final static int DATE_ONLY = 0;
    public final static int TIME_ONLY = 1;
    public final static int TIME_DATE = 2;
    public final static int NULL_TIME = 3;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minutes;
    private int type;

    @NonNull
    @Override
    public String toString() {
        if (minutes < 10) {
            return day + ":" + month + ":" + year + " " + hour + ":0" + minutes;
        } else {
            return day + ":" + month + ":" + year + " " + hour + ":" + minutes;
        }
    }

    public DatePicked() {
    }

    public DatePicked(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.type = DATE_ONLY;
    }

    public DatePicked(int hour, int minutes) {
        this.hour = hour;
        this.minutes = minutes;
        this.type = TIME_ONLY;
    }

    public DatePicked(int hour, int minutes, int year, int month, int day) {
        this.hour = hour;
        this.minutes = minutes;
        this.year = year;
        this.month = month;
        this.day = day;
        this.type = TIME_DATE;
    }

    public void resetAll() {
        this.year = -1;
        this.month = -1;
        this.day = -1;
        this.minutes = -1;
        this.hour = -1;
        this.type = NULL_TIME;
    }

    public boolean isNull() {
        return year == -1 || month == -1 || day == -1 || minutes == -1 || hour == -1;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

}
