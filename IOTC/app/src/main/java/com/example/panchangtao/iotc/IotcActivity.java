package com.example.panchangtao.iotc;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class IotcActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Utils utils = null;
    Handler handlerSocketRev = null;
    TextView textViewDisplay = null;
    ListView listViewDisplay = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iotc);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.iot_center);

        utils = new Utils();
        textViewDisplay = (TextView)findViewById(R.id.textview_dispaly);
        listViewDisplay = (ListView)findViewById(R.id.listview_display);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        handlerSocketRev = new Handler(){
            @Override
            public void handleMessage(Message msg){
                switch (msg.what){
                    case (Utils.iRecvData):{
                        utils.mToast("Recv Message", Toast.LENGTH_SHORT, getApplicationContext());
                        utils.DBG_vPrintf("Recv Message From Socket\n");
                        textViewDisplay.append("Recv Message");

                    }break;
                    case (Utils.iTimeOut):{
                        utils.mToast("Can't Connect With Server", Toast.LENGTH_SHORT, getApplicationContext());
                        textViewDisplay.setText(R.string.connect_timeout);
                    }break;
                    case (Utils.iFindServer):{
                        utils.DBG_vPrintf("Find Iotc Server\n");
                        textViewDisplay.setText(msg.obj.toString());
                        textViewDisplay.setText(R.string.connect_success);

                        utils.DBG_vPrintf("New mData\n");
                        List<IotcServers> mData = new LinkedList<IotcServers>();
                        utils.DBG_vPrintf("add mData:"+msg.obj.toString());

                        mData.add(new IotcServers("IotcServer", msg.obj.toString(), R.mipmap.launcher_router));
                        utils.DBG_vPrintf("New adapter\n");
                        IotcServerAdapter adapter = new IotcServerAdapter((LinkedList<IotcServers>)mData, IotcActivity.this);
                        utils.DBG_vPrintf("display mData\n");
                        listViewDisplay.setAdapter(adapter);
                    }
                    default:
                        break;
                }
            }
        };
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.iotc, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_search_iotc) {
            searchIotcServer();
        } else if (id == R.id.nav_list_devices) {
            listDevicesList();
        } else if (id == R.id.nav_help_iotc) {
            showIotcHelp();
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void searchIotcServer(){
        utils.mToast("searchIotcServer", Toast.LENGTH_SHORT, getApplicationContext());
        WifiManager.MulticastLock multicastLock = null;
        WifiManager wifiManager=(WifiManager)getSystemService(Context.WIFI_SERVICE);
        multicastLock=wifiManager.createMulticastLock("multicast.test");
        multicastLock.acquire();

        new SocketSearchThread("Hello Server").start();

        multicastLock.release();
    }

    public void listDevicesList(){
        utils.mToast("listDevicesList", Toast.LENGTH_SHORT, getApplicationContext());
        String[] strs = new String[] {
                "first", "second", "third", "fourth", "fifth"
        };
        listViewDisplay.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strs));
    }

    private List<String> getData(){

        List<String> data = new ArrayList<String>();
        data.add("测试数据1");
        data.add("测试数据2");
        data.add("测试数据3");
        data.add("测试数据4");

        return data;
    }

    public void showIotcHelp(){
        utils.mToast("showIotcHelp", Toast.LENGTH_SHORT, getApplicationContext());
    }

    class SocketSearchThread extends Thread{
        public String stringSearch;

        public SocketSearchThread(String str){
            stringSearch = str;
        }

        @Override
        public void run() {
            Message msgSocket = new Message();
            utils.DBG_vPrintf("Create Mul Socket");
            try {
                MulticastSocket mSocket = new MulticastSocket(6789);
                InetAddress groupAddress = InetAddress.getByName(Utils.stringMulAddress);
                mSocket.joinGroup(groupAddress);
                mSocket.setTimeToLive(4);

                utils.DBG_vPrintf("Send Data to Server");
                byte[] buffSearch = new byte[255];
                buffSearch = stringSearch.getBytes("utf-8");
                DatagramPacket udpPacket = new DatagramPacket(buffSearch, buffSearch.length, groupAddress, Utils.iMulPort);
                mSocket.send(udpPacket);

                byte[] byteRev = new byte[512];
                udpPacket = new DatagramPacket(byteRev,byteRev.length);
                utils.DBG_vPrintf("Recv Data From Server");
                mSocket.setSoTimeout(2000);//Set Recv Timeout
                mSocket.receive(udpPacket);

                String stringIotcAddress;
                stringIotcAddress = new String(udpPacket.getData()).trim();
                utils.DBG_vPrintf("Recv Data From Server Success:" + stringIotcAddress);

                Message msgIotcAddress = new Message();
                msgIotcAddress.what = Utils.iFindServer;
                msgIotcAddress.obj = udpPacket.getAddress().getHostName();
                utils.DBG_vPrintf("The Server Ip is " + msgIotcAddress.obj.toString());

                handlerSocketRev.sendMessage(msgIotcAddress);

                mSocket.leaveGroup(groupAddress);
                mSocket.disconnect();
                mSocket.close();
            } catch (IOException e) {
                utils.ERR_vPrintf("Can't Search Iotc," + e.toString());
                e.printStackTrace();
                handlerSocketRev.sendEmptyMessage(Utils.iTimeOut);
            }

        }
    }
}
