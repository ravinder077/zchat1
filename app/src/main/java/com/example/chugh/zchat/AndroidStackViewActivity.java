package com.example.chugh.zchat;

import android.app.Activity;
import android.os.Bundle;
import android.widget.StackView;

import java.util.ArrayList;

/**
 * Created by Rajan on 7/7/2017.
 */
public class AndroidStackViewActivity extends Activity {
    private static StackView stackView;

    private static ArrayList list;


    private static final Integer[] icons = {R.drawable.groupdp4, R.drawable.groupdp2,

            R.drawable.groupdp3, R.drawable.groupdp4};



    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);



        stackView = (StackView) findViewById(R.id.stack_view);

        list = new ArrayList();



        for (int i = 0; i < icons.length; i++) {

            list.add(new StackItems("Item " + i, icons[i]));

        }








        StackAdapter adapter = new StackAdapter(AndroidStackViewActivity.this, list);

        stackView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

    }

}