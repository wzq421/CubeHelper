package com.example.myapplication.ui.main;

import androidx.annotation.NonNull;

import com.example.myapplication.bean.MainItemBean;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class MainItemViewModel extends ItemViewModel<MainViewModel> {
    public MainItemBean mainItemBean;
    public MainItemViewModel(@NonNull MainViewModel viewModel, MainItemBean mainItemBean) {
        super(viewModel);
        this.mainItemBean = mainItemBean;
    }

    public BindingCommand itemClick=new BindingCommand((BindingAction)()->{
        if(mainItemBean.getState()==1){
            ToastUtils.showShort("The latest one");
        }else {
            ToastUtils.showShort("your grade is"+ mainItemBean.getGrade());
        }
    });

    public MainItemBean getMainItemBean() {
        return mainItemBean;
    }

    public void setMainItemBean(MainItemBean mainItemBean) {
        this.mainItemBean = mainItemBean;
    }
}
