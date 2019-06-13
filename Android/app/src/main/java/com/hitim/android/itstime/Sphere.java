package com.hitim.android.itstime;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

class Sphere {
    private final String name;
    private final Integer drawableId;
    private final Integer iconId;
    private static List<Sphere> defaultSpheres;

    Sphere(String name, Integer drawableId, Integer iconId) {
        this.name = name;
        this.drawableId = drawableId;
        this.iconId = iconId;
    }

    Integer getIconId() {
        return iconId;
    }

    static List<Sphere> getDefaultSpheres() {
        return defaultSpheres;
    }

    static void initDefaultSpheres(Context context) {
        defaultSpheres = new ArrayList<>();
        defaultSpheres.add(new Sphere(context.getString(R.string.all_tasks_db), R.drawable.all_tasks_background, R.drawable.ic_arrow_forward));
        defaultSpheres.add(new Sphere(context.getString(R.string.work_db), R.drawable.work_background, R.drawable.ic_work));
        defaultSpheres.add(new Sphere(context.getString(R.string.health_db), R.drawable.health_background, R.drawable.ic_heart));
        defaultSpheres.add(new Sphere(context.getString(R.string.routine_db), R.drawable.routine_background, R.drawable.ic_routine));
        defaultSpheres.add(new Sphere(context.getString(R.string.yourself_db), R.drawable.yourself_background, R.drawable.ic_nature_people_white_24dp));
    }

    public String getName() {
        return name;
    }

    Integer getDrawableId() {
        return drawableId;
    }

}
