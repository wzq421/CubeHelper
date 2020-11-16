package com.example.myapplication.ui.timer;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.BR;
import com.example.myapplication.R;
import com.example.myapplication.bean.MainItemBean;
import com.example.myapplication.bean.rxbus.Get_Countdown_Time;
import com.example.myapplication.bean.rxbus.Go_To_Timing;
import com.example.myapplication.bean.rxbus.Grade_Change;
import com.example.myapplication.bean.rxbus.New_Countdown_Time;
import com.example.myapplication.bean.rxbus.New_Formulation;
import com.example.myapplication.ui.main.MainItemViewModel;
import com.example.myapplication.ui.main.MainViewModel;
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
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class TimerViewModel extends BaseViewModel {
    private Disposable mGetCountDown;

    private Disposable mNewFormulation;
    private Disposable mGradeChange;
    private int countdownTime=15;
    public ShufflingFormulation mShufflingFormulation=ShufflingFormulation.getShufflingFormulation();
    public TimerViewModel(@NonNull Application application) {
        super(application);
    }
    public ItemBinding<TimerItemViewModel> itemBinding=ItemBinding.of(BR.viewModel, R.layout.item_timer);
    public ObservableList<TimerItemViewModel> items=new ObservableArrayList<>();
   public ObservableField<String> formulation=new ObservableField<>(mShufflingFormulation.formulation);
    public ObservableField<String > newCountdown=new ObservableField<>();
    public MutableLiveData<String> countdown=new MutableLiveData<>("此处显示倒计时"+countdownTime+"s");
    public MutableLiveData<Grade_Change> gradeChange=new MutableLiveData<>();
    public MutableLiveData<Boolean> isShowEdit=new MutableLiveData<>(false);
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
            isShowEdit.setValue(false);
            countdown.postValue("此处显示倒计时"+newCountdown.get()+"s");
        }
    });
  public BindingCommand getShufflingFormulation=new BindingCommand(new BindingAction() {
      @Override
      public void call() {
          mShufflingFormulation.getFormulation();
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
public BindingCommand showEdit=new BindingCommand(new BindingAction() {
    @Override
    public void call() {
        isShowEdit.setValue(!isShowEdit.getValue());
    }
});
    @Override
    public void registerRxBus() {
        super.registerRxBus();
        //得到主页面倒计时数，mainactivityviewmodel
        mGetCountDown=RxBus.getDefault().toObservableSticky(Get_Countdown_Time.class).subscribe(new Consumer<Get_Countdown_Time>() {
            @Override
            public void accept(Get_Countdown_Time get_countdown_time) throws Exception {
                countdownTime=get_countdown_time.getGetCountdownTime();
            }
        });
        //得到新打乱公式回调
        mNewFormulation=RxBus.getDefault().toObservable(New_Formulation.class).subscribe(new Consumer<New_Formulation>() {
            @Override
            public void accept(New_Formulation new_formulation) throws Exception {
              formulation.set(mShufflingFormulation.formulation);
            }
        });
        //得到timer页面关于grade的变更，timerviewmodel
        mGradeChange=RxBus.getDefault().toObservable(Grade_Change.class).subscribe(new Consumer<Grade_Change>() {
            @Override
            public void accept(Grade_Change grade_change) throws Exception {
                if(grade_change.getAction()==1){
                    items.add(0,new TimerItemViewModel(TimerViewModel.this,new MainItemBean(grade_change.getGrade(),0)));
                }else if(grade_change.getAction()==-1){
                    for(int i=0;i<items.size();i++){
                        if(items.get(i).getMainItemBean().getGrade()==grade_change.getGrade()&&items.get(i).getMainItemBean().getState()==grade_change.getState()) {
                            grade_change.setState(i);
                            break;
                        }
                    }
                }
                gradeChange.setValue(grade_change);
            }
        });
        RxSubscriptions.add(mGradeChange);
        RxSubscriptions.add(mNewFormulation);
        RxSubscriptions.add(mGetCountDown);
    }

    @Override
    public void removeRxBus() {
        super.removeRxBus();
        RxSubscriptions.remove(mGradeChange);
        RxSubscriptions.remove(mNewFormulation);
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
