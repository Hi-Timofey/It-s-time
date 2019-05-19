package com.hitim.android.itstime;

import android.app.Application;

import androidx.room.Room;

/**
 * {@link App} создан для работы с {@link TaskDataBase}
 * в стиле SINGLETON: один экземпляр класса на всю программу
 */

public class App extends Application {

    public static App instance;

    private TaskDataBase dataBase;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        setTheme(R.style.BlueApplicationStyle_LightTheme);
        dataBase = Room.databaseBuilder(this, TaskDataBase.class, getString(R.string.database_name))
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public TaskDataBase getDataBase() {
        return dataBase;
    }
}
