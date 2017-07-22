package com.example.chugh.zchat;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ravinder077 on 28-06-2017.
 */

public class ConnDBB extends AsyncTask<String,String,String>

{


    @Override
    protected String doInBackground(String... params) {

       // ArrayList<String> ls=new ArrayList<String>();
        String st=null;
       // System.out.println(params[0]);
       // System.out.println(params[1]);
        try  {

           // StringBuffer chaine = new StringBuffer("");



            try {

                System.err.println("mehtod hititing ");
                URL url = new URL("http://omtii.com/mile/otpgen.php?id=6000");
                System.err.println("URL  "+url);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("User-Agent", "");
                connection.setRequestMethod("GET");
                connection.connect();

               InputStream in = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder result = new StringBuilder();
                String line;
                while((line = reader.readLine()) != null) {
                    result.append(line);
                }

                 st=result.toString();

                System.err.println("output "+st);
              //  ls.add(result.toString());
                // Toast(,"",Toast.LENGTH_LONG).Show();
                // Toast.makeText(LoginActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
               // System.err.println(result.toString());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            //Your code goes here
        } catch (Exception e) {
            e.printStackTrace();
        }
        return st;
    }

    protected void onPostExecute(String result) {


       // super.onPostExecute(result);
       //done(result);
       // Toast.makeText(this, result.toString(), Toast.LENGTH_SHORT).show();



    }



}
