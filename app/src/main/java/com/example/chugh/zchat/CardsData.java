package com.example.chugh.zchat;

import android.graphics.Bitmap;

/**
 * Created by ravinder077 on 01-07-2017.
 */

public class CardsData
{

    String id;
    String name;
    String img;
    Bitmap imgg;


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Bitmap getImgg() {
        return imgg;
    }

    public void setImgg(Bitmap imgg) {
        this.imgg = imgg;
    }
}
