package com.example.myapplication.adapter;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

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
}
