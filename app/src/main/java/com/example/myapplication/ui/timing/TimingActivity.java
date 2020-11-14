package com.example.myapplication.ui.timing;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import android.animation.Animator;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.bean.MainItemBean;
import com.example.myapplication.databinding.ActivityTimingBinding;
import com.example.myapplication.utils.CircularRevealUtil;

import me.goldze.mvvmhabit.BR;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class TimingActivity extends BaseActivity<ActivityTimingBinding,TimingViewModel> {
    private View container;
    private int flag=0;
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_timing;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.goToTiming.observe(this,go_to_timing -> {
            binding.myChronometer.start();
            Animator animator = CircularRevealUtil.createRevealAnimator(false,500,binding.myChronometer.getWidth()/2,(int)binding.myChronometer.getY(),container.getWidth(),container.getHeight(),container);
            animator.start();
        });
    }

    @Override
    public void initData() {
        super.initData();
        container=binding.timingContainer;
        initContainerTouch();
    }
    public void initContainerTouch(){
        container.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (flag == 0) {
                    binding.myChronometer.stop();
                    viewModel.timingText.setValue("计时停止,点击返回页面");
                    MainItemBean grade_msg=new MainItemBean(binding.myChronometer.getText().toString(),0);
                    RxBus.getDefault().post(grade_msg);
                    flag++;
                }else if(flag==1){
                    animBack();
                    return true;
                }
                return false;
            }
        });
    }
    //返回反转动画以及返回动画监听设置
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void animBack(){
        Animator animator = CircularRevealUtil.createRevealAnimator(true,500,binding.myChronometer.getWidth()/2,(int)binding.myChronometer.getY(),container.getWidth(),container.getHeight(),container);
        animator.addListener(animatorListener);
        animator.start();
    }
    private Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
        }

        @Override
        public void onAnimationEnd(Animator animation) {
           container.setVisibility(View.INVISIBLE);
            finish();
        }

        @Override
        public void onAnimationCancel(Animator animation) {
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
        }
    };
}