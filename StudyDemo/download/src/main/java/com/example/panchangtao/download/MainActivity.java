package com.example.panchangtao.download;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void buttonDownloadText(View view){
        System.out.println("ButtonDownloadText");

    }
    public void buttonDownloadMp3(View view){
        System.out.println("buttonDownloadMp3");
    }
}
