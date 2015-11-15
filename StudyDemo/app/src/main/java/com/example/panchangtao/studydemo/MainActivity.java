package com.example.panchangtao.studydemo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button buttonTest = null;
    private TextView textViewHello = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewHello = (TextView)findViewById(R.id.text_hello);
        buttonTest = (Button)findViewById(R.id.button_start);
        //buttonTest.setText("Button");
    }

    public void buttonStartListener(View view){
        textViewHello.setText("Start");
        handler.post(updateThread);
    }

    public void buttonEndListener(View view){
        textViewHello.setText("End");
        handler.removeCallbacks(updateThread);
    }

    Handler handler = new Handler();
    Runnable updateThread = new Runnable() {
        @Override
        public void run() {
            Log.d("PCT", "Thread Running...\n");
            handler.postDelayed(updateThread, 3000);//3s run this thread again
        }
    };

}
