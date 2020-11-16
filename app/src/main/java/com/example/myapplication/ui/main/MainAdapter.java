package com.example.myapplication.ui.main;

import android.view.View;
import android.widget.Toast;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ui.drawer.DrawerItemViewModel;
import com.example.myapplication.utils.PopupList;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class MainAdapter extends BindingRecyclerViewAdapter<MainItemViewModel> {
    @Override
    public void onBindBinding(ViewDataBinding binding, int variableId, int layoutRes, int position, MainItemViewModel item) {
        super.onBindBinding(binding, variableId, layoutRes, position, item);
    }

}
