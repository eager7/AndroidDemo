package com.example.panchangtao.iotc;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Created by panchangtao on 15/11/14.
 */
public class IotcServerAdapter extends BaseAdapter{
    private LinkedList<IotcServers> mData;
    private Context mContext;

    public IotcServerAdapter(){
    }

    public IotcServerAdapter(LinkedList<IotcServers> mData, Context mContext){
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
        convertView = LayoutInflater.from(mContext).inflate(R.layout.list_devices, parent, false);
        ImageView imageView = (ImageView)convertView.findViewById(R.id.list_view);
        TextView textView = (TextView)convertView.findViewById(R.id.list_text);
        TextView textView2 = (TextView)convertView.findViewById(R.id.list_text2);
        imageView.setBackgroundResource(mData.get(position).getaIcon());
        textView.setText(mData.get(position).getaName());
        textView2.setText(mData.get(position).getaIp());
        return convertView;
    }
}
