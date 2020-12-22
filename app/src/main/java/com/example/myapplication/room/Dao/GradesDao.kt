package com.example.myapplication.room.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.room.Entity.Grades
import com.example.myapplication.room.Entity.UserInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface GradesDao {
    @Query("SELECT grades FROM grades WHERE userName==:userName ")
    fun getUserInfoByUserName(userName:String): Flow<List<String>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(grades: Grades)

}