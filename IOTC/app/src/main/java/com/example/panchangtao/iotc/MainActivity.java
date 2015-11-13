package com.example.panchangtao.iotc;

import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.ImageView;

import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {
    private ImageView imageViewWelcom = null;
    private Utils utils = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        utils = new Utils();

        super.onCreate(savedInstanceState);
        //final View viewStart = View.inflate(this, R.layout.activity_main, null);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        imageViewWelcom = (ImageView)findViewById(R.id.image_welcom);

        final Handler handlerStartNew = new Handler() {
            @Override
            public void handleMessage(Message msg){
                utils.DBG_vPrintf("Start A New Activity\n");

                Intent intent = new Intent();
                intent.setClass(MainActivity.this, IotcActivity.class);
                MainActivity.this.startActivity(intent);
                MainActivity.this.finish();
            }
        };
        utils.DBG_vPrintf("Delay 1 sencond\n");
        handlerStartNew.sendEmptyMessageDelayed(0, Utils.iTimeWelcome);
    }


}
