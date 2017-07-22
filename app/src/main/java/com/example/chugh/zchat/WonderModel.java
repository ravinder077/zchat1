package com.example.chugh.zchat;

import android.graphics.Bitmap;

/**
 * Created by anonymous on 1/15/16.
 */
public class WonderModel {

    String cardName;
    String imageResourceId;

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    Bitmap  img;
    int isfav;
    int isturned;

    public int getIsturned() {
        return isturned;
    }

    public void setIsturned(int isturned) {
        this.isturned = isturned;
    }

    public int getIsfav() {
        return isfav;
    }

    public void setIsfav(int isfav) {
        this.isfav = isfav;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }


    public void setImageResourceId(String imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public String getImageResourceId() {
        return imageResourceId;
    }
}




