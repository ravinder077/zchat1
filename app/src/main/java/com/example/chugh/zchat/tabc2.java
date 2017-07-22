package com.example.chugh.zchat;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ravinder077 on 04-07-2017.
 */

public class tabc2 extends Fragment {


    RecyclerView MyRecyclerView;
    ArrayList<CardDataGroup> listitems = new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toast.makeText(getContext(), "CardGroupFragment", Toast.LENGTH_SHORT).show();

        ArrayList<CardDataGroup> al = new ArrayList<>();

        CardDataGroup cdg=new CardDataGroup();

        cdg.setId("1");
        cdg.setName("My Group");
        //cdg.setImg(R.drawable.taj_mahal);
        cdg.setImg("http://staticmain.narendramodi.in/images/nmApp_img.png");

        al.add(cdg);

        CardDataGroup cdg1=new CardDataGroup();

        cdg1.setId("2");
        cdg1.setName("Love life with Fun");
        //cdg.setImg(R.drawable.taj_mahal);
        cdg1.setImg("https://s-media-cache-ak0.pinimg.com/736x/3d/c9/a9/3dc9a99f5441b38a780294044c7dd220.jpg");
        al.add(cdg1);
        ConnDBPhoto cdp=new ConnDBPhoto();
        cdp.execute(al);
        try {
            al=cdp.get();
            initializeList(al);
        } catch (Exception e) {
            e.printStackTrace();
        }



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_group, container, false);



        MyRecyclerView = (RecyclerView) view.findViewById(R.id.cardViewGroup);
        MyRecyclerView.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
        MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if (listitems.size() > 0 & MyRecyclerView != null) {
            MyRecyclerView.setAdapter(new tabc2.MyAdapter(listitems));
        }
        MyRecyclerView.setLayoutManager(MyLayoutManager);

        return view;
    }


    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private ArrayList<CardDataGroup> list;

        public MyAdapter(ArrayList<CardDataGroup> Data) {
            list = Data;
        }

        @Override
        public tabc2.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycle_items_group, parent, false);
            tabc2.MyViewHolder holder = new tabc2.MyViewHolder(view);
            return holder;
        }


        @Override
        public void onBindViewHolder(final tabc2.MyViewHolder holder, int position) {

            holder.titleTextView.setText(list.get(position).getName());

            //holder.coverImageView.setImageResource(R.drawable.great_wall_of_china);

            holder.coverImageView.setImageBitmap(list.get(position).getImgg());
            //   holder.coverImageView.setTag(list.get(position).getImageResourceId());


        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTextView;
        public ImageView coverImageView;


        public MyViewHolder(View v) {
            super(v);
            titleTextView = (TextView) v.findViewById(R.id.grouptxt);
            coverImageView = (ImageView) v.findViewById(R.id.groupimg);

            coverImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(getContext(), titleTextView.getText(), Toast.LENGTH_SHORT).show();
                }
            });





        }
    }


    public void initializeList( ArrayList<CardDataGroup> al) {
        listitems.clear();

        for(CardDataGroup cd:al)
        {

            CardDataGroup item = new CardDataGroup();
            item.setId(cd.getId());
            item.setImg(cd.getImg());
            item.setImgg(cd.getImgg());
            item.setName(cd.getName());
            listitems.add(item);

        }




    }


    public class ConnDBPhoto extends AsyncTask<ArrayList<CardDataGroup>,String,ArrayList<CardDataGroup>>

    {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setCancelable(true);
            progressDialog.setMessage("Loading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setProgress(0);
            progressDialog.show();
        }

        @Override
        protected ArrayList<CardDataGroup> doInBackground(ArrayList<CardDataGroup>... params) {

            ArrayList<CardDataGroup> ls=new ArrayList<CardDataGroup>();
            ArrayList<CardDataGroup> ls1=params[0];
            String st=null;

            for(CardDataGroup cd:ls1)
            {
                String id=  cd.getId();
                String name=    cd.getName();
                String img=    cd.getImg();

                Bitmap imgg=  cd.getImgg();


                imgg=  getBitmapfromUrl(img);


                CardDataGroup cc=new CardDataGroup();
                cc.setId(id);
                cc.setName(name);
                cc.setImg(img);
                cc.setImgg(imgg);

                ls.add(cc);

            }



            return ls;

        }


        protected  Bitmap getBitmapfromUrl(String imageUrl)
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

        protected void onPostExecute(ArrayList<CardDataGroup> result) {


            // super.onPostExecute(result);
            //done(result);
            // Toast.makeText(this, result.toString(), Toast.LENGTH_SHORT).show();
            // progressDialog.hide();


        }



    }

}
