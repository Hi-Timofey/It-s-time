package com.hitim.android.itstime;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * {@link Task } - класс описывающий задачу пользователя.
 * Содержит время дедлайна, название, сферу жизни и описание.
 * При помощи {@link androidx.room.Room} сохраняется в качестве
 * базы данных ({@link TaskDataBase})
 */

@Entity
class Task {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String name;
    private String description;
    @Embedded(prefix = "deadline")
    @NonNull
    private DatePicked datePicked;
    @Embedded(prefix = "needed_time")
    private DatePicked neededTimePicked;
    @NonNull
    private int priority;
    private int color;
    //private ArrayList<String> tags;
    @NonNull
    private String sphere;

    public Task(@NonNull String name, String description, @NonNull DatePicked datePicked, @NonNull String sphere, @NonNull int color, DatePicked neededTimePicked, @NonNull int priority) {
        this.name = name;
        this.description = description;
        this.sphere = sphere;
        this.datePicked = datePicked;
        this.color = color;
        if (neededTimePicked.isNull()) {
            this.neededTimePicked = neededTimePicked;
        }
        this.priority = priority;
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
        return sphere;
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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public DatePicked getNeededTimePicked() {
        return neededTimePicked;
    }

    public void setNeededTimePicked(DatePicked neededTimePicked) {
        this.neededTimePicked = neededTimePicked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getTaskDescription() {
        String fullDescription = "Name: " +name + "\n";
        fullDescription +="Sphere: "+ sphere + "\n";
        fullDescription += "Description: \n" + description;
        return fullDescription;
    }
}
