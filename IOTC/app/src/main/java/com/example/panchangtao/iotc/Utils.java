package com.example.panchangtao.iotc;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by panchangtao on 15/11/13.
 */
public class Utils {
    public static final int iTimeWelcome = 500;
    public static final int iRecvData = 0x01;
    public static final int iFindServer = 0x02;
    public static final int iTimeOut = 0x03;

    public static final int iMulPort = 7789;
    public static final String stringMulAddress = "239.227.227.227";


    void mToast(String str, int showTime, Context mContext)
    {
        Toast toast;
        toast = Toast.makeText(mContext, str, showTime);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL , 0, 0);  //设置显示位置
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        v.setTextColor(Color.YELLOW);     //设置字体颜色
        toast.show();
    }

    void DBG_vPrintf(String str){
        Log.d("PCT", str);
    }
    void INF_vPrintf(String str){
        Log.i("PCT", str);
    }
    void ERR_vPrintf(String str){
        Log.e("PCT", str);
    }
    void delay(int ms) {
        Thread.currentThread();
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
