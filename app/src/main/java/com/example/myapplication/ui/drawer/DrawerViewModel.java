package com.example.myapplication.ui.drawer;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.BR;
import com.example.myapplication.R;
import com.example.myapplication.bean.UserInfo;
import com.example.myapplication.bean.rxbus.Is_Login;

import java.math.BigInteger;
import java.util.Observable;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.bus.RxSubscriptions;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class DrawerViewModel extends BaseViewModel {
    private Disposable mLoginData;
    public DrawerViewModel(@NonNull Application application) {
        super(application);
    }
    public ItemBinding<DrawerItemViewModel> itemBinding=ItemBinding.of(BR.viewModel, R.layout.item_drawer);
    public ObservableList<DrawerItemViewModel> items=new ObservableArrayList<>();
    public MutableLiveData<Boolean> newRecord=new MutableLiveData<>();
    public BindingCommand logout=new BindingCommand(new BindingAction() {
        @Override
        public void call() {
           RxBus.getDefault().post(new Is_Login(false));
            ToastUtils.showShort("您已退出");
        }
    });
    @Override
    public void registerRxBus() {
        super.registerRxBus();
        //Mainviewmodel
        mLoginData= RxBus.getDefault().toObservable(UserInfo.class).subscribe(new Consumer<UserInfo>() {
            @Override
            public void accept(UserInfo userInfo) throws Exception {
                items.clear();
                items.add(new DrawerItemViewModel(DrawerViewModel.this,R.drawable.drawer_mine,userInfo.getName()));
                items.add(new DrawerItemViewModel(DrawerViewModel.this,R.drawable.grade_1,userInfo.getRecord()));
            }
        });
        RxSubscriptions.add(mLoginData);
    }

    @Override
    public void removeRxBus() {
        super.removeRxBus();
        RxSubscriptions.remove(mLoginData);
    }
}
