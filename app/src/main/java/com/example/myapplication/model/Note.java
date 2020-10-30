package com.example.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class Note implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "text")
    public String text;

    @ColumnInfo(name = "tag")
    public String tag;

    @ColumnInfo(name = "timestamp")
    public long timestamp;

    @ColumnInfo(name = "done")
    public boolean done;

    public Note(){

    }

    protected Note(Parcel in) {
        uid = in.readInt();
        text = in.readString();
        tag = in.readString();
        timestamp = in.readLong();
        done = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(uid);
        dest.writeString(text);
        dest.writeString(tag);
        dest.writeLong(timestamp);
        dest.writeByte((byte) (done ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Note)) return false;
        Note note = (Note) o;
        return uid == note.uid &&
                timestamp == note.timestamp &&
                done == note.done &&
                Objects.equals(text, note.text) &&
                Objects.equals(tag, note.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, text, tag, timestamp, done);
    }


}
