package com.example.myapplication.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user_table ")
LiveData<List<User>> getUsers();
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User user);
   @Query("DELETE FROM user_table")
    void deleteAll();
}
