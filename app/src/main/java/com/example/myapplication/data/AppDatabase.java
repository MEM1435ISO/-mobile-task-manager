package com.example.myapplication.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myapplication.model.Note;

@Database(entities = {Note.class}, version = 2, exportSchema = false) // 2 version IO
public abstract class AppDatabase extends RoomDatabase {

    public abstract NoteDao noteDao();
}
