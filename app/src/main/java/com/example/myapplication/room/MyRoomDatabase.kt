package com.example.myapplication.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myapplication.room.Dao.GradesDao
import com.example.myapplication.room.Dao.UserInfoDao
import com.example.myapplication.room.Entity.Grades
import com.example.myapplication.room.Entity.UserInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [UserInfo::class,Grades::class],version = 1)
abstract class MyRoomDatabase : RoomDatabase() {
    abstract fun gradesDao():GradesDao
    abstract fun userInfoDao(): UserInfoDao
    companion object{
        @Volatile
        private var INSTANCE: MyRoomDatabase? = null
        fun getDatabase(
                context: Context,
                scope: CoroutineScope
        ):MyRoomDatabase{
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        MyRoomDatabase::class.java,
                        "word_database"
                )
                        // Wipes and rebuilds instead of migrating if no Migration object.
                        // Migration is not part of this codelab.
                        .fallbackToDestructiveMigration()
                        .addCallback(MyRoomDatabaseCallback(scope))
                        .build()
                INSTANCE = instance
                // return instance
                instance
        }
    }
        private class MyRoomDatabaseCallback(
                private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onCreate method to populate the database.
             */
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.userInfoDao(),database.gradesDao())
                    }
                }
            }
        }
        suspend fun populateDatabase(userInfoDao:UserInfoDao,gradesDao:GradesDao) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            gradesDao.deleteAll()
            userInfoDao.deleteAll()
            val userInfo1=UserInfo("12345","54321")
            val grades1=Grades("12345", listOf("00:15:32#0"))
            userInfoDao.insert(userInfo1)
            gradesDao.insert(grades1)
        }
    }
}