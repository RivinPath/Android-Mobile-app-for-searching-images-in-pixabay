package com.example.imagesearch;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ResultAdapter extends RecyclerView.Adapter<ResultVH>
{
    ArrayList<ResultItem> results;

    private IterateViewModel viewModel;

    public ResultAdapter(ArrayList<ResultItem> results, IterateViewModel viewModel)
    {
        this.results = results;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ResultVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.result_item,parent,false);
        ResultVH resultVH = new ResultVH(view);
        return resultVH;
        //return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ResultVH holder, int position)
    {
        ResultItem singleData = results.get(position);
        holder.resultImage.setImageBitmap(singleData.getBitImage());
        Bitmap image = singleData.getBitImage();



        holder.resultImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //viewModel.getImage().setValue(image);
                //viewModel.setImage(image);

                if (viewModel != null)
                {
                    viewModel.setImage(image);
                }
                Log.d("adapter image", "value is " + image);


            }
        });


    }

    @Override
    public int getItemCount()
    {
        return results.size();
    }

}
