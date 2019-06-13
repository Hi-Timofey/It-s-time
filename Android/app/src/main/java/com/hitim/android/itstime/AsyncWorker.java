package com.hitim.android.itstime;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Класс {@link AsyncWorker} создан для работы с бд асинхронно
 * Для каждого запроса к базе генерирует анонимный класс для работы в другом потоке с этими данными
 */


class AsyncWorker {

    private final TaskDao taskDao;


    AsyncWorker() {
        taskDao = App.getInstance().getDataBase().getTaskDao();
    }

    List<Task> getAllTasks() {

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

    List<Task> getAllTasksWithSphere(String sphere) {
        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, List<Task>> asyncTask =
                new AsyncTask<Void, Void, List<Task>>() {

                    @Override
                    protected List<Task> doInBackground(Void... voids) {
                        try {
                            return taskDao.getAllTasksWithSphere(sphere);
                        } catch (Exception e) {
                            Log.w("DATABASE", e.getMessage());
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
        } catch (Exception e ){
            e.printStackTrace();
            Log.w("DATABASE", e.getMessage());
        }
        return null;
    }


    boolean insertTask(Task task) {
        @SuppressLint("StaticFieldLeak") AsyncTask<Task, Void, Boolean> asyncTask = new AsyncTask<Task, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Task... tasks) {
                try {
                    taskDao.insert(tasks[0]);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        };
        asyncTask.execute(task);
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

    public void deleteTask(Task task) {
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
    }

    void updateTask(Task task) {
        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Boolean> asyncTask = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    taskDao.update(task);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        };
        asyncTask.execute();
    }
}
