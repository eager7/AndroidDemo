package com.example.panchangtao.hello;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by panchangtao on 2015/11/12.
 */
public class Utils {
    void mToast(String str, int showTime, Context mContext)
    {
        Toast toast;
        toast = Toast.makeText(mContext, str, showTime);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL , 0, 0);  //设置显示位置
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        v.setTextColor(Color.YELLOW);     //设置字体颜色
        toast.show();
    }
}
