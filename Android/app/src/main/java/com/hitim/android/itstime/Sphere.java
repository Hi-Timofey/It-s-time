package com.hitim.android.itstime;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class Sphere {
    private String name;
    private Integer drawableId;
    private Integer iconId;
    private static List<Sphere> defaultSpheres;

    public Sphere(String name, Integer drawableId, Integer iconId) {
        this.name = name;
        this.drawableId = drawableId;
        this.iconId = iconId;
    }

    public Integer getIconId() {
        return iconId;
    }

    public void setIconId(Integer iconId) {
        this.iconId = iconId;
    }

    public static List<Sphere> getDefaultSpheres() {
        return defaultSpheres;
    }

    public static void initDefaultSpheres(Context context) {
        defaultSpheres = new ArrayList<>();
        defaultSpheres.add(new Sphere(context.getString(R.string.all_tasks_db), R.drawable.btn_background, R.drawable.arrow_up));
        defaultSpheres.add(new Sphere(context.getString(R.string.work_db), R.drawable.work_background, R.drawable.ic_work));
        defaultSpheres.add(new Sphere(context.getString(R.string.health_db), R.drawable.health_background, R.drawable.ic_heart));
        defaultSpheres.add(new Sphere(context.getString(R.string.routine_db), R.drawable.routine_background, R.drawable.ic_routine));
        defaultSpheres.add(new Sphere(context.getString(R.string.yourself_db), R.drawable.yourself_background, R.drawable.ic_nature_people_white_24dp));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(Integer drawableId) {
        this.drawableId = drawableId;
    }


}
