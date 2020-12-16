package com.example.myapplication.room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class UserRepository {
    private UserDao mUserDao;
    private LiveData<List<User>> mAllUsers;
    public UserRepository(Application application){
UserRoomDatabase db=UserRoomDatabase.getDatabase(application);
mUserDao=db.userDao();
mAllUsers=mUserDao.getUsers();
    }
public LiveData<List<User>> getAllUsers(){return mAllUsers;}
   public void insert(User user) {
        UserRoomDatabase.databaseWriteExecutor.execute(() -> {
            mUserDao.insert(user);
        });
    }
}
