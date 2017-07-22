package com.example.chugh.zchat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ravinder077 on 13-07-2017.
 */

public class GetBitmapfromUrlThread extends AsyncTask<String,String,Bitmap> {



    @Override
    protected Bitmap doInBackground(String... params) {


        String photoUrl=params[0];
        System.err.println("photoUrl"+photoUrl);
        Bitmap bitmap=getBitmapfromUrl(photoUrl);
        return bitmap;
    }







    protected Bitmap getBitmapfromUrl(String imageUrl)
    {
        try
        {
            URL url = new URL(imageUrl);

            System.out.println("loading img  "+imageUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }
}
