package com.example.myapplication.utils;

import android.animation.Animator;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.RequiresApi;

public final class CircularRevealUtil {
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static Animator createRevealAnimator(boolean reversed,int durationTime ,int x, int y, float width, float height, View view) {
        float hypot = (float) Math.hypot(height,width);
        float startRadius = reversed ? hypot : 0;
        float endRadius = reversed ? 0 : hypot;

        Animator animator = ViewAnimationUtils.createCircularReveal(
                view, x, y,
                startRadius,
                endRadius);
        animator.setDuration(durationTime);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        return animator;
    }
}
