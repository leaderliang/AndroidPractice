package com.android.ballot;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.sax.EndElementListener;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;


/**
 * 课堂题目交互效果动画工具类
 */
public class AnimationUtils {


    /**
     * branch 打开后入场动画
     * 顺序如下
     * 1、小-大
     * 2、翻转
     * 3、Z 轴晃动
     *
     * @param context
     * @param view
     * @param listener
     */
    public static void branchVewStartAnimation(Context context, View view,
                                               ValueAnimator.AnimatorUpdateListener updateListener,
                                               Animator.AnimatorListener listener) {
        AnimatorSet animatorSet = new AnimatorSet();
        Rect targetR = new Rect();
        view.getGlobalVisibleRect(targetR);

        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 0, 1);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 0, 1);
        ObjectAnimator scaleAnimator = ObjectAnimator.ofPropertyValuesHolder(view, scaleX, scaleY);
        scaleAnimator.setDuration(1000);
        scaleAnimator.addListener(new MyAnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setVisibility(View.VISIBLE);
            }
        });
        /*360.0f, 180.0f 从大到小是符合设计效果的从右向左旋转效果*/
        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(view, "rotationY",  0.0f);
        rotationAnimator.setDuration(1200);
        rotationAnimator.addUpdateListener(updateListener);

        ObjectAnimator flapAnimator = ObjectAnimator.ofFloat(view, "rotationY",
                -50.0f, 50.0f, -15.0f, 15.0f, -5.0f, 5.0f, -1.0f, 1.0f);
        // 50.0f, -50.0f, 15.0f, -15.0f, 5.0f, -5.0f, 1.0f, -1.0f
        // -50.0f, 50.0f, -15.0f, 15.0f, -5.0f, 5.0f, -1.0f, 1.0f
        flapAnimator.setStartDelay(2200);
        flapAnimator.setDuration(3000);
        flapAnimator.setInterpolator(new DecelerateInterpolator());

        animatorSet.play(scaleAnimator).before(rotationAnimator);
        animatorSet.play(flapAnimator);
        animatorSet.addListener(listener);
        animatorSet.start();

    }


    public static void courseCardAnimation(Context context, View view, View lottieAnimationView, ValueAnimator.AnimatorUpdateListener updateListener,
                                           Animator.AnimatorListener listener) {
        AnimatorSet animatorSet = new AnimatorSet();
        Rect targetR = new Rect();
        view.getGlobalVisibleRect(targetR);

        PropertyValuesHolder scaleFromX = PropertyValuesHolder.ofFloat("scaleX", 0f, 1f);
        PropertyValuesHolder scaleFromY = PropertyValuesHolder.ofFloat("scaleY", 0f, 1f);
        ObjectAnimator scaleFromAnimator = ObjectAnimator.ofPropertyValuesHolder(view, scaleFromX, scaleFromY);
        scaleFromAnimator.setDuration(2800);
        scaleFromAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        PropertyValuesHolder rotation = PropertyValuesHolder.ofFloat("rotationY", 360f * 5);
        ObjectAnimator rotationAnimator = ObjectAnimator.ofPropertyValuesHolder(view, rotation);
        rotationAnimator.addListener(new MyAnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                lottieAnimationView.setVisibility(View.VISIBLE);
            }
        });

        rotationAnimator.setDuration(2800);
        rotationAnimator.addUpdateListener(updateListener);
        rotationAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 1f, 1.6f, 0.65f, 1.2f, 0.9f, 1f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1f, 1.6f, 0.65f, 1.2f, 0.9f, 1f);
        ObjectAnimator scaleAnimator = ObjectAnimator.ofPropertyValuesHolder(view, scaleX, scaleY);
        scaleAnimator.setStartDelay(3000);
        scaleAnimator.setDuration(2000);
        scaleAnimator.setInterpolator(new FastOutSlowInInterpolator());

        animatorSet.play(scaleFromAnimator).with(rotationAnimator);
        animatorSet.play(scaleAnimator);
        animatorSet.addListener(listener);
        animatorSet.start();

    }

}