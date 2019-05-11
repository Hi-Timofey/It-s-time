package com.hitim.android.itstime;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
class Task {
    @PrimaryKey private String name;
    private String description;
    private DatePicked datePicked;
    //public Color taskColor;

    private ArrayList<String> tags;
    private String sphere;

    public Task(String name,String description/*,Color taskColor*/,ArrayList<String> tags, String sphere){
        this.name = name;
        this.description = description;
        //this.taskColor = taskColor;
        this.tags = tags;
        this.sphere = sphere;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public ArrayList<String> getTags() {
        return this.tags;
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

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public void setSphere(String sphere) {
        this.sphere = sphere;
    }
}
