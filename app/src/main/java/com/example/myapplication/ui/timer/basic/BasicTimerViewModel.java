package com.example.myapplication.ui.timer.basic;

import android.app.Application;

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
import me.goldze.mvvmhabit.utils.ToastUtils;

public class BasicTimerViewModel extends BaseViewModel {
    public ShufflingFormulation mShufflingFormulation=ShufflingFormulation.getShufflingFormulation();
    public BasicTimerViewModel(@NonNull Application application) {
        super(application);
    }
    private Disposable mNewFormulation;
    public ObservableField<String> formulation=new ObservableField<>(mShufflingFormulation.formulation);
    public ObservableField<String > newCountdown=new ObservableField<>();
    public MutableLiveData<Boolean> isShowEdit=new MutableLiveData<>(false);
    public BindingCommand changeCountdown=new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //mainActivityViewModel
            if(!isNumber(newCountdown.get())){
                ToastUtils.showShort("请输入数字");
                return;
            }
            isShowEdit.setValue(false);
            RxBus.getDefault().postSticky(new Get_Countdown_Time(Integer.parseInt(newCountdown.get())));
        }
    });
    public BindingCommand getShufflingFormulation=new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            mShufflingFormulation.getFormulation();
        }
    });
    public BindingCommand showEdit=new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            isShowEdit.setValue(!isShowEdit.getValue());
        }
    });
    //判断String是不是数字
    private static boolean isNumber(String str){
        if(str==null)return false;
        String reg = "^[0-9]+(.[0-9]+)?$";
        return str.matches(reg);
    }

    @Override
    public void registerRxBus() {
        super.registerRxBus();
        //得到新打乱公式回调
        mNewFormulation=RxBus.getDefault().toObservable(New_Formulation .class).subscribe(new Consumer<New_Formulation>() {
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
