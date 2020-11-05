package com.example.times;

import android.app.Application;

import androidx.room.Room;

import com.example.times.data.AppDatabase;
import com.example.times.data.NoteDao;

public class App extends Application {

    private AppDatabase database;
    private NoteDao noteDao;

    private static App insance;

    public static App getInstance() {
        return insance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        insance = this;

        database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "db")
                .allowMainThreadQueries()
                .build();
        noteDao = database.noteDao();
    }

    public AppDatabase getDatabase(AppDatabase database) {
        return database;
    }

    public void setDatabase(AppDatabase database) {
        this.database = database;
    }

    public NoteDao getNoteDao() {
        return noteDao;
    }

    public void setNoteDao(NoteDao noteDao) {
        this.noteDao = noteDao;
    }
}