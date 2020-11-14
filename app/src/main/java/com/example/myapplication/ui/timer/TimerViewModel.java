package com.example.myapplication.ui.timer;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.bean.rxbus.Get_Countdown_Time;
import com.example.myapplication.bean.rxbus.Go_To_Timing;
import com.example.myapplication.bean.rxbus.New_Countdown_Time;
import com.example.myapplication.ui.timing.TimingActivity;
import com.example.myapplication.ui.timing.TimingViewModel;
import com.example.myapplication.utils.ShufflingFormulation;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.bus.RxSubscriptions;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class TimerViewModel extends BaseViewModel {
    private Disposable mGetCountDown;
    private int countdownTime=15;
    public ShufflingFormulation mShufflingFormulation=ShufflingFormulation.getShufflingFormulation();
    public TimerViewModel(@NonNull Application application) {
        super(application);
    }
    public ObservableField<String > newCountdown=new ObservableField<>();
    public MutableLiveData<String> countdown=new MutableLiveData<>("此处显示倒计时"+countdownTime+"s");
  public   BindingCommand goBack=new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            onBackPressed();
        }
    });
  public   BindingCommand changeCountdown=new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //mainActivityViewModel
            if(!isNumber(newCountdown.get())){
                ToastUtils.showShort("请输入数字");
                return;
            }
            RxBus.getDefault().post(new New_Countdown_Time(newCountdown.get()));
            countdown.postValue("此处显示倒计时"+newCountdown.get()+"s");
        }
    });
  public   BindingCommand goToTimingDirectly=new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            goToTiming();
        }
    });
  public BindingCommand startCountdown=new BindingCommand(new BindingAction() {
      @Override
      public void call() {
          ToastUtils.showShort("程序员暂时肝不动了，这个功能和主页面一样");
      }
  });

    @Override
    public void registerRxBus() {
        super.registerRxBus();
        mGetCountDown=RxBus.getDefault().toObservableSticky(Get_Countdown_Time.class).subscribe(new Consumer<Get_Countdown_Time>() {
            @Override
            public void accept(Get_Countdown_Time get_countdown_time) throws Exception {
                countdownTime=get_countdown_time.getGetCountdownTime();
            }
        });
        RxSubscriptions.add(mGetCountDown);
    }

    @Override
    public void removeRxBus() {
        super.removeRxBus();
        RxSubscriptions.remove(mGetCountDown);
    }

    public void goToTiming(){
        RxBus.getDefault().postSticky(new Go_To_Timing(1));
        startActivity(TimingActivity.class);
    }
    //判断String是不是数字
    private static boolean isNumber(String str){
        if(str==null)return false;
        String reg = "^[0-9]+(.[0-9]+)?$";
        return str.matches(reg);
    }
}
