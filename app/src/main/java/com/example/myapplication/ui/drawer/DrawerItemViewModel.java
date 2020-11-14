package com.example.myapplication.ui.drawer;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.ObservableField;

import com.example.myapplication.R;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class DrawerItemViewModel extends ItemViewModel<DrawerViewModel> {
    public String text;
    public int imgurl;
    public DrawerItemViewModel(@NonNull DrawerViewModel viewModel, int imgurl,String text) {
        super(viewModel);
        this.imgurl=imgurl;
        this.text=text;
    }
    public BindingCommand itemClick=new BindingCommand((BindingAction)()->{
        ToastUtils.showShort("itemClicked");
    });

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImgurl() {
        return imgurl;
    }

    public void setImgurl(int imgurl) {
        this.imgurl = imgurl;
    }
}
