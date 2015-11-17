package com.example.panchangtao.download;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by panchangtao on 15/11/16.
 */
public class HttpDownloader {
    private URL url = null;

    public String download(String urlStr){  //the file of download must be a text file
        StringBuffer sb = new StringBuffer();
        String line = null;
        BufferedReader buffer = null;
        try {
            url = new URL(urlStr);
            HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();
            buffer = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            while ((line = buffer.readLine()) != null){
                sb.append(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try {
                buffer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
