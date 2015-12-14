package com.example.panchangtao.udpbroadcast;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {
    private TextView showView = null;
    Handler handlerSocketRev = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showView = (TextView)findViewById(R.id.textshow);

        handlerSocketRev = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case (0x01):{
                        showView.setText(msg.obj.toString());
                    }break;
                    case (0x02):{
                        showView.setText(msg.obj.toString());
                    }break;
                    default:
                        break;
                }
            }
        };
    }

    public void FindServer(View view){
        showView.setText("search...");
        new SocketSearchThread().start();
    }

    class SocketSearchThread extends Thread{
        public SocketSearchThread(){

        }

        @Override
        public void run() {
            super.run();

            try {
                DatagramSocket bcSocket = new DatagramSocket(null);
                bcSocket.setReuseAddress(true);
                bcSocket.bind(new InetSocketAddress(18020));
                bcSocket.setSoTimeout(5000);

                byte[] buffer = new byte[256];
                DatagramPacket dataPacket = new DatagramPacket(buffer, buffer.length);
                dataPacket.setPort(18020);

                InetAddress broadcastaddr = InetAddress.getByName("255.255.255.255");
                dataPacket.setAddress(broadcastaddr);

                bcSocket.receive(dataPacket);
                String strRecv;
                strRecv = new String(dataPacket.getData()).trim();
                Message msgSocket = new Message();
                msgSocket.what = 0x01;
                msgSocket.obj = "address:" + dataPacket.getAddress().getHostAddress() + "; data:" + strRecv;
                handlerSocketRev.sendMessage(msgSocket);

                bcSocket.disconnect();
                bcSocket.close();
            } catch (SocketException e) {
                e.printStackTrace();
                Message errsmg = new Message();
                errsmg.what = 0x02;
                errsmg.obj = e.toString();
                handlerSocketRev.sendMessage(errsmg);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

