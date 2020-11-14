package com.example.myapplication.ui.drawer;

import androidx.databinding.ViewDataBinding;

import com.example.myapplication.ui.drawer.DrawerItemViewModel;

import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;

public class DrawerAdapter extends BindingRecyclerViewAdapter<DrawerItemViewModel> {
    @Override
    public void onBindBinding(ViewDataBinding binding, int variableId, int layoutRes, int position, DrawerItemViewModel item) {
        super.onBindBinding(binding, variableId, layoutRes, position, item);
    }
    public void refreshRecord(String record){
        getAdapterItem(1).setText("记录"+"   "+"15s");
        notifyDataSetChanged();
    }
}
