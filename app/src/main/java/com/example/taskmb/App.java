package com.example.taskmb;

import android.app.Application;

import androidx.room.Room;

import data.AddDataBase;
import data.TaskDao;

public class App extends Application {

    private AddDataBase dataBase;
    private TaskDao taskDao;

    private static App insance;

    public static App getInstance(){
        return insance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        insance = this;

        dataBase = Room.databaseBuilder(getApplicationContext(),
                AddDataBase.class,  "mtm-db")
                .allowMainThreadQueries()
                .build();
        taskDao  = dataBase.noteDao();
    }

    public AddDataBase getDataBase(){
        return dataBase;
    }

    public void setDataBase (AddDataBase dataBase){
        this.dataBase = dataBase;
    }

    public TaskDao getTaskDao(){
       return taskDao;
    }

    public void setTaskDao (TaskDao taskDao){
        this.taskDao = taskDao;
    }
}
