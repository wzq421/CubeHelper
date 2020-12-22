package com.example.myapplication.room.Repository

import com.example.myapplication.room.Dao.GradesDao
import com.example.myapplication.room.Entity.Grades

class GradesRepository(private val gradesDao:GradesDao) {
    fun getGrades(userName:String){
        gradesDao.getGradesByUserName(userName)
    }
    suspend fun insertGrades(grades:Grades){
        gradesDao.insert(grades)
    }
}