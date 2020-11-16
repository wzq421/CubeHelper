package com.example.myapplication.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.BR;
import com.example.myapplication.MainActivityViewModel;
import com.example.myapplication.R;
import com.example.myapplication.bean.MainItemBean;
import com.example.myapplication.bean.UserInfo;
import com.example.myapplication.bean.rxbus.Grade_Change;
import com.example.myapplication.bean.rxbus.Is_Login;
import com.example.myapplication.bean.rxbus.New_Formulation;
import com.example.myapplication.utils.ShufflingFormulation;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.bus.RxSubscriptions;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;


public class MainViewModel extends BaseViewModel {
    public ShufflingFormulation mShufflingFormulation=ShufflingFormulation.getShufflingFormulation();
    private Disposable mGradeChange;
    private Disposable mIsLogin;
    private Disposable mNewFormulation;
    public ItemBinding<MainItemViewModel> itemBinding=ItemBinding.of(BR.viewModel, R.layout.item_main);
    public ObservableList<MainItemViewModel> items=new ObservableArrayList<>();
    public ObservableField<String> formulation=new ObservableField<>(mShufflingFormulation.formulation);
    public MutableLiveData<MainItemBean> newGrade=new MutableLiveData<>();
    public MutableLiveData<Boolean> showAlertDialog=new MutableLiveData<>(false);
    public MutableLiveData<Boolean> isLogin=new MutableLiveData<>();
    public MutableLiveData<Grade_Change> gradeChange=new MutableLiveData<>();
    public MutableLiveData<Boolean> showDelete=new MutableLiveData<>(false);
    public MainViewModel(@NonNull Application application) {
        super(application);
    }
    //打开侧滑栏
    public BindingCommand menuClick=new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Messenger.getDefault().sendNoMsg(MainActivityViewModel.TOKEN_MAINACTIVITYVIEWMODEL_OPENDRAWER);
        }
    });
    public BindingCommand getNewFormulation=new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            mShufflingFormulation.getFormulation();
        }
    });
    public BindingCommand showHelper=new BindingCommand(new BindingAction() {
        @Override
        public void call() {
           showAlertDialog.setValue(true);
        }
    });
    //模拟登录
    public BindingCommand login=new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            RxBus.getDefault().post(new UserInfo("13:02s","汪自骞"));
            RxBus.getDefault().post(new Is_Login(true));
            items.clear();
            isLogin.setValue(true);
            //模拟加载数据
            items.add(new MainItemViewModel(MainViewModel.this,new MainItemBean("00:14:02",0)));
            for(int i=0;i<10;i++){
                items.add(new MainItemViewModel(MainViewModel.this,new MainItemBean("00:15:0"+i,0)));
            }
        }
    });
    //显示删除按钮
    public BindingCommand delete=new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if(items.size()==0) {
                ToastUtils.showShort("成绩已经清空");
                return;
            }
            showDelete.setValue(!showDelete.getValue());
        }
    });
    //注册计时页面的观察者
    @Override
    public void registerRxBus() {
        super.registerRxBus();
        mIsLogin=RxBus.getDefault().toObservable(Is_Login.class).subscribe(new Consumer<Is_Login>() {
            @Override
            public void accept(Is_Login is_login) throws Exception {
               isLogin.setValue(is_login.isLogin());
            }
        });
        //得到timer页面关于grade的变更，timerviewmodel
        mGradeChange=RxBus.getDefault().toObservable(Grade_Change.class).subscribe(new Consumer<Grade_Change>() {
            @Override
            public void accept(Grade_Change grade_change) throws Exception {
                if(grade_change.getAction()==1){
                    items.add(0,new MainItemViewModel(MainViewModel.this,new MainItemBean(grade_change.getGrade(),0)));
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
        //得到新打乱公式回调
        mNewFormulation=RxBus.getDefault().toObservable(New_Formulation.class).subscribe(new Consumer<New_Formulation>() {
            @Override
            public void accept(New_Formulation new_formulation) throws Exception {
                formulation.set(mShufflingFormulation.formulation);
            }
        });
        RxSubscriptions.add(mNewFormulation);
        RxSubscriptions.add(mGradeChange);
        RxSubscriptions.add(mIsLogin);
    }
    @Override
    public void removeRxBus() {
        super.removeRxBus();
        RxSubscriptions.remove(mNewFormulation);
        RxSubscriptions.remove(mGradeChange);
        RxSubscriptions.remove(mIsLogin);
    }

}
