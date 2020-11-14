package com.example.myapplication.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.BR;
import com.example.myapplication.MainActivityViewModel;
import com.example.myapplication.R;
import com.example.myapplication.bean.MainItemBean;
import com.example.myapplication.bean.UserInfo;
import com.example.myapplication.bean.rxbus.Is_Login;
import com.example.myapplication.callback.SharedViewModel;
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
    private Disposable mGetGrade;
    private Disposable mIsLogin;
    public MainViewModel(@NonNull Application application) {
        super(application);
    }
    public ItemBinding<MainItemViewModel> itemBinding=ItemBinding.of(BR.viewModel, R.layout.item_main);
    public ObservableList<MainItemViewModel> items=new ObservableArrayList<>();
    public ObservableField<String> formulation=new ObservableField<>(mShufflingFormulation.formulation);
    public MutableLiveData<MainItemBean> newGrade=new MutableLiveData<>();
    public MutableLiveData<Boolean> showAlertDialog=new MutableLiveData<>(false);
    public MutableLiveData<Boolean> isLogin=new MutableLiveData<>();
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
            formulation.set(mShufflingFormulation.formulation);
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
            items.add(new MainItemViewModel(MainViewModel.this,new MainItemBean("14.02",0)));
            for(int i=0;i<10;i++){
                items.add(new MainItemViewModel(MainViewModel.this,new MainItemBean("15.0"+i,0)));
            }
        }
    });
    //注册计时页面的观察者
    @Override
    public void registerRxBus() {
        super.registerRxBus();
        mGetGrade= RxBus.getDefault().toObservable(MainItemBean.class).subscribe(new Consumer<MainItemBean>() {
            @Override
            public void accept(MainItemBean mainItemBean) throws Exception {
               items.add(0,new MainItemViewModel(MainViewModel.this,mainItemBean));
            }
        });
        mIsLogin=RxBus.getDefault().toObservable(Is_Login.class).subscribe(new Consumer<Is_Login>() {
            @Override
            public void accept(Is_Login is_login) throws Exception {
               isLogin.setValue(is_login.isLogin());
            }
        });
        RxSubscriptions.add(mIsLogin);
        RxSubscriptions.add(mGetGrade);
    }
    @Override
    public void removeRxBus() {
        super.removeRxBus();
        RxSubscriptions.remove(mGetGrade);
        RxSubscriptions.remove(mIsLogin);
    }

}
