package com.example.myapplication.ui.main;

import androidx.databinding.ViewDataBinding;

import com.example.myapplication.ui.drawer.DrawerItemViewModel;

import io.reactivex.disposables.Disposable;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;

public class MainAdapter extends BindingRecyclerViewAdapter<MainItemViewModel> {
    @Override
    public void onBindBinding(ViewDataBinding binding, int variableId, int layoutRes, int position, MainItemViewModel item) {
        item.getMainItemBean().setResId(position);
        super.onBindBinding(binding, variableId, layoutRes, position, item);
    }
}
