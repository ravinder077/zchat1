package com.example.chugh.zchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ravinder077 on 29-06-2017.
 */

public class TabA extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.taba, container, false);
        //getContext().startService(new Intent(this.getActivity(), PhotoUpdateService.class));

        return view;
    }
}