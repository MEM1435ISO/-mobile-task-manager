package data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.taskmb.Task;

import java.util.List;

@Dao
public interface TaskDao {
    // слой запросов на взаисодействие с базой данных
    @Query("SELECT * FROM Task")
    List<Task> getAll();

    @Query("SELECT * FROM Task WHERE uniqueId IN (:userIds)")
    List<Task> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM Task WHERE uniqueId = :uniqueId LIMIT 1")
    Task findById(int uniqueId);
    Task findById(String first, String last);

    @Insert/*(onConflict = OnConflictStrategy.REPLACE)*/
    void insert(Task task);

    @Update
    void update(Task task);

    @Insert
    void delete(Task task);

    LiveData<List<Task>> getAllLiveData();
}
