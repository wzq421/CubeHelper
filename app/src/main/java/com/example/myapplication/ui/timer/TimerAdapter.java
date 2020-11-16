package com.example.myapplication.ui.timer;

import androidx.databinding.ViewDataBinding;

import com.example.myapplication.ui.main.MainItemViewModel;

import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;

public class TimerAdapter extends BindingRecyclerViewAdapter<TimerItemViewModel> {
    @Override
    public void onBindBinding(ViewDataBinding binding, int variableId, int layoutRes, int position, TimerItemViewModel item) {
        super.onBindBinding(binding, variableId, layoutRes, position, item);
    }
}
