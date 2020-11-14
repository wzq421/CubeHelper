package com.example.myapplication.callback;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import me.goldze.mvvmhabit.base.BaseViewModel;

public class SharedViewModel extends ViewModel {
    private volatile static SharedViewModel sharedViewModel;

    private SharedViewModel() {
    }

    public static SharedViewModel getSharedViewModel() {
        if (sharedViewModel == null) {
            synchronized (SharedViewModel.class) {
                if (sharedViewModel == null) {
                    sharedViewModel = new SharedViewModel();
                }
            }
        }
        return sharedViewModel;
    }

    private MutableLiveData<Boolean> toTimer = new MutableLiveData<>();

    public MutableLiveData<Boolean> isToTimer() {
        if (toTimer == null) {
            toTimer = new MutableLiveData<>(false);
        }
        return toTimer;
    }
}

