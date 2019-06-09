package com.hitim.android.itstime;

import android.graphics.Color;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TaskClassTest {

    private String name = "Test yourself";
    private String description = "Description";
    private DatePicked datePicked = new DatePicked(6,45,2019,6,1);
    private DatePicked neededTime = new DatePicked(3,15,2019,5,1);
    private String sphereDef = "Work";
    private String[] spheres = {"Work", "Yourself", "Routine", "Health"};
    private int color = Color.BLUE;
    private Task tsk = new Task(name,description,datePicked, sphereDef,color,neededTime,1);

    @Test
    public void testTaskClass() {
        assertEquals("Task name exception",name,tsk.getName());
        assertEquals("Task description exception",description, tsk.getDescription());
        assertEquals("Task date(deadline) exception",datePicked,tsk.getDatePicked());
        assertEquals("Task date(start) exception",neededTime,tsk.getNeededTimePicked());
        assertEquals("Task sphereDef exception", sphereDef,tsk.getSphere());
        assertEquals("Task color exception",color,tsk.getColor());
    }

    @Test
    public void testTaskDate(){
        Calendar ndTm = neededTime.getCalendarFormat();
        Calendar dtTm = datePicked.getCalendarFormat();
        assertEquals("Task date(start) exception  with Calendar format",
                ndTm.getTime().toString() ,
                tsk.getNeededTimePicked().getCalendarFormat().getTime().toString()
        );
        assertEquals("Task date(deadline) exception with Calendar format",
                dtTm.getTime().toString(),
                tsk.getDatePicked().getCalendarFormat().getTime().toString()
        );
    }

    @Test
    public void testTaskSphere(){
        Task taskWork = new Task(name,description,datePicked, "Work",color,neededTime,1);
        Task taskHealth = new Task(name,description,datePicked, "Health",color,neededTime,1);
        Task taskRoutine = new Task(name,description,datePicked, "Routine",color,neededTime,1);
        Task taskYourself = new Task(name,description,datePicked, "Yourself",color,neededTime,1);
        ArrayList<Task> taskArrayList = new ArrayList<>();
        taskArrayList.add(taskYourself);
        taskArrayList.add(taskHealth);
        taskArrayList.add(taskWork);
        taskArrayList.add(taskRoutine);
        for (int i = 0;i < 4;i++){
            boolean correct = false;
            for(int j = 0;j < 4;j++){
                String tmp = taskArrayList.get(i).getSphere();
                correct = tmp.equals(spheres[j]);
                if (correct) break;
                else continue;
            }
            assertTrue("Task spheres exception", correct);
        }
    }
}