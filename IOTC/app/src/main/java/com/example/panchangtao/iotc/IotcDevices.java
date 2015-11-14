package com.example.panchangtao.iotc;

/**
 * Created by panchangtao on 15/11/14.
 */
public class IotcDevices {
    private String stringDeviceName;
    private int intDeviceID;
    private long longDeviceMac;
    private boolean boolDeviceOnline;
    private boolean boolDeviceOnOff;

    public IotcDevices(){
    }

    public IotcDevices(String strName, int iDeviceID, long lDeviceMac){
        this.stringDeviceName = strName;
        this.intDeviceID = iDeviceID;
        this.longDeviceMac = lDeviceMac;
    }

}
