package com.example.chugh.zchat;

import android.graphics.Bitmap;

/**
 * Created by Chugh on 7/6/2017.
 */

public class FriendData {
    String contact;
    String id;
    String name;
    String image;
    Bitmap bimg;
    int img;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Bitmap getBimg() {
        return bimg;
    }

    public void setBimg(Bitmap bimg) {
        this.bimg = bimg;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
