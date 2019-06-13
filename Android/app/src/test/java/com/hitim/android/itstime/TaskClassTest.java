package com.hitim.android.itstime;

import android.graphics.Color;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TaskClassTest {

    private String name;
    private String description;
    private DatePicked datePicked;
    private DatePicked neededTime;
    private String sphereDef;
    private String[] spheres;
    private int color;
    private Task tsk;
    private ArrayList<Task> taskArrayList;

    @Before
    public void init() throws Exception{
        name = "Test yourself";
        description = "Description";
        datePicked = new DatePicked(6, 45, 2019, 6, 1);
        neededTime = new DatePicked(3, 15, 2019, 5, 1);
        sphereDef = "Work";
        spheres = new String[]{"Work", "Yourself", "Routine", "Health"};
        color = Color.BLUE;
        tsk = new Task(name, description, datePicked, sphereDef, color, neededTime, 1);

        taskArrayList = new ArrayList<>();
        Task taskWork = new Task(name, description, datePicked, "Work", color, neededTime, 1);
        Task taskHealth = new Task(name, description, datePicked, "Health", color, neededTime, 1);
        Task taskRoutine = new Task(name, description, datePicked, "Routine", color, neededTime, 1);
        Task taskYourself = new Task(name, description, datePicked, "Yourself", color, neededTime, 1);
        taskArrayList.add(taskYourself);
        taskArrayList.add(taskHealth);
        taskArrayList.add(taskWork);
        taskArrayList.add(taskRoutine);
    }


    @Test
    public void testTaskClass() throws Exception{
        assertEquals("Task name exception", name, tsk.getName());
        assertEquals("Task description exception", description, tsk.getDescription());
        assertEquals("Task date(deadline) exception", datePicked, tsk.getDatePicked());
        assertEquals("Task date(start) exception", neededTime, tsk.getNeededTimePicked());
        assertEquals("Task sphereDef exception", sphereDef, tsk.getSphere());
        assertEquals("Task color exception", color, tsk.getColor());
    }

    @Test
    public void testTaskDate() throws Exception{
        Calendar ndTm = neededTime.getCalendarFormat();
        Calendar dtTm = datePicked.getCalendarFormat();
        assertEquals("Task date(start) exception  with Calendar format",
                ndTm.getTime().toString(),
                tsk.getNeededTimePicked().getCalendarFormat().getTime().toString()
        );
        assertEquals("Task date(deadline) exception with Calendar format",
                dtTm.getTime().toString(),
                tsk.getDatePicked().getCalendarFormat().getTime().toString()
        );
    }

    @Test
    public void testTaskSphere() throws Exception{

        for (int i = 0; i < 4; i++) {
            boolean correct = false;
            for (int j = 0; j < 4; j++) {
                String tmp = taskArrayList.get(i).getSphere();
                correct = tmp.equals(spheres[j]);
                if (correct) break;
            }
            assertTrue("Task spheres exception", correct);
        }
    }
}