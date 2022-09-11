package com.android.handlerthread;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;

/**
 * 主线程与子线程之间的通信
 * 使用 MyThread 时候，不加 Thread.sleep(1000); 此时子线程的Looper对象还没有被创建出来，那么此时 handler 和 thread.looper 绑定，thread.looper肯定为空，
 * 替代方案是使用系统封装的 handlerthread，通过使用 synchronized 来保证在调用 handerthread 的 getLooper() 时候，looper 不为空
 * <p>
 * <p>
 * 线程（Thread）、循环器（Looper）、处理者（Handler）之间的对应关系如下：
 * <p>
 * 1个线程（Thread）只能绑定 1个循环器（Looper），但可以有多个处理者（Handler）
 * 1个循环器（Looper） 可绑定多个处理者（Handler）
 * 1个处理者（Handler） 只能绑定1个1个循环器（Looper）
 * <p>
 * Handler 里可以放置 主线程的 mainlooper 或者子线程创建的 looper，但是 handlermessage 里处理的 UI 都是在主线程
 *
 * @author devliang
 */
public class HandlerTestActivity extends AppCompatActivity {

    private TextView mTv;
    private final MyHandler mHandler = new MyHandler(this);
    private Handler mHandler2;
    private MyThread thread;


    private static class MyHandler extends Handler {
        private final WeakReference<HandlerTestActivity> mActivity;

        public MyHandler(HandlerTestActivity activity) {
            this.mActivity = new WeakReference<>(activity);
        }


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            HandlerTestActivity activity = mActivity.get();
            if (activity == null) {
                return;
            }

            if (msg != null) {
                System.out.println("当前 msg.arg1=" + msg.arg1 + "  msg.arg2=" + msg.arg2);
                activity.mTv.setText("msg.arg1=" + msg.arg1 + "  msg.arg2=" + msg.arg2);
            }
        }
    }


    class MyThread extends Thread {
        private Looper looper;//取出该子线程的Looper

        public MyThread() {
        }

        public MyThread(@NonNull String name) {
            super(name);
        }

        @Override
        public void run() {
            Message msg = new Message();
            msg.arg1 = 1992;
            msg.arg2 = 1997;
            mHandler.sendMessage(msg);

            Looper.prepare();//创建该子线程的Looper
            looper = Looper.myLooper();//取出该子线程的Looper
            Looper.loop();//只要调用了该方法才能不断循环取出消息
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mTv = findViewById(R.id.textView);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//       testDelayMessage();

        System.out.println("当前线程是----->" + Thread.currentThread() + "  group=" + Thread.currentThread().getThreadGroup());
        System.out.println("当前线程id是----->" + Thread.currentThread().getId());
        System.out.println("当前线程是----->" + Thread.currentThread().getName());
        System.out.println("\n");


//        thread = new MyThread("子线程 thread");
//        thread.start();


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 子线程里传 main looper
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Looper looper = Looper.myLooper();
                mHandler2 = new Handler(looper) {
                    // mHandler2 = new Handler(getMainLooper()) {
                    @Override
                    public void handleMessage(@NonNull Message msg) {
                        super.handleMessage(msg);
                        mTv.setText("msg.arg1=" + msg.arg1 + "  msg.arg2=" + msg.arg2);
                        System.out.println("----->msg.arg1=" + msg.arg1 + "  msg.arg2=" + msg.arg2);
                        System.out.println("mHandler2  handleMessage 当前线程是----->" + Thread.currentThread() + "  group=" + Thread.currentThread().getThreadGroup());
                        System.out.println("mHandler2  handleMessage 当前线程id是----->" + Thread.currentThread().getId());
                        System.out.println("mHandler2  handleMessage 当前线程是----->" + Thread.currentThread().getName());
                    }
                };

                System.out.println("当前线程是----->" + Thread.currentThread() + "  group=" + Thread.currentThread().getThreadGroup());
                System.out.println("当前线程id是----->" + Thread.currentThread().getId());
                System.out.println("当前线程是----->" + Thread.currentThread().getName());

                Looper.loop();

                Message msg = new Message();
                msg.arg1 = 1993;
                msg.arg2 = 1998;
                mHandler2.sendMessage(msg);


            }
        }, "子线程里传 main looper").start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 实例化一个特殊的线程HandlerThread
       /* HandlerThread thread = new HandlerThread("HandlerThread");
        thread.start();
        System.out.println("HandlerThread 当前线程是----->" + thread.getLooper() + "  group="+ thread.getThreadGroup());
        System.out.println("HandlerThread 当前子线程id是----->" + thread.getId());
        System.out.println("HandlerThread 当前子线程是----->" + thread.getName());
        mHandler2 = new Handler(thread.getLooper()) {
            //        mHandler2 = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                mTv.setText("msg.arg1=" + msg.arg1 + "  msg.arg2=" + msg.arg2);
                System.out.println("----->msg.arg1=" + msg.arg1 + "  msg.arg2=" + msg.arg2);
                System.out.println("HandlerThread 当前子线程是----->" + Thread.currentThread());
                System.out.println("HandlerThread 当前子线程id是----->" + Thread.currentThread().getId());
                System.out.println("HandlerThread 当前子线程是----->" + Thread.currentThread().getName());
            }
        };*/

        Message msg = new Message();
        msg.arg1 = 1993;
        msg.arg2 = 1998;
        mHandler2.sendMessage(msg);


    }

    private void testDelayMessage() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println("TestActivity 我开始执行消息了");
            }
        }, 3000);

        try {
            System.out.println("TestActivity 开始执行 Thread.sleep");
            Thread.sleep(5000);
            System.out.println("TestActivity 执行结束 Thread.sleep");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}