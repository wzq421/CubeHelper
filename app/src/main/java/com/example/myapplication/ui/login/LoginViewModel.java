package com.example.myapplication.ui.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.room.User;
import com.example.myapplication.room.UserRepository;

import java.util.List;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class LoginViewModel extends BaseViewModel {
    public LoginViewModel(@NonNull Application application) {
        super(application);
        mRepository=new UserRepository(application);
        mUsers= mRepository.getAllUsers();
    }
    private UserRepository mRepository;
    private final LiveData<List<User>> mUsers;
    LiveData<List<User>> getAllWords() {
        return mUsers;
    }
    public BindingCommand showUsers=new BindingCommand(new BindingAction() {
        @Override
        public void call() {

        }
    });
}
