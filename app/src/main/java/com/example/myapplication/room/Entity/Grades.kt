package com.example.myapplication.room.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "grades")
data class Grades(@PrimaryKey @ColumnInfo(name = "userName") val userName: String,
                  @ColumnInfo(name = "grades")val grades:List<String>?)