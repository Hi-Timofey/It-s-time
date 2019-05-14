package com.hitim.android.itstime;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
class Task {
    @PrimaryKey
    @NonNull
    private String name;
    private String description;
    @Embedded(prefix = "deadline")
    private DatePicked datePicked;
    //public Color taskColor;
    //private ArrayList<String> tags;
    private String sphere;

    public Task(@NonNull String name, String description, DatePicked datePicked, String sphere) {
        this.name = name;
        this.description = description;
        this.sphere = sphere;
        this.datePicked = datePicked;
    }

    @NonNull
    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getSphere() {
        return this.sphere;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DatePicked getDatePicked() {
        return datePicked;
    }

    public void setDatePicked(DatePicked datePicked) {
        this.datePicked = datePicked;
    }

    public void setSphere(String sphere) {
        this.sphere = sphere;
    }
}
