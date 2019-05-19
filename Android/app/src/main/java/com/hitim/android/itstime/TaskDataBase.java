package com.hitim.android.itstime;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class}, version = 3)
public abstract class TaskDataBase extends RoomDatabase {
    public abstract TaskDao getTaskDao();
}
