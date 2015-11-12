package com.example.panchangtao.hello;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity {
    private Button buttonSearch = null;
    private Button buttonConnect = null;
    private TextView textViewStatus = null;
    private ListView listViewResult = null;
    Utils utils = null;
    Handler handlerSocketRev = null;
    private String iotcAddress = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        utils = new Utils();
        textViewStatus = (TextView)findViewById(R.id.textView);
        listViewResult = (ListView)findViewById(R.id.listViewResult);
        //listViewResult.add

        buttonConnect = (Button)findViewById(R.id.button_connect);
        buttonConnect.setText("Connect");
        buttonConnect.setOnClickListener(new ButtonConnectLister());

        buttonSearch = (Button)findViewById(R.id.button_search);
        buttonSearch.setText("Search Server");
        buttonSearch.setOnClickListener(new ButtonSearchLister());

        handlerSocketRev = new Handler(){
            @Override
            public void handleMessage(Message msg){
                switch (msg.what){
                    case (0x11):{
                        utils.mToast("Recv Message", Toast.LENGTH_SHORT, getApplicationContext());
                        utils.DBG_vPrintf("Recv Message From Socket\n");
                        textViewStatus.append("Recv Message");
                        utils.DBG_vPrintf("Set TextView\n");
                    }break;
                    case (0x12):{
                        utils.mToast("Connect TimeOut", Toast.LENGTH_SHORT, getApplicationContext());
                    }break;
                    case (0x13):{
                        utils.DBG_vPrintf("Find Iotc Server");
                        textViewStatus.setText(msg.obj.toString());
                    }
                    default:
                        break;
                }
            }
        };
        /*new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handlerSocketRev.sendEmptyMessage(0x11);
            }
        }, 0, 200);*/
    }

    class ButtonConnectLister implements View.OnClickListener{
        @Override
        public void onClick(View v){
            //buttonConnect.setText("Click");
            //Toast.makeText(getApplicationContext(),"Connect IOTC...",Toast.LENGTH_LONG).show();

            utils.mToast("Connect IOTC...", Toast.LENGTH_SHORT, MainActivity.this);
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
            new SocketClientThread(jsonRequestList.toString()).start();
        }
    }

    class ButtonSearchLister implements View.OnClickListener{
        @Override
        public void onClick(View v){
            utils.mToast("Search IOTC...", Toast.LENGTH_SHORT, MainActivity.this);

            new SocketSearchThread("Search").start();
        }
    }

    class SocketClientThread extends Thread{
        public String stringRev;

        public SocketClientThread(String str){
            stringRev = str;
        }

        @Override
        public void run() {
            Message msgSocket = new Message();

            Socket socketClient = new Socket();
            try {
                Log.i("PCT", "Connect to Server...\n");
                socketClient.connect(new InetSocketAddress("10.128.118.43", 7788), 5000);
                Log.i("PCT", "Connect Success\n");
                OutputStream outputStreamSocketClient = socketClient.getOutputStream();
                outputStreamSocketClient.write(Integer.parseInt(stringRev));
                outputStreamSocketClient.flush();

                BufferedReader bufferedReaderSocketClient = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
                String line = null;
                String buffer = "";
                while((line = bufferedReaderSocketClient.readLine()) != null){
                    buffer = line + buffer;
                }
                msgSocket.what = 0x11;
                msgSocket.obj = buffer;
                handlerSocketRev.sendMessage(msgSocket);


            } catch (IOException e) {
                utils.ERR_vPrintf("Can,t Connect to Server," + e.toString());
                handlerSocketRev.sendEmptyMessage(0x12);
                e.printStackTrace();
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class SocketSearchThread extends Thread{
        public String stringSearch;

        public SocketSearchThread(String str){
            stringSearch = str;
        }

        @Override
        public void run() {
            Message msgSocket = new Message();

            try {
                MulticastSocket mSocket = new MulticastSocket(7789);
                InetAddress groupAddress = InetAddress.getByName("240.240.1.1");
                mSocket.joinGroup(groupAddress);
                mSocket.setTimeToLive(4);

                byte[] buffSearch = new byte[255];
                buffSearch = "Hello IOTC".getBytes("utf-8");
                DatagramPacket udpPacketSend = new DatagramPacket(buffSearch, buffSearch.length, groupAddress, 7789);
                mSocket.send(udpPacketSend);

                DatagramPacket udpPacketRecv = new DatagramPacket(buffSearch, 255);
                mSocket.receive(udpPacketRecv);

                String stringIotcAddress;
                stringIotcAddress = udpPacketRecv.toString();

                Message msgIotcAddress = null;
                msgIotcAddress.what = 0x13;
                msgIotcAddress.obj = stringIotcAddress;
                handlerSocketRev.sendMessage(msgIotcAddress);
                mSocket.close();
            } catch (IOException e) {
                utils.ERR_vPrintf("Can,t Search Iotc\n");
                e.printStackTrace();
            }

        }
    }
}
