package com.android.handlerthread;

import android.os.Bundle;
import android.os.HandlerThread;
import android.os.Message;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 *
 * HandlerThread
 * HandlerThread是一种具有消息循环的线程，在它的内部可以使用 Handler。
 * HandlerThread继承了Thread，它是一种可以使用Handler的Thread，在run方法中通过Looper.prepare来创建消息队列，
 * 并通过Looper.loop来开启消息循环，这样在实际的使用中就允许在HandlerThread中创建Handler。
 *
 * HandlerThread在内部创建了消息队列，外界需要通过 Handler 的消息方式来通知 HandlerThread 执行一个具体的任务。
 * @author devliang
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HandlerThread handlerThread = new HandlerThread("MainActivity");
        handlerThread.start();


//        HandlerThread在内部创建了消息队列，外界需要通过 Handler 的消息方式来通知 HandlerThread 执行一个具体的任务。
        MyHandler myHandler = new MyHandler(handlerThread.getLooper());

        Message message = myHandler.obtainMessage();
        message.obj = Thread.currentThread().getName();
        myHandler.sendMessage(message);


        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                TextView tv_context = findViewById(R.id.tv_context);
                tv_context.setText("i'm in thread");

            }
        }).start();

    }







}
