package com.example.myapplication.ui.main;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.BR;
import com.example.myapplication.MainActivityViewModel;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentMainBinding;
import com.example.myapplication.bean.rxbus.MainFrg_To_MainAct;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.bus.RxBus;

public class MainFragment extends BaseFragment<FragmentMainBinding,MainViewModel> {

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_main;
    }

    @Override
    public int initVariableId() {
        binding.setVariable(BR.click,new ClickProxy());
        return BR.viewModel;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.showAlertDialog.observe(this,aBoolean -> {
            if(aBoolean){
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setIcon(R.drawable.cube_icon);
                builder.setMessage(R.string.gesture_helper);
                builder.setPositiveButton("ok我学会了", null);
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
        viewModel.newGrade.observe(this,mainItemBean -> {
            viewModel.items.add(0,new MainItemViewModel(viewModel,mainItemBean));
        });
        viewModel.isLogin.observe(this,aBoolean -> {
            if(aBoolean){
                binding.loginSpace.setVisibility(View.GONE);
                binding.gradesRv.setVisibility(View.VISIBLE);
            }else {
                binding.loginSpace.setVisibility(View.VISIBLE);
                binding.gradesRv.setVisibility(View.GONE);
            }
        });
    }
//点击打开timer
    public class ClickProxy{
        public void clickToTimer(){
            MainFrg_To_MainAct msg=new MainFrg_To_MainAct(binding.iconToTimer.getWidth(),binding.iconToTimer.getHeight());
            RxBus.getDefault().post(msg);
            Messenger.getDefault().sendNoMsg(MainActivityViewModel.TOKEN_MAINACTIVITYVIEWMODEL_TOTIMER);
        }
}
}