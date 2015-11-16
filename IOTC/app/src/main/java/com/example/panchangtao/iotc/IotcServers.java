package com.example.panchangtao.iotc;

/**
 * Created by panchangtao on 15/11/14.
 */
public class IotcServers {
    private String strIotcServerName;
    private String strIotcServerIP;
    private int aIcon;

    public IotcServers(){
    }

    public IotcServers(String strIotcServerName, String strIotcServerIP, int aIcon){
        this.strIotcServerName = strIotcServerName;
        this.strIotcServerIP = strIotcServerIP;
        this.aIcon = aIcon;
    }


    public String getaName() {
        return strIotcServerName;
    }

    public String getaIp() {
        return strIotcServerIP;
    }

    public int getaIcon() {
        return aIcon;
    }

    public void setaName(String aName) {
        this.strIotcServerName = aName;
    }

    public void setaIp(String aIP) {
        this.strIotcServerIP = aIP;
    }

    public void setaIcon(int aIcon) {
        this.aIcon = aIcon;
    }
}
