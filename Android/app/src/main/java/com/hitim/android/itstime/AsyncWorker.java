package com.hitim.android.itstime;

import android.os.AsyncTask;


public class AsyncWorker{

    private TaskDataBase taskDataBase;
    private TaskDao taskDao;

    public AsyncWorker(){
        taskDataBase = App.getInstance().getDataBase();
        taskDao = taskDataBase.getTaskDao();
    }

    public boolean insertTask(Task task){
        return true;
    }

    public boolean deleteTask(Task task){
        return true;
    }

    public boolean updateTask(Task task){
        return true;
    }

}
