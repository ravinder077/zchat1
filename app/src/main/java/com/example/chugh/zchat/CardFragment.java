package com.example.chugh.zchat;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import static android.content.ContentValues.TAG;


public class CardFragment extends Fragment {



    ConnDB connDB=new ConnDB();
    String[] par={"hello","jello"};
    ArrayList<CardsData> al=new ArrayList<CardsData>();



    ArrayList<WonderModel> listitems = new ArrayList<>();
    RecyclerView MyRecyclerView;
   String Wonders[] = {"Chichen Itza","Christ the Redeemer","Great Wall of China","Machu Picchu","Petra","Taj Mahal","Colosseum"};
    int  Images[] = {R.drawable.chichen_itza,R.drawable.christ_the_redeemer,R.drawable.great_wall_of_china,R.drawable.machu_picchu,R.drawable.petra,R.drawable.taj_mahal,R.drawable.colosseum};



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ConnDBB cbb=new ConnDBB();

        cbb.execute();

        al= bindGridview();



        initializeList(al);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_card, container, false);







        MyRecyclerView = (RecyclerView) view.findViewById(R.id.cardView);
        MyRecyclerView.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
        MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if (listitems.size() > 0 & MyRecyclerView != null) {
            MyRecyclerView.setAdapter(new MyAdapter(listitems));
        }
        MyRecyclerView.setLayoutManager(MyLayoutManager);

        return view;
    }




    public ArrayList<CardsData> bindGridview()
    {

        ConnDB connDB=new ConnDB();
        String[] par={"hello","jello"};
        connDB.execute(par);
        ArrayList<CardsData> al=new ArrayList<CardsData>();
        try {


            // ArrayList<String> ls=new ArrayList<String>();

            System.err.println();
            System.err.println(connDB.get());
            JSONObject jsonObj = new JSONObject(connDB.get());
           al= jsonToMap(jsonObj.toString());

            ConnDBPhoto cdp=new ConnDBPhoto();
            cdp.execute(al);

            al=cdp.get();

            System.err.println("i m inside login activity");

             /*   for (String temp : ls) {
                    System.err.println("i m inside login activity lioop");
                    System.err.println(temp);
                }*/



            //et.setText("done");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return al;
    }



    public static ArrayList<CardsData> jsonToMap(String jsonStr) throws JSONException {


        HashMap<String, String> contact = new HashMap<>();

        ArrayList<CardsData> al=new ArrayList<CardsData>();

        if (jsonStr != null) {

            System.err.print("josn strin is");

            System.err.print(jsonStr);

            System.err.print("josn strin ends");



            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray contacts = jsonObj.getJSONArray("tasks");

                // looping through All Contacts
                JSONArray c = jsonObj.getJSONArray("tasks");
                for (int i = 0 ; i < c.length(); i++) {
                    JSONObject obj = c.getJSONObject(i);
                    String A = obj.getString("id");
                    String B = obj.getString("name");
                    String C = obj.getString("img");
                    System.out.println(A + " " + B + " " + C);

                    CardsData cc=new CardsData();

                    cc.setId(A);
                    cc.setName(B);
                    cc.setImg(C);

                // Bitmap bb=   getBitmapfromUrl(C);
                    cc.setImgg(null);





                    al.add(cc);



                }

               for (CardsData cd:al) {
                    String id = cd.getId();
                    String name = cd.getName();
                   String img  =  cd.getImg();



                    System.err.println("name inside "+name  +"  values inside "+img );



                }



            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());


            }
        }

        return  al;
    }


    public class ConnDB extends AsyncTask<String,String,String>

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
        protected String doInBackground(String... params) {

            ArrayList<String> ls=new ArrayList<String>();
            String st=null;
            System.out.println(params[0]);
            System.out.println(params[1]);
            try  {

                StringBuffer chaine = new StringBuffer("");



                try {
                    URL url = new URL("http://omtii.com/mile/text.php");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestProperty("User-Agent", "");
                    connection.setRequestMethod("GET");
                    connection.connect();

                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    st=result.toString();
                    //  ls.add(result.toString());
                    // Toast(,"",Toast.LENGTH_LONG).Show();
                    // Toast.makeText(LoginActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
                    System.err.println(result.toString());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                //Your code goes here
            } catch (Exception e) {
                e.printStackTrace();
            }
            return st;
        }

        protected void onPostExecute(String result) {


            // super.onPostExecute(result);
            //done(result);
            // Toast.makeText(this, result.toString(), Toast.LENGTH_SHORT).show();

           //progressDialog.hide();

        }



    }



    public class ConnDBPhoto extends AsyncTask<ArrayList<CardsData>,String,ArrayList<CardsData>>

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
        protected ArrayList<CardsData> doInBackground(ArrayList<CardsData>... params) {

            ArrayList<CardsData> ls=new ArrayList<CardsData>();
            ArrayList<CardsData> ls1=params[0];
            String st=null;

           for(CardsData cd:ls1)
           {
           String id=  cd.getId();
           String name=    cd.getName();
           String img=    cd.getImg();

               Bitmap imgg=  cd.getImgg();


               imgg=  getBitmapfromUrl(img);


             CardsData cc=new CardsData();
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

        protected void onPostExecute(ArrayList<CardsData> result) {


            // super.onPostExecute(result);
            //done(result);
            // Toast.makeText(this, result.toString(), Toast.LENGTH_SHORT).show();
           // progressDialog.hide();


        }



    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private ArrayList<WonderModel> list;

        public MyAdapter(ArrayList<WonderModel> Data) {
            list = Data;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
            // create a new view
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycle_items, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {

            holder.titleTextView.setText(list.get(position).getCardName());

            //holder.coverImageView.setImageResource(R.drawable.great_wall_of_china);

           holder.coverImageView.setImageBitmap(list.get(position).getImg());
            holder.coverImageView.setTag(list.get(position).getImageResourceId());
            holder.likeImageView.setTag(R.drawable.ic_like);

        }

       @Override
        public int getItemCount() {
            return list.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTextView;
        public ImageView coverImageView;
        public ImageView likeImageView;
        public ImageView shareImageView;

        public MyViewHolder(View v) {
            super(v);
            titleTextView = (TextView) v.findViewById(R.id.titleTextView);
            coverImageView = (ImageView) v.findViewById(R.id.coverImageView);
            likeImageView = (ImageView) v.findViewById(R.id.likeImageView);
            shareImageView = (ImageView) v.findViewById(R.id.shareImageView);
            likeImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    int id = (int)likeImageView.getTag();
                        if( id == R.drawable.ic_like){

                            likeImageView.setTag(R.drawable.ic_liked);
                            likeImageView.setImageResource(R.drawable.ic_liked);

                            Toast.makeText(getActivity(),titleTextView.getText()+" added to favourites",Toast.LENGTH_SHORT).show();

                        }else{

                            likeImageView.setTag(R.drawable.ic_like);
                            likeImageView.setImageResource(R.drawable.ic_like);
                            Toast.makeText(getActivity(),titleTextView.getText()+" removed from favourites",Toast.LENGTH_SHORT).show();


                        }

                }
            });



            shareImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {






                    Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                            "://" + getResources().getResourcePackageName(coverImageView.getId())
                            + '/' + "drawable" + '/' + getResources().getResourceEntryName((int)coverImageView.getTag()));


                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_STREAM,imageUri);
                    shareIntent.setType("image/jpeg");
                    startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));



                }
            });



        }
    }

    public void initializeList( ArrayList<CardsData> al) {
        listitems.clear();

       for(CardsData cd:al)
       {

            WonderModel item = new WonderModel();
            item.setCardName(cd.getName());
           item.setImg(cd.imgg);
            item.setImageResourceId(cd.getImg());
            item.setIsfav(0);
            item.setIsturned(0);
            listitems.add(item);

        }




    }







}
