package com.example.chugh.zchat;

/**
 * Created by ravinder077 on 12-07-2017.
 */

import android.app.Service;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by TutorialsPoint7 on 8/23/2016.
 */

public class PhotoService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();

        System.err.println("services started");



        SQLiteDatabase mydata = openOrCreateDatabase("DM", MODE_PRIVATE, null);
        Cursor resultSet = mydata.rawQuery("Select * from new_user", null);
        resultSet.moveToFirst();

        String pmobile = resultSet.getString(1);

        System.err.println("Mobile no pid = " + pmobile);

        OtpGen otpgen =new OtpGen();
        otpgen.execute("http://www.omtii.com/mile/simages.php?pmobile="+pmobile);
        try {
            System.err.println("Photo cc" + otpgen.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
      /*  Bitmap cpic= BitmapFactory.decodeResource(getResources(),R.drawable.great_wall_of_china);
        System.err.println("cpic" + cpic);*/
        String jsonphoto=null;
        try {
            jsonphoto= otpgen.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            ArrayList<PhotoModel> photono=  jsonToMap(jsonphoto);

            for (PhotoModel cd:photono) {


                Toast.makeText(this, "Friends List Updated", Toast.LENGTH_SHORT).show();
                SQLiteDatabase mydata1 = openOrCreateDatabase("DM", MODE_PRIVATE, null);
                mydata1.execSQL("update contact set cphoto='"+cd.getImgpath()+"' where cnumber='"+cd.getName()+"'");
                System.err.println(" query : update contact set cphoto='"+cd.getImgpath()+"' where cnumber='"+cd.getName()+"'");

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return START_STICKY;
    }





    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }




    public ArrayList<PhotoModel> jsonToMap(String jsonStr) throws JSONException {


        HashMap<String, String> contact = new HashMap<>();

        ArrayList<PhotoModel> al=new ArrayList<PhotoModel>();

        if (jsonStr != null) {

            System.err.print("josn strin is");

            System.err.print(jsonStr);

            System.err.print("josn strin ends");



            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray contacts = jsonObj.getJSONArray("tasks");

                // looping through All Contacts
                JSONArray c = jsonObj.getJSONArray("tasks");
                for (int i = 0 ; i < c.length(); i++) {
                    JSONObject obj = c.getJSONObject(i);
                    String A = obj.getString("cmobile");
                    String B = obj.getString("cphoto");

                    System.out.println(A + " " + B );

                    PhotoModel cc=new PhotoModel();

                    cc.setName(A);
                    // cc.setImgpath(B);

                //  MyThread myThread=new MyThread(B,A);
                 //  myThread.start();
                    GetBitmapfromUrlThread  getBitmapfromUrlThread=new GetBitmapfromUrlThread();
                    getBitmapfromUrlThread.execute(B);
                    Bitmap bb=getBitmapfromUrlThread.get();

                   // Bitmap bb=   getBitmapfromUrl(B);
                      String photopath=saveToInternalStorage(bb);
                   // String photopath=null;
                    cc.setImgpath(photopath);

                    System.err.println("photopath"+photopath);
                    al.add(cc);



                }

                for (PhotoModel cd:al) {

                    String name = cd.getName();
                    String img  =  cd.getImgpath();



                    System.err.println("name inside "+name  +"  values inside "+img );



                }



            } catch (Exception e) {
                e.printStackTrace();


            }
        }

        return  al;
    }
    private String saveToInternalStorage(Bitmap bitmapImage){
        System.err.println("I m in save Internal storage function");
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        String imgname=Math.random()+"upic.jpg";
        File mypath=new File(directory,imgname);

        FileOutputStream fos = null;
        try {
            System.err.println("I m in save Internal storage function try block");
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            System.err.println("I m in save Internal storage function catch block");
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath()+"/"+imgname;
    }


}