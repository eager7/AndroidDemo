package com.example.panchangtao.iotc;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.LinkedList;

/**
 * Created by panchangtao on 15/11/14.
 */
public class IotcDeviceAdapter extends BaseAdapter {
    private LinkedList<IotcDevices> mData;
    private Context mContext;

    public IotcDeviceAdapter(){
    }

    public IotcDeviceAdapter(LinkedList<IotcDevices> mData, Context mContext){
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount(){
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
