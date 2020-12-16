package com.example.myapplication.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class User {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "userName")
    private String userName;
    @NonNull
    @ColumnInfo(name = "record")
    private String record;

    public User(String userName, String record) {
        this.userName = userName;
        this.record = record;
    }

    public String getUserName() {
        return userName;
    }


    public String getRecord() {
        return record;
    }

}
