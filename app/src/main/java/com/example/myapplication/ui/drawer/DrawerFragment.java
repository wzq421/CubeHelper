package com.example.myapplication.ui.drawer;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.BR;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentDrawerBinding;

import io.reactivex.disposables.Disposable;
import me.goldze.mvvmhabit.base.BaseFragment;


public class DrawerFragment extends BaseFragment<FragmentDrawerBinding,DrawerViewModel> {

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_drawer;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.newRecord.observe(this,aBoolean -> {
            if(aBoolean){
                viewModel.items.get(1).setText("记录"+"        15s");
                binding.rv.getAdapter().notifyDataSetChanged();
            }
        });
    }
}