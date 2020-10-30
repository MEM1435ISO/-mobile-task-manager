package com.example.myapplication.screen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.App;
import com.example.myapplication.model.Note;

import java.util.List;

public class MainViewModel extends ViewModel {
    private LiveData<List<Note>> noteLiveData = App.getInstance().getNoteDao().getAllLiveData();

    public LiveData<List<Note>> getNoteLiveData(){
        return noteLiveData;
    }
}
