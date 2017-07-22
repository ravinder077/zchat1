package com.example.chugh.zchat;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Chugh on 7/3/2017.
 */

public class OtpGen extends AsyncTask<String,String,String> {


    @Override
    protected String doInBackground(String... params) {

        URL url;
    String st = null;
        try {


            url=new URL(params[0]);
            System.err.println("url"+url);
            HttpURLConnection con=(HttpURLConnection) url.openConnection();
           con.setRequestProperty("User-Agent","");
            con.setRequestMethod("GET");

            con.connect();

            InputStream in= con.getInputStream();
            BufferedReader reader=new BufferedReader(new InputStreamReader(in));
            StringBuilder result=new StringBuilder();
            String line;
            while((line=reader.readLine())!=null)
            {
                result.append(line);
            }

            st=result.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }



        return st;
    }
}
