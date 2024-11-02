package com.example.imagesearch;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class IterateViewModel extends ViewModel
{

    public MutableLiveData<Bitmap> image= new MutableLiveData<>();



    public LiveData<Bitmap> getImage()
    {
        return image;
    }

    public void setImage(Bitmap bitmap)
    {
        image.setValue(bitmap);
    }
}
