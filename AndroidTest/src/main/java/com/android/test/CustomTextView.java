package com.android.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

/**
 * TODO
 *
 * @author dev.liang <a href="mailto:dev.liang@outlook.com">Contact me.</a>
 * @version 1.0.0
 * @since 2022/06/15 18:39
 */
public class CustomTextView extends androidx.appcompat.widget.AppCompatTextView {
    private Paint mPaint;
    private Paint mPaintSelect;
    private Paint mTextPaint;

    public CustomTextView(Context context) {
        super(context);
        initPaint();
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    private void initPaint() {
        mTextPaint = new Paint();
        mTextPaint.setTextSize(18);
        mTextPaint.setColor(Color.WHITE);


        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(0xFFF7F7F7);


        mPaintSelect = new Paint();
        mPaintSelect.setAntiAlias(true);
        mPaintSelect.setDither(true);
        mPaintSelect.setStyle(Paint.Style.FILL);
        mPaintSelect.setColor(0x50CFCFCF);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //绘制背景,在绘制文字之前绘制
        canvas.drawText("测试绘制文本颜色", 0, 0, mTextPaint);

        canvas.drawRect(new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight()), mPaint);
        canvas.drawRect(new Rect(10, 10, getMeasuredWidth() - 10, getMeasuredHeight() - 10), mPaintSelect);
        super.onDraw(canvas);
    }
}
