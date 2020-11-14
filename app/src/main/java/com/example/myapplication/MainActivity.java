package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.drawerlayout.widget.DrawerLayout;

import android.animation.Animator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;


import com.blankj.swipepanel.SwipePanel;
import com.example.myapplication.bean.rxbus.Get_Countdown_Time;
import com.example.myapplication.callback.SharedViewModel;
import com.example.myapplication.databinding.ActivityMainBinding;

import com.example.myapplication.bean.rxbus.MainFrg_To_MainAct;
import com.example.myapplication.ui.timer.TimerActivity;
import com.example.myapplication.utils.CircularRevealUtil;
import com.example.myapplication.utils.ScreenUtils;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.bus.RxBus;

public class MainActivity extends BaseActivity<ActivityMainBinding,MainActivityViewModel> {
    private SharedViewModel mSharedViewModel;
    private SwipePanel mSwipePanel;
    private int mScreenWidth;
    private int mScreenHeight;
    private View container;
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public int initVariableId() {
        //给datebindiing中绑定的其他对象初始化
        binding.setVariable(BR.event,new EventHandler());
        return BR.viewModel;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void initData() {
        super.initData();
        mScreenHeight=ScreenUtils.getScreenHeight();
        mScreenWidth=ScreenUtils.getScreenWidth();
        mSharedViewModel=SharedViewModel.getSharedViewModel();
        container=binding.mainSwipePanel;
        initSlideSlip();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initViewObservable() {

            viewModel.drawerOpened.observe(this,aBoolean -> {
                 if(aBoolean){
                     mSwipePanel.setRightEnabled(false);
                 }else{
                     mSwipePanel.setRightEnabled(true);
                 }
                });
            viewModel.isToTimer().observe(this,aBoolean -> {
                if(aBoolean){
                    goToTimer(mScreenWidth/2,mScreenHeight/2);
                }
            });
    }
//得到焦点后将转换变量设置为false
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mSharedViewModel.isToTimer().setValue(false);
        viewModel.isToTimer().setValue(false);
    }

    @Override
    public void onBackPressed() {
     if(viewModel.openDrawer.getValue()){
         viewModel.openDrawer.setValue(false);
     }else {
         super.onBackPressed();
     }
    }

    public class EventHandler extends DrawerLayout.SimpleDrawerListener{
        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            viewModel.openDrawer.setValue(true);
            viewModel.drawerOpened.setValue(true);
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
            viewModel.openDrawer.setValue(false);
            viewModel.drawerOpened.setValue(false);
        }
    }
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
private void initSlideSlip(){
    mSwipePanel=binding.mainSwipePanel;
    mSwipePanel.setRightEdgeSize((int)(mScreenWidth*6/10));
    mSwipePanel.setOnFullSwipeListener(new SwipePanel.OnFullSwipeListener() {
        @Override
        public void onFullSwipe(int direction) {
            mSwipePanel.close(direction);
            goToTimer(-1,0);
        }
    });
}
public void goToTimer(int x_num,int y_num){
        RxBus.getDefault().postSticky(new Get_Countdown_Time(viewModel.countDownTime));
    Intent i=new Intent(MainActivity.this,TimerActivity.class);
    i.putExtra("x",x_num);
    i.putExtra("y",y_num);
    startActivity(i);
}
}