package com.android.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * TODO
 * https://blog.csdn.net/iamchb2/article/details/79878619
 * 属性在多个地方被赋值后，系统以哪个属性为准呢？
 * 优先级xml定义 > style > theme （优先级 xml直接定义>xml的style定义>theme直接定义）
 *
 *
 * 在Theme中的优先级主要涉及到三个部分：defStyleAttr，defStyleRes和主题直接定义
 * 我们需要分三种情况，在构造函数中，
 * 1 当defStyleAttr！=0时，
 * 主题中如果对defStyleAttr属性进行赋值，显示对defStyleAttr的赋值，优先级最高！
 * 2 当（defStyleAttr==0或主题没有对defStyleAttr进行赋值）&& defStyleRes!=0而且theme中没有定义时时，显示defStyleRes，优先级中
 * 3 如果defStyleAttr==0且defStyleRes==0时，显示theme直接定义，优先级最低
 * 由此我们得到属性赋值总体优先级：
 *
 * 【结论 总】属性赋值优先级   Xml定义 > xml的style定义 > defStyleAttr > defStyleRes> theme直接定义
 *
 *
 * @author dev.liang <a href="mailto:dev.liang@outlook.com">Contact me.</a>
 * @version 1.0.0
 * @since 2022/04/20 18:35
 */
public class ViewConstructor extends View {
    public ViewConstructor(Context context) {
        super(context);
    }

    public ViewConstructor(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewConstructor(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     *
     * 在android中的属性可以在多个地方进行赋值，涉及到的优先级排序为：
     * Xml直接定义 > xml中style引用 > defStyleAttr > defStyleRes > theme直接定义
     *
     * @param context 上线文，这个不用多说
     * @param attrs  从xml中定义的参数
     * @param defStyleAttr 主题中优先级最高的属性
     * @param defStyleRes 优先级次之的内置于View的style
     */
    public ViewConstructor(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
