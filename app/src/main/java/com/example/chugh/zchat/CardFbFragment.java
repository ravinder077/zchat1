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

public class CardFbFragment extends Fragment {


    RecyclerView MyRecyclerView;
    ArrayList<CardFbData> listitems = new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toast.makeText(getContext(), "CardGroupFragment", Toast.LENGTH_SHORT).show();

        ArrayList<CardFbData> al = new ArrayList<>();

        CardFbData cdg=new CardFbData();

        cdg.setId("1");
        cdg.setProfileName("My Group");

        //cdg.setImg(R.drawable.taj_mahal);
        cdg.setImg("http://staticmain.narendramodi.in/images/nmApp_img.png");

        al.add(cdg);

        CardFbData cdg1=new CardFbData();

        cdg1.setId("2");
        cdg1.setProfileName("Love life with Fun");
        //cdg.setImg(R.drawable.taj_mahal);
        cdg1.setImg("https://s-media-cache-ak0.pinimg.com/736x/3d/c9/a9/3dc9a99f5441b38a780294044c7dd220.jpg");
        al.add(cdg1);
        CardFbData cdg2=new CardFbData();

        cdg2.setId("2");
        cdg2.setProfileName("Modi ZindaBAD");
        //cdg.setImg(R.drawable.taj_mahal);
        cdg2.setImg("https://cdn.pixabay.com/photo/2013/10/16/14/45/flower-196360__340.jpg");
        al.add(cdg2);
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


        View view = inflater.inflate(R.layout.fragment_group_fb, container, false);



        MyRecyclerView = (RecyclerView) view.findViewById(R.id.cardViewFb);
        MyRecyclerView.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
        MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if (listitems.size() > 0 & MyRecyclerView != null) {
            MyRecyclerView.setAdapter(new MyAdapter(listitems));
        }
        MyRecyclerView.setLayoutManager(MyLayoutManager);

        return view;
    }


    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private ArrayList<CardFbData> list;

        public MyAdapter(ArrayList<CardFbData> Data) {
            list = Data;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycle_item_fb, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {

            holder.titleTextView.setText(list.get(position).getProfileName());

            //holder.coverImageView.setImageResource(R.drawable.great_wall_of_china);

            holder.coverImageView.setImageBitmap(list.get(position).getImggg());
         //   holder.coverImageView.setTag(list.get(position).getImageResourceId());

            holder.coverImageView1.setImageBitmap(list.get(position).getImggg());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTextView;
        public ImageView coverImageView;
        public ImageView coverImageView1;


        public MyViewHolder(View v) {
            super(v);
            titleTextView = (TextView) v.findViewById(R.id.ProfileName);
            coverImageView = (ImageView) v.findViewById(R.id.img);
            coverImageView1=(ImageView) v.findViewById(R.id.mainimg);

            coverImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(getContext(), titleTextView.getText(), Toast.LENGTH_SHORT).show();
                }
            });





        }
    }


    public void initializeList( ArrayList<CardFbData> al) {
        listitems.clear();

        for(CardFbData cd:al)
        {

            CardFbData item = new CardFbData();
            item.setId(cd.getId());
            item.setImg(cd.getImg());
            item.setImggg(cd.getImggg());
            item.setProfileName(cd.getProfileName());
            listitems.add(item);

        }




    }


    public class ConnDBPhoto extends AsyncTask<ArrayList<CardFbData>,String,ArrayList<CardFbData>>

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
        protected ArrayList<CardFbData> doInBackground(ArrayList<CardFbData>... params) {

            ArrayList<CardFbData> ls=new ArrayList<CardFbData>();
            ArrayList<CardFbData> ls1=params[0];
            String st=null;

            for(CardFbData cd:ls1)
            {
                String id=  cd.getId();
                String name=    cd.getProfileName();
                String img=    cd.getImg();

                Bitmap imgg=  cd.getImggg();


                imgg=  getBitmapfromUrl(img);


                CardFbData cc=new CardFbData();
                cc.setId(id);
                cc.setProfileName(name);
                cc.setImg(img);
                cc.setImggg(imgg);

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

        protected void onPostExecute(ArrayList<CardFbData> result) {


            // super.onPostExecute(result);
            //done(result);
            // Toast.makeText(this, result.toString(), Toast.LENGTH_SHORT).show();
            // progressDialog.hide();


        }



    }

}
