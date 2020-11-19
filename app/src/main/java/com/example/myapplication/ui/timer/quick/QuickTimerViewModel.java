package com.example.myapplication.ui.timer.quick;

import android.app.Application;
import android.os.CountDownTimer;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.bean.rxbus.Get_Countdown_Time;
import com.example.myapplication.bean.rxbus.New_Formulation;
import com.example.myapplication.utils.ShufflingFormulation;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.bus.RxSubscriptions;

public class QuickTimerViewModel extends BaseViewModel {
    private Disposable mNewFormulation;
    public ShufflingFormulation mShufflingFormulation=ShufflingFormulation.getShufflingFormulation();
    public MutableLiveData<Boolean> isSave=new MutableLiveData<>(true);
    public ObservableField<String> formulation=new ObservableField<>(mShufflingFormulation.formulation);
    public QuickTimerViewModel(@NonNull Application application) {
        super(application);
    }
    public BindingCommand notSave=new BindingCommand(new BindingAction() {
        @Override
        public void call() {
       isSave.setValue(false);
        }
    });
    public BindingCommand getShufflingFormulation=new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            mShufflingFormulation.getFormulation();
        }
    });
    @Override
    public void registerRxBus() {
        super.registerRxBus();
        //得到新打乱公式回调
        mNewFormulation=RxBus.getDefault().toObservable(New_Formulation.class).subscribe(new Consumer<New_Formulation>() {
            @Override
            public void accept(New_Formulation new_formulation) throws Exception {
                formulation.set(mShufflingFormulation.formulation);
            }
        });
        RxSubscriptions.add(mNewFormulation);
    }

    @Override
    public void removeRxBus() {
        super.removeRxBus();
        RxSubscriptions.remove(mNewFormulation);
    }

}
