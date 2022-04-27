package com.android.handlerthread;

import android.os.Bundle;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * HandlerThread
 * HandlerThread 是一种具有消息循环的线程，在它的内部可以使用 Handler。
 * HandlerThread 继承了Thread，它是一种可以使用 Handler 的 Thread，在 run 方法中通过 Looper.prepare 来创建消息队列，
 * 并通过Looper.loop来开启消息循环，这样在实际的使用中就允许在 HandlerThread 中创建 Handler。
 * <p>
 * HandlerThread 在内部创建了消息队列，外界需要通过 Handler 的消息方式来通知 HandlerThread 执行一个具体的任务。
 *
 * @author devliang
 */
public class MainActivity extends AppCompatActivity {

    private Button mBt;
    private HandlerThread mHandlerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBt = findViewById(R.id.button);
        mBt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 测试 true or false 对 onClick 的执行的影响
                // 如果在 onTouch 方法中通过返回 true 将事件消费掉，onTouchEvent 将不会再执行，
                // onTouchEvent 不执行的话，onClick 相关的也就不会执行
                return false;
            }
        });

        mBt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("onLongClick", "onLongClick");
                Toast.makeText(MainActivity.this, "long click button", Toast.LENGTH_LONG).show();
                // 返回值是false（默认）  则长按时执行完长按监听之后会走 onClick 的监听
                // 返回值是true   则长按时只会执行 setOnLongClickListener
                return false;
            }
        });

        mBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "click ", Toast.LENGTH_SHORT).show();
            }
        });



        mHandlerThread = new HandlerThread("current thread name is handlerThread");
        mHandlerThread.start();

        // HandlerThread 在内部创建了消息队列，外界需要通过 Handler 的消息方式来通知 HandlerThread 执行一个具体的任务。
        final MyHandler myHandler = new MyHandler(mHandlerThread.getLooper());
        Message message = myHandler.obtainMessage();
        message.obj = Thread.currentThread().getName() + "_1";
        myHandler.sendMessageDelayed(message,0);

        Message message_1 = myHandler.obtainMessage();
        message_1.obj = "延迟 1s 消息";
        myHandler.sendMessageDelayed(message_1,1000);

        Message message_2 = myHandler.obtainMessage();
        message_2.obj = "延迟 3s 消息";
        myHandler.sendMessageDelayed(message_2,3000);

        Message message_3 = myHandler.obtainMessage();
        message_3.obj = "延迟 5s 消息";
        myHandler.sendMessageDelayed(message_3,5000);



        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    Thread.sleep(100L);
                    Message message = myHandler.obtainMessage();
                    message.obj = Thread.currentThread().getName()+"_2";
                    myHandler.sendMessage(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /*TextView tv_context = findViewById(R.id.tv_context);
                tv_context.setText("i'm in thread");*/

            }
        }).start();


    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandlerThread.quit();



    }


}
