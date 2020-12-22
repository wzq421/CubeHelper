package com.example.myapplication.room.Repository

import androidx.annotation.WorkerThread
import com.example.myapplication.room.Dao.UserInfoDao
import com.example.myapplication.room.Entity.UserInfo
import java.util.concurrent.Flow

class UserInfoRepository(private val userInfoDao:UserInfoDao) {
fun login(userName:String) : kotlinx.coroutines.flow.Flow<UserInfo> {
    val userInfo = userInfoDao.getUserInfoByUserName(userName)
    return userInfo
}
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun register(userInfo:UserInfo){
        userInfoDao.insert(userInfo)
    }
}