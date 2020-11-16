package com.example.myapplication.ui.main;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;

import com.example.myapplication.R;
import com.example.myapplication.bean.MainItemBean;
import com.example.myapplication.bean.rxbus.Grade_Change;
import com.example.myapplication.utils.PopupList;

import java.util.ArrayList;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class MainItemViewModel extends ItemViewModel<MainViewModel> {
    public MainItemBean mainItemBean;
    public MainItemViewModel(@NonNull MainViewModel viewModel, MainItemBean mainItemBean) {
        super(viewModel);
        this.mainItemBean = mainItemBean;
    }

public BindingCommand<View> itemDelete=new BindingCommand<View>(new BindingConsumer<View>() {
    @Override
    public void call(View view) {
        ArrayList<String> popupMenuItemList=new ArrayList<>();
        popupMenuItemList.add("删除");
        PopupList popupList = new PopupList(view.getContext());
        popupList.bind(view, popupMenuItemList, new PopupList.PopupListListener() {
            @Override
            public boolean showPopupList(View adapterView, View contextView, int contextPosition) {
                return true;
            }

            @Override
            public void onPopupListClick(View contextView, int contextPosition, int position) {
                RxBus.getDefault().post(new Grade_Change(-1,mainItemBean.getGrade(),0));
            }
        });
    }
});
    public BindingCommand delete=new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            RxBus.getDefault().post(new Grade_Change(-1,mainItemBean.getGrade(),0));
        }
    });
    public MainItemBean getMainItemBean() {
        return mainItemBean;
    }

    public void setMainItemBean(MainItemBean mainItemBean) {
        this.mainItemBean = mainItemBean;
    }
}
