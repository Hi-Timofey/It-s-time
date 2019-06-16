package com.hitim.android.itstime;

import android.app.Application;

import androidx.room.Room;

import com.crashlytics.android.Crashlytics;

/**
 * {@link App} создан для работы с {@link TaskDataBase}
 * в стиле SINGLETON: один экземпляр класса на всю программу
 */

public class App extends Application {

    private static App instance;

    private TaskDataBase dataBase;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Crashlytics.getInstance();
        instance = this;
        setTheme(R.style.BlueApplicationStyle_LightTheme);
        dataBase = Room.databaseBuilder(this, TaskDataBase.class, getString(R.string.database_name))
                .fallbackToDestructiveMigration()
                .build();
    }

    public synchronized TaskDataBase getDataBase() {
        return dataBase;
    }
}
