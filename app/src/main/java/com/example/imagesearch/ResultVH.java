package com.example.imagesearch;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ResultVH extends RecyclerView.ViewHolder
{
    public ImageView resultImage;

    public ResultVH(@NonNull View itemView)
    {
        super(itemView);
        resultImage = itemView.findViewById(R.id.result_image);
    }
}
