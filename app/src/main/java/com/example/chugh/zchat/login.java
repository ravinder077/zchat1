package com.example.chugh.zchat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

/**
 * Created by Chugh on 6/30/2017.
 */

public class login extends AppCompatActivity {


    private int MY_PERMISSIONS_REQUEST_SEND_SMS;

    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.login);



        SQLiteDatabase mydata=openOrCreateDatabase("DM",MODE_PRIVATE,null);
        mydata.execSQL("create table if not exists new_user(name varchar,mobile varchar,photo varchar);");
        Cursor resultSet = mydata.rawQuery("Select * from new_user", null);
        if(resultSet.getCount()>0) {
            resultSet.moveToFirst();

            String mnum = resultSet.getString(1);

            if (mnum != null) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }

        }

        // Prompt for send sms Permission start

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            }


            else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);


            }
        }

        // Prompt for send sms Permission end



        final EditText etmob =(EditText) findViewById(R.id.etmob);
        final Button button = (Button) findViewById(R.id.btnsotp);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                if(etmob.getText().toString().length()==0)
                {
                    etmob.setError("Mobile Number is Required");
                }
                else

                {
                    OtpGen otpg=new OtpGen();
                    String numurl="http://omtii.com/mile/otpgen.php?id="+etmob.getText();

                    System.err.println("numurl"+numurl);

                    otpg.execute(numurl);
                    String st=null;
                    try {
                         st=otpg.get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    //Toast.makeText(login.this, st, Toast.LENGTH_SHORT).show();

                    SmsManager sms = SmsManager.getDefault();
                    String mobno=etmob.getText().toString();
                    if(st==null)
                    {
                        /*String mess="Please Connect your Device with Internet";
                        sms.sendTextMessage(mobno, null,mess , null, null);*/

                       // Toast.makeText(login.this, "Please Wait", Toast.LENGTH_SHORT).show();
                        etmob.setError("Please connect your Device With Internet");

                    }
                    else {


                        String mess = "Your One Time Password (OTP) for Direct Message is " + st + ". Only valid for 20 min.";
                        sms.sendTextMessage(mobno, null,mess , null, null);
                        Intent i = new Intent(v.getContext(), otp.class);
                        i.putExtra("sotp",st);
                        i.putExtra("mobno",mobno);
                        startActivity(i);
                    }





                }
            }
        });


    }





}
