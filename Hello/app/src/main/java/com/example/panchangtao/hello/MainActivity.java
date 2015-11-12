package com.example.panchangtao.hello;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button buttonConnect = null;
    private TextView textViewStatus = null;
    private ListView listViewResult = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewStatus = (TextView)findViewById(R.id.textView);
        listViewResult = (ListView)findViewById(R.id.listViewResult);
        //listViewResult.add

        buttonConnect = (Button)findViewById(R.id.button_connect);
        buttonConnect.setText("Connect");
        buttonConnect.setOnClickListener(new ButtonConnectLister());
    }

    class ButtonConnectLister implements View.OnClickListener{
        @Override
        public void onClick(View v){
            //buttonConnect.setText("Click");
            //Toast.makeText(getApplicationContext(),"Connect IOTC...",Toast.LENGTH_LONG).show();
            Utils utils = new Utils();
            utils.mToast("Connect IOTC...", Toast.LENGTH_LONG, MainActivity.this);
           // midToast("Connect IOTC...", Toast.LENGTH_LONG);
            JSONObject jsonRequestList = new JSONObject();
            try{
                jsonRequestList.put("sequence_no", 123123);
                jsonRequestList.put("event_type", 1);
                jsonRequestList.put("message_type",0x8003);
                jsonRequestList.put("description","[]");
            }catch (JSONException ex){
                throw new RuntimeException(ex);
            }
            textViewStatus.setText(jsonRequestList.toString());
        }
    }
}
