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

    private TaskDao taskDao;


    public AsyncWorker() {
        taskDao = App.getInstance().getDataBase().getTaskDao();
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

    public List<Task> getAllTasksWithSphere(String sphere) {
        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, List<Task>> asyncTask =
                new AsyncTask<Void, Void, List<Task>>() {

                    @Override
                    protected List<Task> doInBackground(Void... voids) {
                        try {
                            List<Task> result = taskDao.getAllTasksWithSphere(sphere);
                            return result;
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


    public boolean insertTask(Task task) {
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

    public synchronized Task getTaskByName(OnPostExecuteTask executedTask) {
        @SuppressLint("StaticFieldLeak") AsyncTask<String, Void, Task> asyncTask = new AsyncTask<String, Void, Task>() {
            @Override
            protected Task doInBackground(String... strings) {
                try {
                    return taskDao.getByName(strings[0]);
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Task task) {
                if (executedTask != null) executedTask.run(task);
                super.onPostExecute(task);
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

    public void updateTask(Task task) {
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
