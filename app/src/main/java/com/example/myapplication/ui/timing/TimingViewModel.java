package com.example.myapplication.ui.timing;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.bean.rxbus.Go_To_Timing;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.bus.RxSubscriptions;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class TimingViewModel extends BaseViewModel {
    private static Disposable mGoToTimingSubscription;
    public TimingViewModel(@NonNull Application application) {
        super(application);
    }
    public MutableLiveData<Go_To_Timing> goToTiming=new MutableLiveData<>();
    public MutableLiveData<String> timingText=new MutableLiveData<>("点击停止计时");
    public MutableLiveData<Boolean> isSave=new MutableLiveData<>(true);
    public  BindingCommand notSaveGrade=new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            isSave.setValue(false);
        }
    });

    @Override
    public void registerRxBus() {
        super.registerRxBus();
        mGoToTimingSubscription=RxBus.getDefault().toObservableSticky(Go_To_Timing.class).subscribe(new Consumer<Go_To_Timing>() {
            @Override
            public void accept(Go_To_Timing go_to_timing) throws Exception {
                goToTiming.postValue(go_to_timing);
            }
        });
        RxSubscriptions.add(mGoToTimingSubscription);
    }

    @Override
    public void removeRxBus() {
        super.removeRxBus();
        RxSubscriptions.remove(mGoToTimingSubscription);
    }
}
