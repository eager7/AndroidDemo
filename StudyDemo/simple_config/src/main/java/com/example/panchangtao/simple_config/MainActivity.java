package com.example.panchangtao.simple_config;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void connect(View view){
        Toast.makeText(this, "Connect to server", Toast.LENGTH_SHORT);
        new SocketClientThread("Hello WiFi").start();
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
            Handler handlerSocketRev;
            try {
                Log.i("PCT", "Connect to Server...\n");
                socketClient.connect(new InetSocketAddress(SOCKET, 7788), 5000);
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
                msgSocket.what = Utils.iRecvData;
                msgSocket.obj = buffer;
                handlerSocketRev.sendMessage(msgSocket);
                socketClient.close();

            } catch (IOException e) {
                Log.d("PCT", "Can,t Connect to Server," + e.toString());
                handlerSocketRev.sendEmptyMessage(Utils.iTimeOut);
                e.printStackTrace();

            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
