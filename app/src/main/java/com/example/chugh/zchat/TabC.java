package com.example.chugh.zchat;

/**
 * Created by ravinder077 on 29-06-2017.
 */

import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.R.attr.fragment;
import static android.R.id.list;
import static android.content.Context.MODE_PRIVATE;
import static com.example.chugh.zchat.R.layout.tabc;


public class TabC extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(tabc, container, false);

        List<FriendData> ls = new ArrayList<FriendData>();

        Cursor phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        SQLiteDatabase mydata=getActivity().openOrCreateDatabase("DM",MODE_PRIVATE,null);

        try {


            Cursor resultSet = mydata.rawQuery("Select * from contact", null);
            resultSet.moveToFirst();
            while(resultSet.moveToNext()) {
                FriendData fd=new FriendData();
                String name = resultSet.getString(1);
                String mobile = resultSet.getString(2);
                fd.setName(name);
              //  fd.setContact(mobile);
                fd.setImg(R.drawable.great_wall_of_china);

                ls.add(fd);


            }
            System.err.println("contact ="+ ls);


            ArrayList<String> a12=new ArrayList<String>();
     ArrayList<Integer> a13=new ArrayList<Integer>();
            for (FriendData fd:ls)
            {
               a12.add(fd.getName());
               a13.add(fd.getImg());

            }

          //  System.err.println("111111111111111111111" + resultSet.getCount());
            // Toast.makeText(v.getContext(), name, Toast.LENGTH_SHORT).show();
                //    Toast.makeText(v.getContext(), mobile, Toast.LENGTH_SHORT).show();
    class MyAdapter extends ArrayAdapter
    {
        public MyAdapter(Context c,int r,int fid,List fd)
        {
            super(c,r,fid,fd);
        }
        @Override
        public int getCount()
        {
            return super.getCount();
        }
      /*  @Override
        public View getView(int p,View cv,ViewGroup vg)
        {
           return super.getView(p,cv,vg);
        }*/

    }
    MyAdapter myadp=new MyAdapter(getContext(),R.layout.mylist,R.id.Itemname,ls);
            ListView simplelist=(ListView) view.findViewById(R.id.list2);

            simplelist.setAdapter(myadp);
       /*  setListAdapter(new ArrayAdapter<Integer>(
                    getContext(), R.layout.mylist,
                    R.id.Itemname,a13));*/


        }
        catch (Exception ee)
        {
            // ee.printStackTrace();
            Toast.makeText(getContext(), ee.getMessage(), Toast.LENGTH_SHORT).show();

        }

        return view;
    }
}



/*
public class TabC extends ListActivity {

    String[] itemname ={
            "Safari",
            "Camera",
            "Global",
            "FireFox",
            "UC Browser",
            "Android Folder",
            "VLC Player",
            "Cold War"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabc);

        this.setListAdapter(new ArrayAdapter<String>(
                this, R.layout.mylist,
                R.id.Itemname,itemname));
    }
}*/
