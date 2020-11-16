package com.example.myapplication.adapter;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.utils.PopupList;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class CommonBindingAdapter {
    @BindingAdapter(value = "imgurl",requireAll = false)
    public static void loadUrl(ImageView view, int state) {
        int img_url=R.drawable.loading;
        if(state==0){
            img_url= R.drawable.grade_0;
        }else if(state==1){
            img_url=R.drawable.grade_1;
        }
        Glide.with(view.getContext()).load(img_url).into(view);
    }
    @BindingAdapter(value = "drawerimg",requireAll = false)
    public static void loadDrawerUrl(ImageView view,int drawerurl)
    {
        Glide.with(view.getContext()).load(drawerurl).into(view);
    }

    @BindingAdapter(value = {"mOnLongClickCommand"}, requireAll = false)
    public static void onLongClickCommand(View view, final BindingCommand clickCommand) {
        RxView.longClicks(view)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object object) throws Exception {
                        if (clickCommand != null) {
                            clickCommand.execute(view);
                        }
                    }
                });
    }
}
