package com.example.taskmb;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Task implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int uniqueId;

    @ColumnInfo(name = "text")
    public String text;

    @ColumnInfo(name = "time")
    public long taskTime;

    @ColumnInfo(name = "ready")
    public boolean ready;

    public Task(){
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task note = (Task) o;

        if (uniqueId != note.uniqueId) return false;
        if (taskTime != note.taskTime) return false;
        if (ready != note.ready) return false;
        return text != null ? text.equals(note.text) : note.text == null;
    }

    @Override
    public int hashCode() {
        int result = uniqueId;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (int) (taskTime ^ (taskTime >>> 32));
        result = 31 * result + (ready ? 1 : 0);
        return result;
    }

    protected Task(Parcel in) {
        uniqueId = in.readInt();
        text = in.readString();
        taskTime = in.readLong();
        ready = in.readByte() != 0;
    }

    @Override
    public void writeToParcel (Parcel dest, int flags){
        dest.writeInt(uniqueId);
        dest.writeString(text);
        dest.writeLong(taskTime);
        dest.writeByte((byte) (ready ? 1 : 0));
    }

    @Override
    public int describeContents(){
        return 0;
    }

    public static final Creator<Task> CREATOR = new Creator<Task>(){
        @Override
        public Task createFromParcel(Parcel in){
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size){
            return new Task[size];
        }

    };
}