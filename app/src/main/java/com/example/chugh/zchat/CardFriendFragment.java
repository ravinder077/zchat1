package com.example.chugh.zchat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.SYSTEM_HEALTH_SERVICE;

/**
 * Created by ravinder077 on 04-07-2017.
 */

public class CardFriendFragment extends Fragment {

    TextView nf;
    String name;
    RecyclerView MyRecyclerView;
    static MyAdapter adapter;
    ArrayList<FriendData> listitems = new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toast.makeText(getContext(), "CardGroupFragment", Toast.LENGTH_SHORT).show();

        ArrayList<FriendData> al = new ArrayList<>();


        Cursor phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        SQLiteDatabase mydata = getActivity().openOrCreateDatabase("DM", MODE_PRIVATE, null);




        Cursor resultSet = mydata.rawQuery("Select * from contact group by cnumber order by cname", null);
        resultSet.moveToFirst();
        while (resultSet.moveToNext()) {
            FriendData fd = new FriendData();
            name = resultSet.getString(1);
            String imgUrl=resultSet.getString(3);

            String mobile = resultSet.getString(2);
            String cphoto=resultSet.getString(3);
            System.err.println("cphoto" + cphoto);
            fd.setImage(cphoto);
            fd.setName(name);
            fd.setContact(mobile.replaceAll("\\s",""));

            fd.setImage(imgUrl);



            //  fd.setContact(mobile);
          //  fd.setImg(R.drawable.great_wall_of_china);

            al.add(fd);


        }
        initializeList(al);

        // System.err.println("contact ="+ ls);

        // al.add(fd);
       /* ConnDBPhoto cdp = new ConnDBPhoto();
        cdp.execute(al);
        try {
            al = cdp.get();
            initializeList(al);
        } catch (Exception e) {
            e.printStackTrace();
        }*/


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_friend, container, false);





        MyRecyclerView = (RecyclerView) view.findViewById(R.id.cardViewGroup);
        MyRecyclerView.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
        MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

       adapter= new CardFriendFragment.MyAdapter(listitems);

        if (listitems.size() > 0 & MyRecyclerView != null) {
            MyRecyclerView.setAdapter(adapter);
        }
        MyRecyclerView.setLayoutManager(MyLayoutManager);



        return view;
    }




    public void myFilter(String d)
    {
        adapter.getFilter().filter(d);

        //new CardFriendFragment.MyAdapter(listitems).getFilter().filter(d);
    }



    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> implements Filterable {
        private ArrayList<FriendData> list;
        private ArrayList<FriendData> list2;

        public MyAdapter(ArrayList<FriendData> Data) {
            list = Data;
            list2=Data;
        }

        @Override
        public CardFriendFragment.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycle_items_friend, parent, false);
            CardFriendFragment.MyViewHolder holder = new CardFriendFragment.MyViewHolder(view);
         //  nf =(TextView) view.findViewById(R.id.txtnfirst);

            return holder;
        }

        @Override
        public void onBindViewHolder(final CardFriendFragment.MyViewHolder holder, int position) {

            holder.titleTextView.setText(list.get(position).getName());

            // holder.coverImageView.setImageResource(R.drawable.great_wall_of_china);
            //  holder.coverImageView.setImageResource(R.drawable.user2);

            //   holder.coverImageView.setTag(list.get(position).getImageResourceId());

            // nf.setText(list.get(position).getName().substring(0,1));
          //  if (list.get(position).getImage() == null) {
             /*  holder.ImgTextView.setText(list.get(position).getName().substring(0, 1));

           // } else {
               // holder.ImgTextView.setVisibility(View.INVISIBLE);
                //holder.coverImageView.setImageBitmap(list.get(position).getBimg());
                   File file=new File(list.get(position).getImage());

                System.err.println("list.get(position).getImage()"+list.get(position).getImage());
                  //holder.coverImageView.setImageBitmap(list.get(position).getBimg());



                              System.err.println("file.getAbsolutePath()"+file.getAbsolutePath());
                              Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                              holder.coverImageView.setImageBitmap(bitmap);







           // }*/





            File imgFile = new  File(list.get(position).getImage());

            System.err.println("list.get(position).getImage()"+list.get(position).getImage());
            if(imgFile.exists()){

                System.err.println("i m if");
                System.err.println("imgFile.getAbsolutePath()"+imgFile.getAbsolutePath());
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                // ImageView myImage = (ImageView) findViewById(R.id.imageviewTest);

                //  myImage.setImageBitmap(myBitmap);
                holder.coverImageView.setVisibility(View.VISIBLE);
                holder.ImgTextView.setVisibility(View.INVISIBLE);
                holder.coverImageView.setImageBitmap(myBitmap);

            }
            else
            {
                System.err.println("i m else");
                holder.ImgTextView.setVisibility(View.VISIBLE);
                holder.coverImageView.setVisibility(View.INVISIBLE);
                holder.ImgTextView.setText(list.get(position).getName().substring(0, 1));
            }

        }




        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public Filter getFilter() {
            return FilterHelper.newInstance(list2,this);
        }
        public void setSpacecrafts(ArrayList<FriendData> fd)
        {
            this.list=fd;
        }
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTextView;
        public ImageView coverImageView;
        public TextView ImgTextView;


        public MyViewHolder(View v) {
            super(v);
            titleTextView = (TextView) v.findViewById(R.id.grouptxt);
            coverImageView = (ImageView) v.findViewById(R.id.groupimg);
            ImgTextView=(TextView) v.findViewById(R.id.txtnfirst);
            coverImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(getContext(), titleTextView.getText(), Toast.LENGTH_SHORT).show();
                }
            });





        }
    }


    public void initializeList( ArrayList<FriendData> al) {
        listitems.clear();

        for(FriendData cd:al)
        {

            FriendData item = new FriendData();
            item.setId(cd.getId());
            item.setImg(cd.getImg());
            item.setBimg(cd.getBimg());
            item.setImage(cd.getImage());

            System.err.println("cd.getImage()" + cd.getImage());
           // item.setImgg(cd.getImgg());
            item.setName(cd.getName());
            listitems.add(item);

        }




    }


    public class ConnDBPhoto extends AsyncTask<ArrayList<FriendData>,String,ArrayList<FriendData>>

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
        protected ArrayList<FriendData> doInBackground(ArrayList<FriendData>... params) {

            ArrayList<FriendData> ls=new ArrayList<FriendData>();
            ArrayList<FriendData> ls1=params[0];
            String st=null;







            for(FriendData cd:ls1)
            {
                String id=  cd.getId();
                String name=    cd.getName();
                String no=cd.getContact();
                Bitmap imgg=null;
                String img=cd.getImage();
              //  String img=    cd.getImg();

              //  Bitmap imgg=  cd.getImgg();
           /*String path="http://omtii.com/mile/selectnum.php?id="+no;
                      try {
                              URL url = new URL(path);
                               System.err.println("url" + url);
                             HttpURLConnection con = (HttpURLConnection) url.openConnection();
                          con.setRequestProperty("User-Agent", "");
                          con.setRequestMethod("GET");

                          con.connect();

                          InputStream in = con.getInputStream();
                          BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                          StringBuilder result = new StringBuilder();
                          String line;
                          while ((line = reader.readLine()) != null) {
                              result.append(line);
                          }

                          st = result.toString();
                      }
                      catch(Exception ee)
                      {
                          ee.printStackTrace();

                      }
                     imgg=  getBitmapfromUrl(st);*/


                FriendData cc=new FriendData();
                cc.setId(id);
                cc.setName(name);
                cc.setBimg(imgg);

                  cc.setImage(img);

              //  cc.setImg(img);
              //  cc.setImgg(imgg);

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

        protected void onPostExecute(ArrayList<FriendData> result) {


            // super.onPostExecute(result);
            //done(result);
            // Toast.makeText(this, result.toString(), Toast.LENGTH_SHORT).show();
            // progressDialog.hide();


        }



    }

}
