package com.example.myapplication.room.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_info")
data class UserInfo (@PrimaryKey @ColumnInfo(name = "userName") val userName: String,
                         @ColumnInfo(name="password")val password:String)