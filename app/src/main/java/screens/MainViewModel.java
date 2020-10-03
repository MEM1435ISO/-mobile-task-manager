package screens;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.taskmb.App;
import com.example.taskmb.Task;

import java.util.List;

public class MainViewModel extends ViewModel {
    private LiveData <List<Task>> taskLiveData = App.getInstance().getTaskDao().getAllLiveData();

    public LiveData<List<Task>> getTaskLiveData(){
        return taskLiveData;
    }
}
