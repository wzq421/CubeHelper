package com.example.myapplication.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.room.Entity.UserInfo

@Database(entities = [UserInfo::class],version = 1)
abstract class MyRoomDatabase : RoomDatabase() {

}