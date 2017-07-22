package com.example.chugh.zchat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Chugh on 7/1/2017.
 */

public class Upload_Photo extends AppCompatActivity {
    private static final int RESULT_LOAD_IMAGE = 0;
    //keep track of cropping intent
    final int PIC_CROP = 2;
    protected Upload_Photo CameraActivity = null;
    protected static final int IMAGE_CAPTURE = 102;
    protected Uri picuri;
    private int MY_PERMISSIONS_REQUEST_CAMERA;

    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.upload_photo);
       // startService(new Intent(getBaseContext(), PhotoUpdateService.class));

        // Prompt for camera Permission start

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            }


            else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);


            }
        }

        // Prompt for camera Permission end


        Button btntimg = (Button) findViewById(R.id.btntimg);

        btntimg.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    intent.putExtra("android.intent.extras.CAMERA_FACING", 1);

                    startActivityForResult(intent, IMAGE_CAPTURE);


                } catch (ActivityNotFoundException anfe) {
                    //display an error message
                    String errorMessage = "Whoops - your device doesn't support capturing images!";
                    Toast toast = Toast.makeText(Upload_Photo.this, errorMessage, Toast.LENGTH_SHORT);
                    toast.show();
                }

            }


        });


        Button btnctn = (Button) findViewById(R.id.btnctn);
        btnctn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);


            }
            }     );


        Button btncimg = (Button) findViewById(R.id.btncimg);
        btncimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    Intent i = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                    System.err.println("Image Data" + i.getDataString());

                }
                catch(Exception exp)
                {
                    String errorMessage = "Whoops - your device doesn't support Gallery Feature!";
                    Toast toast = Toast.makeText(Upload_Photo.this, errorMessage, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {


        if (resultCode == RESULT_OK) {

            Bitmap b;

            if(requestCode == IMAGE_CAPTURE || requestCode==RESULT_LOAD_IMAGE) {

                try
                {
                if(data.getData()==null)
                {

                    b=(Bitmap) data.getExtras().get("data");
                 }
                 else
                {

                    b=MediaStore.Images.Media.getBitmap(this.getContentResolver(),data.getData());
                }

                    ImageView picView = (ImageView)findViewById(R.id.imguser);
                    picView.setImageBitmap(b);
                    super.onActivityResult(requestCode, resultCode, data);

                    // call to "saveToInternalStorage" function
                    String saveimg = saveToInternalStorage(b);

                    FileUpload fup=new FileUpload();
                    fup.execute(saveimg);
                    System.err.println("imageName1"+saveimg);


                    String fupget=fup.get();
                    System.err.println("fupget" + fupget);

                    Intent intent=getIntent();

                    String mobno=intent.getStringExtra("mobno");
                    String uname=intent.getStringExtra("uname");


                    SQLiteDatabase mydata=openOrCreateDatabase("DM",MODE_PRIVATE,null);

                    mydata.execSQL("create table if not exists new_user(name varchar,mobile varchar,photo varchar);");

                    mydata.execSQL("insert into new_user values ('"+uname+"','"+mobno+"','"+b+"')");



                    OtpGen otpg=new OtpGen();


                    String numurl="http://omtii.com/mile/savedata.php?uname="+uname+"&umob="+mobno+"&uimg="+fupget;

                    System.err.println("save img"+saveimg);

                    otpg.execute(numurl);

                    System.err.println("numurl" + numurl);





                    // Printing start

                    Cursor resultSet = mydata.rawQuery("Select * from new_user", null);
                    resultSet.moveToFirst();

                    String username = resultSet.getString(0);
                    String mobileno = resultSet.getString(1);
                    String photo = resultSet.getString(2);

                   System.err.println("user name " + username);
                    System.err.println("mobileno" + mobileno);
                    System.err.println("photo" + photo);

                    //printing end
            }
            catch(Exception e1)
                {
                   e1.printStackTrace();
                }
            }




        }
    }

    private String saveToInternalStorage(Bitmap bitmapImage){
        System.err.println("I m in save Internal storage function");
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        String imgname="upic.jpg";
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