package com.android.handlerthread;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

/**
 * TODO
 *
 *  Handler存在一个构造函数，传入一个Looper对象，Handler 的 handleMessage 获取的是Looper的MessageQueue中的Message
 *  因此，handleMessage的调用与Looper对象同属于一个线程，这里我们在构造时传入HandlerThread的Looper对象，
 *
 *  handleMessage运行于HandlerThread线程（也就是一个子线程），所以Handler虽然是在主线程创建，但是它的
 *  handleMessage接收到消息是在HandlerThread线程，
 *
 *  执行下代码可以看到打印出如下log：
 *  D/HandlerThreadDemo: HandlerThread/Demo thread receiver the message from thread: main
 *  log 也说明，message由主线程传递到了HandlerThread中。
 *
 * @author dev.liang <a href="mailto:dev.liang@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2019/11/21 17:15
 */
public class MyHandler extends Handler {


    private static final String TAG = "MyHandler";

    public MyHandler(@NonNull Looper looper) {
        super(looper);
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        String str = (String) msg.obj;
        Log.d(TAG, Thread.currentThread().getName() + " thread receiver the message from thread: " + str);
    }
}
