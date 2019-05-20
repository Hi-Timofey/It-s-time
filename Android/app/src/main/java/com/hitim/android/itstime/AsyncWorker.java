package com.hitim.android.itstime;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**Класс {@link AsyncWorker} создан для работы с бд асинхронно
 * Для каждого запроса к базе генерирует анонимный класс для работы в другом потоке с этими данными
 *
 */


public class AsyncWorker {

    private TaskDataBase taskDataBase;
    private TaskDao taskDao;


    public AsyncWorker() {
        taskDataBase = App.getInstance().getDataBase();
        taskDao = taskDataBase.getTaskDao();
    }

    public List<Task> getAllTasks() {

        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, List<Task>> asyncTask =
                new AsyncTask<Void, Void, List<Task>>() {

                    @Override
                    protected List<Task> doInBackground(Void... voids) {
                        try {
                            return taskDao.getAllTasks();
                        } catch (Exception e) {
                            return null;
                        }
                    }

                };
        asyncTask.execute();
        try {
            return asyncTask.get(4, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Task> getAllTasksWithSphere(String sphere){
        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, List<Task>> asyncTask =
                new AsyncTask<Void, Void, List<Task>>() {

                    @Override
                    protected List<Task> doInBackground(Void... voids) {
                        try {
                            return taskDao.getAllTasksWithSphere(sphere);
                        } catch (Exception e) {
                            return null;
                        }
                    }

                };
        asyncTask.execute();
        try {
            return asyncTask.get(4, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }



    public boolean insertTask(Task task) {
        return true;
    }

    public boolean deleteTask(Task task) {
        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Boolean> asyncTask = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    taskDao.delete(task);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        };
        asyncTask.execute();
        try {
            return asyncTask.get(4, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateTask(Task task) {
        return true;
    }

}
