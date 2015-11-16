package com.example.panchangtao.handlerbundle;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Member;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Handler handler = new Handler();
        //handler.post(runnable);
        setContentView(R.layout.activity_main);
    }

    HandlerThread handlerThread = new HandlerThread("handler_thread");//提供循环处理消息的功能
    handlerThread.start();

    MyHandler myHandler = new MyHandler(handlerThread.getLooper());
    Message msg = myHandler.obtainMessage();
    msg.sendToTarget();

    class MyHandler extends Handler{
        public MyHandler(){

        }
        public MyHandler(Looper looper){
            super(looper);
        }
        @Override
        public void handleMessage(Message msg){
            Log.d("PCT", "Recv Message\n");
            System.out.println(Thread.currentThread().getId());
        }
    }


    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
}
