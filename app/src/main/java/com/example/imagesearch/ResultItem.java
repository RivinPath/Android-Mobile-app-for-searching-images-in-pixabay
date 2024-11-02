package com.example.imagesearch;

import android.graphics.Bitmap;

public class ResultItem
{
    //private int image;
    private Bitmap image;

    public ResultItem(Bitmap image)
    {
        this.image = image;
    }

    public Bitmap getBitImage()
    {
        return image;
    }

    /*public void setImage(Bitmap image)
    {
        this.image = image;
    }*/
/*public ResultItem(int image)
    {
        this.image = image;
    }

    public int getImage()
    {
        return image;
    }

    public void setImage(int image)
    {
        this.image = image;
    }*/
}
