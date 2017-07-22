package com.example.chugh.zchat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Chugh on 7/1/2017.
 */

public class User_Name extends AppCompatActivity {
    private int MY_PERMISSIONS_REQUEST_READ_WRITE_EXTERNAL_STORAGE;
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.user_name);

        Intent intent=getIntent();
        String mobno=intent.getStringExtra("mobno");
        System.err.println("mobile" +  mobno);

        // create table for contacts start

        SQLiteDatabase mydata=openOrCreateDatabase("DM",MODE_PRIVATE,null);

        mydata.execSQL("create table if not exists contact(pid varchar,cname varchar,cnumber varchar,cphoto varchar);");


        // create table for contacts end

        // Access Contact start
        System.err.println("before loop before");
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        System.err.println("before loop");
        while (phones.moveToNext()) {

           // System.err.println("in loop");
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

          /*  System.err.println("name " + name);
            System.err.println("number" + phoneNumber);*/

            mydata.execSQL("insert or replace into contact values ('"+mobno+"','"+name+"','"+phoneNumber+"','null')");

          /*  OtpGen otpg=new OtpGen();
            String numurl="http://omtii.com/mile/contact_app.php?pid="+mobno+"&cname="+name.replaceAll("\\s","")+"&mobile="+phoneNumber.replaceAll("\\s","");

            System.err.println("numurl"+numurl);

            otpg.execute(numurl);*/

        }
        // Access contacts end


        // Printing start

                     /*   Cursor resultSet = mydata.rawQuery("Select * from contact", null);
                        resultSet.moveToFirst();

                        String pid = resultSet.getString(0);
                        String cname = resultSet.getString(1);
                        String cno = resultSet.getString(2);

                        System.err.println("pid " + pid);
                        System.err.println("cname" + cname);
                        System.err.println("cno" + cno);*/

        //printing end

        // Prompt for external storage Permission start

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            }


            else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_READ_WRITE_EXTERNAL_STORAGE);


            }
        }

        // Prompt for  external storage Permission end


        final EditText etname=(EditText) findViewById(R.id.etname);
        final Button btnncont = (Button) findViewById(R.id.btnncont);
        btnncont.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                if(etname.getText().toString().length()==0)
                {
                    etname.setError("Name is Required");
                }
                else
                 {
                     Intent intent=getIntent();
                     String mobno=intent.getStringExtra("mobno");
                     String uname=etname.getText().toString();
                    Intent i = new Intent(v.getContext(), Upload_Photo.class);
                     i.putExtra("uname",uname);
                     i.putExtra("mobno",mobno);
                    startActivity(i);
                }
            }
        });
    }

}
