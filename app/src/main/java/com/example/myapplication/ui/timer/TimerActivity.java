package com.example.myapplication.ui.timer;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.blankj.swipepanel.SwipePanel;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityTimerBinding;
import com.example.myapplication.utils.ScreenUtils;

import me.goldze.mvvmhabit.BR;
import me.goldze.mvvmhabit.base.BaseActivity;

public class TimerActivity extends BaseActivity<ActivityTimerBinding, TimerViewModel> {
    private int start_x;
    private int start_y;
    private View container;
    private SwipePanel mSwipePanel;
    private int mScreenWidth;
    private int mScreenHeight;
    private int mFrgHeight;
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_timer;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
    @Override
    public void initViewObservable() {
        viewModel.isToQuick.observe(this,aBoolean -> {
            NavController nav=Navigation.findNavController(this,R.id.timer_fragment);
            if (aBoolean){
                if(nav.getCurrentDestination().getId()==R.id.quickTimerFragment){
                binding.countdownLinear.setVisibility(View.VISIBLE);
                binding.gotoTiming.setVisibility(View.VISIBLE);
                ConstraintLayout.LayoutParams l= (ConstraintLayout.LayoutParams) binding.frame.getLayoutParams();
                l.height=mFrgHeight*5/13;
                binding.frame.setLayoutParams(l);
                binding.changeFragment.setImageResource(R.drawable.quick_timer);
                binding.rv.setLayoutManager(new LinearLayoutManager(this));
                nav.navigate(R.id.action_quickTimerFragment_to_basicTimerFragment);}
            }else {
                if(nav.getCurrentDestination().getId()==R.id.basicTimerFragment){
                binding.countdownLinear.setVisibility(View.GONE);
                binding.gotoTiming.setVisibility(View.GONE);
                ConstraintLayout.LayoutParams l= (ConstraintLayout.LayoutParams) binding.frame.getLayoutParams();
                l.height=mFrgHeight;
                binding.frame.setLayoutParams(l);
                binding.changeFragment.setImageResource(R.drawable.backto_normal_timer);
                binding.rv.setLayoutManager(new GridLayoutManager(this,2));
                nav.navigate(R.id.action_basicTimerFragment_to_quickTimerFragment);}
            }
        });
        viewModel.gradeChange.observe(this,grade_change -> {
            if(grade_change.getAction()==-1){
                int position=grade_change.getState();
               viewModel.items.remove(position);
            }else if(grade_change.getAction()==1){
                binding.rv.getLayoutManager().scrollToPosition(0);
            }
        });
        viewModel.countdown.observe(this,s -> {
            binding.countdownTime.setText(s);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void initData() {
        super.initData();
        mScreenWidth=ScreenUtils.getScreenWidth();
        mScreenHeight=ScreenUtils.getScreenHeight();
        mFrgHeight= (int)((int)mScreenHeight*0.65);
        initSlideSlip();
        container=binding.timerContainer;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initSlideSlip(){
        mSwipePanel=binding.timerSwipePanel;
        mSwipePanel.setLeftEdgeSize((int)(mScreenWidth*6/10));
        mSwipePanel.setOnFullSwipeListener(new SwipePanel.OnFullSwipeListener() {
            @Override
            public void onFullSwipe(int direction) {
                mSwipePanel.close(direction);
                finish();
            }
        });
    }
}