package com.example.times.screen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.times.App;
import com.example.times.model.Note;

import java.util.List;

public class MainViewModel extends ViewModel {
    private LiveData<List<Note>> noteLiveData = App.getInstance().getNoteDao().getAllLiveData();

    public LiveData<List<Note>> getNoteLiveData() {
        return noteLiveData;
    }
}
