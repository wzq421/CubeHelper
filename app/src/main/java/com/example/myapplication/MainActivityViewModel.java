package com.example.myapplication;

import android.app.Application;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.bean.rxbus.Get_Countdown_Time;
import com.example.myapplication.bean.rxbus.Go_To_Timing;
import com.example.myapplication.bean.rxbus.Is_Login;
import com.example.myapplication.bean.rxbus.MainFrg_To_MainAct;
import com.example.myapplication.bean.rxbus.New_Countdown_Time;
import com.example.myapplication.ui.timer.TimerViewModel;
import com.example.myapplication.ui.timing.TimingActivity;
import com.example.myapplication.ui.timing.TimingViewModel;

import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.bus.RxSubscriptions;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class MainActivityViewModel extends BaseViewModel {
    private Disposable mNewCountDown;
    private Disposable mIsLogin;
    public  int  countDownTime=15;
    private CountDownTimer timer;
    public static String TOKEN_MAINACTIVITYVIEWMODEL_OPENDRAWER="token_mainactivity_opendrawer";
    public static final String TOKEN_MAINACTIVITYVIEWMODEL_TOTIMER = "token_mainactivityviewmodel_totimer";
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        setCountDownTimer(countDownTime);
        Messenger.getDefault().register(this, MainActivityViewModel.TOKEN_MAINACTIVITYVIEWMODEL_OPENDRAWER, new BindingAction() {
            @Override
            public void call() {
                if(allowDrawerOpen.getValue())openDrawer.setValue(true);
                else ToastUtils.showShort("您还未登录，请登录");
            }
        });
        //打开timer并把计时数传给timer页面
        Messenger.getDefault().register(this, MainActivityViewModel.TOKEN_MAINACTIVITYVIEWMODEL_TOTIMER, new BindingAction() {
            @Override
            public void call() {
                toTimer.setValue(true);
            }
        });
    }
    public  MutableLiveData<Boolean> openDrawer=new MutableLiveData<>(false);
    public  MutableLiveData<Boolean> allowDrawerOpen=new MutableLiveData<>(false);
    public MutableLiveData<Boolean> drawerOpened=new MutableLiveData<>(false);
    public MutableLiveData<Boolean> toTimer=new MutableLiveData<>(false);
    public MutableLiveData<String> showTime=new MutableLiveData<>("计时");
    public MutableLiveData<Boolean> isToTimer(){
        if(toTimer==null){
            toTimer=new MutableLiveData<>(false);
        }
        return toTimer;
    }

    //注册rxbus和移除
    @Override
    public void registerRxBus() {
        super.registerRxBus();
        //更改倒计时时间
        mNewCountDown=RxBus.getDefault().toObservable(New_Countdown_Time.class).subscribe(new Consumer<New_Countdown_Time>() {
            @Override
            public void accept(New_Countdown_Time new_countdown_time) throws Exception {
                countDownTime=Integer.parseInt(new_countdown_time.getTime());
                setCountDownTimer(countDownTime);
            }
        });
        //是否登录mainviewmodel
        mIsLogin=RxBus.getDefault().toObservable(Is_Login.class).subscribe(new Consumer<Is_Login>() {
            @Override
            public void accept(Is_Login is_login) throws Exception {
                allowDrawerOpen.setValue(is_login.isLogin());
            }
        });
        RxSubscriptions.add(mIsLogin);
        RxSubscriptions.add(mNewCountDown);

    }
    @Override
    public void removeRxBus() {
        super.removeRxBus();
        RxSubscriptions.remove(mNewCountDown);
        RxSubscriptions.remove(mIsLogin);
    }
    private int flag=0;
    //点击计时
    public View.OnClickListener clickToTiming=new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(flag==0){
                showTime.setValue("现在你有"+countDownTime+"秒时间观察魔方");
                        timer.start();
                        flag++;
               }else {
                flag=0;
                goToTiming();
            }
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        showTime.setValue("计时");
    }

    @Override
    public void onStop() {
        super.onStop();
        timer.cancel();
        flag=0;
    }

    //设置计时器
    public void setCountDownTimer(int countDownTime){
        timer = new CountDownTimer((countDownTime+1)*1000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                if((millisUntilFinished/1000)<countDownTime) {
                    showTime.setValue(millisUntilFinished / 1000 + "s");
                }
            }

            @Override
            public void onFinish() {
                showTime.setValue("开始");
                flag=0;
                goToTiming();
            }
        };
    }
    //进入计时页面
public void goToTiming(){
    timer.cancel();
    RxBus.getDefault().postSticky(new Go_To_Timing(1));
    startActivity(TimingActivity.class);
}
    @Override
    protected void onCleared() {
        super.onCleared();
        timer.cancel();
    }
}
