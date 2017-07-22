package com.example.chugh.zchat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

/**
 * Created by Chugh on 7/3/2017.
 */

public class test extends AppCompatActivity {


    private int MY_PERMISSIONS_REQUEST_SEND_SMS;

    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.login);
        startService(new Intent(getBaseContext(), PhotoUpdateService.class));
        System.err.println("in Resend button click ");

        OtpGen otpg=new OtpGen();
        String numurl="http://omtii.com/mile/otpgen.php?id=8360313194";

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
        String mess="Your One Time Password (OTP) for Direct Message is " + st + ". Only valid for 20 min.";

       // String mobno=etmob.getText().toString();
        sms.sendTextMessage("8360313194", null,mess , null, null);
    }
}