package com.example.myapplication.room.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.room.Entity.UserInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface UserInfoDao {
    @Query("SELECT * FROM user_info WHERE userName==:userName ")
    fun getUserInfoByUserName(userName:String): Flow<UserInfo>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userInfo:UserInfo)
    @Query("DELETE FROM user_info")
    suspend fun deleteAll()
}