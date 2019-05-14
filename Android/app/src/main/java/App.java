import android.app.Application;

import androidx.room.Room;

import com.hitim.android.itstime.R;
import com.hitim.android.itstime.TaskDataBase;

public class App extends Application {

    public static App instance;

    private TaskDataBase dataBase;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        dataBase = Room.databaseBuilder(this, TaskDataBase.class,getString(R.string.database_name)).build();
    }

    public static App getInstance(){
        return instance;
    }

    public TaskDataBase getDataBase(){
        return dataBase;
    }
}
