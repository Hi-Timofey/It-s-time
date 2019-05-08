package com.hitim.android.itstime;

import java.util.ArrayList;

class Task {
    private String name;
    private String description;

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
}
