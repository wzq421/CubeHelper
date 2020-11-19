package com.example.myapplication.ui.timer.quick;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.swipepanel.SwipePanel;
import com.example.myapplication.R;
import com.example.myapplication.bean.rxbus.Grade_Change;
import com.example.myapplication.databinding.FragmentQuickTimerBinding;

import me.goldze.mvvmhabit.BR;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class QuickTimerFragment extends BaseFragment<FragmentQuickTimerBinding,QuickTimerViewModel> {
    private View container;
    private int mContainerHeight;
    private Boolean save=true;
    private int flag=0;
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_quick_timer;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        container=binding.quickTimer;
        mContainerHeight=container.getHeight();
        initContainerTouch();
    }
    public void initContainerTouch(){
        container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_UP) {
                  startChronometer();
                }
                return true;
            }
        });
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.isSave.observe(this,aBoolean -> {
            if(!aBoolean){
                save=false;
                reset();
                save=true;
            }
        });
    }
    public void startChronometer(){
        if (flag == 0) {
            binding.shufflingFormulation.setVisibility(View.INVISIBLE);
            //防止计时出错在开始的时候再重新设置时间
            binding.myChronometer.setBase(SystemClock.elapsedRealtime());
            binding.myChronometer.start();
            flag=1;
        } else if(flag==1){
            binding.myChronometer.stop();
            binding.notSave.setVisibility(View.VISIBLE);
            flag=2;
        }else {
            if(save) {
                Grade_Change grade_msg = new Grade_Change(1, binding.myChronometer.getText().toString(), 0);
                RxBus.getDefault().post(grade_msg);
            }else {
                save=true;
            }
           reset();
        }
    }
    public void reset(){
        viewModel.mShufflingFormulation.getFormulation();
        binding.notSave.setVisibility(View.INVISIBLE);
        binding.shufflingFormulation.setVisibility(View.VISIBLE);
        binding.myChronometer.setBase(SystemClock.elapsedRealtime());
        flag=0;
    }
}