package com.android.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private Disposable mDisposable;
    private Button bt_start;
    private Button btnShow;
    FloatWindow floatWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_start = findViewById(R.id.bt_start);
        bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeTimer();
            }
        });
        startTime();


        // 权限判断
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(getApplicationContext())) {
                // 启动Activity让用户授权
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 10);
            } else {
                // 执行6.0以上绘制代码
                initView();
            }
        } else {
            // 执行6.0以下绘制代码
            initView();
        }
    }


    /*  *//**
     * 启动定时器
     *//*
    public void startTime() {

        Observable.timer(3000, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(Long value) {
                        Log.d("Timer", "" + value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        closeTimer();
                    }
                });
    }


    *//**
     * 关闭定时器
     *//*
    public void closeTimer() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }*/


    /**
     * 启动定时器
     */
    public void startTime() {
        int count_time = 10; //总时间
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(count_time + 1)//设置总共发送的次数
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        //aLong从0开始
                        return count_time - aLong;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(Long value) {
                        Log.d("Timer", "" + value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        // TODO:2017/12/1
                        closeTimer();
                    }
                });
    }

    /**
     * 关闭定时器
     */
    public void closeTimer() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        // 权限判断
        if (Build.VERSION.SDK_INT >= 23) {
            if (Settings.canDrawOverlays(getApplicationContext())) {
                initView();
            }
        } else {
            //执行6.0以下绘制代码
            initView();
        }
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        floatWindow = new FloatWindow(getApplicationContext());

        btnShow = findViewById(R.id.btn_show);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != floatWindow) {
                    floatWindow.showFloatWindow();
                }
            }
        });

        Button btnrefresh = findViewById(R.id.btn_refresh);
        btnrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int random = (int) (Math.random() * 10);
                if (null != floatWindow) {
                    floatWindow.updateText(String.valueOf(random));
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != floatWindow) {
            floatWindow.hideFloatWindow();
        }
    }


}