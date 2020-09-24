package data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import  com.example.taskmb.Task;

@Database(entities = {Task.class}, version = 1, exportSchema = false)

public abstract class AddDataBase extends RoomDatabase {
    public abstract TaskDao noteDao();

}
