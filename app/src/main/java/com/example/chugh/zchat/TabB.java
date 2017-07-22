package com.example.chugh.zchat;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ravinder077 on 29-06-2017.
 import android.os.Bundle;
 import android.support.v4.app.Fragment;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;

 /**
 * Created by ravinder077 on 29-06-2017.
 */

public class TabB extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.tabb, container, false);

       // getContext().startService(new Intent(getContext(), PhotoUpdateService.class));
        ImageView imageView=(ImageView)view.findViewById(R.id.subimg);

        String  pathName="/data/user/0/com.example.chugh.zchat/app_imageDir/upic.jpg";


        if(pathName!=null && pathName!="")
        {
            File f = new File(pathName);
            if(f.exists())
            {
                Drawable d = Drawable.createFromPath(pathName);
                imageView.setImageDrawable(d);

            }
        }
       // imageView.setImageBitmap(BitmapFactory.decodeFile("/data/user/0/com.example.chugh.zchat/app_imageDir/upic.jpg"));


        //getContext().startService(new Intent(view.getContext(), PhotoUpdateService.class));

        SQLiteDatabase mydata = getActivity().openOrCreateDatabase("DM", MODE_PRIVATE, null);
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


        return view;
        }

    }


