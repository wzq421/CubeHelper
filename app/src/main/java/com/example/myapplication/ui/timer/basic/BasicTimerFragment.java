package com.example.myapplication.ui.timer.basic;

import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentBasicTimerBinding;

import me.goldze.mvvmhabit.BR;
import me.goldze.mvvmhabit.base.BaseFragment;

public class BasicTimerFragment extends BaseFragment<FragmentBasicTimerBinding, BasicTimerViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_basic_timer;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.isShowEdit.observe(this,aBoolean -> {
            if(aBoolean) {
                binding.textNewCountdown.setVisibility(View.VISIBLE);
                binding.btnNewCountdown.setVisibility(View.VISIBLE);
            }else {
                binding.textNewCountdown.setVisibility(View.INVISIBLE);
                binding.btnNewCountdown.setVisibility(View.INVISIBLE);
            }
        });
    }
}