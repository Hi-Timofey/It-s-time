package com.hitim.android.itstime;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface

TaskDao {
    //Получить все задачи
    @Query("SELECT * FROM task")
    List<Task> getAllTasks();

    //Поиск по имени
    @Query("SELECT * FROM task WHERE name = :name")
    Task getById(long name);

    //Получить все задачи с опр. сферы
    @Query("SELECT * FROM task WHERE sphere LIKE :sphere")
    List<Task> getAllTasksWithSphere(String sphere);

    //Новая задача
    @Insert
    void insert(Task task);

    //изменение старой задачи
    @Update
    void update(Task task);

    //Удаление задачи
    @Delete
    void delete(Task task);
}
