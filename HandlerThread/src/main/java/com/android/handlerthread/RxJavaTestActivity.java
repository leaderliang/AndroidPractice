package com.android.handlerthread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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


public class RxJavaTestActivity extends AppCompatActivity {
    private Disposable mDisposable;
    private Button bt_start,bt_stop;
    int count_time = 0; //总时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_test);
        bt_start = findViewById(R.id.bt_start);
        bt_stop = findViewById(R.id.bt_stop);
        bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime();
            }
        });
        bt_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeTimer();
            }
        });
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

        Observable.interval(0, 1000L, TimeUnit.MILLISECONDS)
                .take(count_time)//设置总共发送的次数
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        Log.d("Timer", "apply- " + aLong);
                        //aLong 从 0开始
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
                        Log.d("Timer", "onNext- " + value);
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

    /**
     * 关闭定时器
     */
    public void closeTimer() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }
}