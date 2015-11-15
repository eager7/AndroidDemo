package com.example.panchangtao.handler2;

import android.os.Message;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar = null;
    Button button = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)findViewById(R.id.buttonStart);
        progressBar = (ProgressBar)findViewById(R.id.progressbar_test);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            Log.d("PCT","Recv Message\n");
            progressBar.setProgress(msg.arg1);
            handler.post(runnable);//start thread again
        }
    };

    public void buttonStartFun(View view){
        progressBar.setVisibility(View.VISIBLE);
        handler.post(runnable);
    }

    Runnable runnable = new Runnable() {
        int i = 0;//This a global variable
        @Override
        public void run() {
            Log.d("PCT", "Start Thread\n");
            i += 10;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Log.d("PCT", "Error Occurred\n");
                e.printStackTrace();
            }
            if(i == 100){
                handler.removeCallbacks(runnable);
                Log.d("PCT", "Stop Thread\n");
            }
            else {
                Message msg = handler.obtainMessage();
                msg.arg1 = i;
                handler.sendMessage(msg);
            }
        }
    };
}
