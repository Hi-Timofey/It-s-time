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
    @NonNull
    private DatePicked datePicked;
    //public Color taskColor;
    //private ArrayList<String> tags;
    @NonNull
    private String sphere;

    public Task(@NonNull String name, String description, @NonNull DatePicked datePicked, @NonNull String sphere) {
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

    @NonNull
    public String getSphere() {
        return this.sphere;
    }

    @NonNull
    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NonNull
    public DatePicked getDatePicked() {
        return datePicked;
    }

    public void setDatePicked(@NonNull DatePicked datePicked) {
        this.datePicked = datePicked;
    }

    public void setSphere(@NonNull String sphere) {
        this.sphere = sphere;
    }

}
