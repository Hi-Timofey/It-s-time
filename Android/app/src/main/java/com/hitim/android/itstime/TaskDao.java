package com.hitim.android.itstime;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**{@link TaskDao} - интерфейс, реализующий методы
 * взаимодействия с базой данных от {@link androidx.room.Room}
 * Через него происходят запросы.
 * Получить {@link Dao} для работы с базойт данных можно
 * только через класс {@link TaskDataBase}
 */

@Dao
public interface TaskDao {
    //Получить все задачи
    @Query("SELECT * FROM task")
    List<Task> getAllTasks();

    //Находит задачу по id
    @Query("SELECT * FROM task WHERE id = :id")
    Task getById(int id);
    //Поиск по имени
    @Query("SELECT * FROM task WHERE name LIKE :name")
    Task getByName(String name);

    //Получить все задачи с опр. сферы
    @Query("SELECT * FROM task WHERE sphere LIKE :sphereStr")
    List<Task> getAllTasksWithSphere(String sphereStr);

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
