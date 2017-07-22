package com.example.chugh.zchat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Rajan on 7/7/2017.
 */

public class StackAdapter extends BaseAdapter
{
        ArrayList arrayList;
        LayoutInflater inflater;
        ViewHolder holder = null;

        public StackAdapter(Context context, ArrayList arrayList)
        {
            this.arrayList = arrayList;
            this.inflater = LayoutInflater.from(context);
        }

        @Override

        public int getCount()
        {
            return arrayList.size();
        }

        @Override

        public StackItems getItem(int pos)
        {
            return (StackItems) arrayList.get(pos);
        }

        @Override

        public long getItemId(int pos)
        {
            return pos;
        }

        @Override

        public View getView(int pos, View view, ViewGroup parent)
        {
            if (view == null)
            {
                view = inflater.inflate(R.layout.item, parent, false);

                holder = new ViewHolder();

                holder.image = (ImageView) view.findViewById(R.id.image);



                view.setTag(holder);
            }

            else
            {
                holder = (ViewHolder) view.getTag();
            }





            holder.image.setBackgroundResource(((StackItems)arrayList.get(pos)).getImage());

            return view;
        }


        public class ViewHolder {

            ImageView image;
            TextView text;
        }
}

