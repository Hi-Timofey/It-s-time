package com.hitim.android.itstime;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SphereTest {


    private String name;
    private Integer drawableId;
    private Integer iconId;
    private Sphere defaultSphereWork;

    @Before
    public void init() {

        name = "Work";
        drawableId = R.drawable.work_background;
        iconId = R.drawable.ic_work;
        defaultSphereWork = new Sphere(name, drawableId, iconId);

        /*ArrayList<Sphere> defaultSpheres = new ArrayList<>();
        defaultSpheres.add(new Sphere("All task's", R.drawable.all_tasks_background, R.drawable.ic_arrow_forward));
        defaultSpheres.add(new Sphere("Work", R.drawable.work_background, R.drawable.ic_work));
        defaultSpheres.add(new Sphere("Health", R.drawable.health_background, R.drawable.ic_heart));
        defaultSpheres.add(new Sphere("Routine", R.drawable.routine_background, R.drawable.ic_routine));
        defaultSpheres.add(new Sphere("Yourself", R.drawable.yourself_background, R.drawable.ic_nature_people_white_24dp));*/
    }

    @Test
    public void testSphereClass() {
        assertEquals("Sphere name exception", name, defaultSphereWork.getName());
        assertEquals("Sphere drawable exception", drawableId, defaultSphereWork.getDrawableId());
        assertEquals("Sphere icon exception", iconId, defaultSphereWork.getIconId());
    }
}
