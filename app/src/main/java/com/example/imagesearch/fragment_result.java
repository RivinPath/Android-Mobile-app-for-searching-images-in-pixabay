package com.example.imagesearch;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_result#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_result extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private IterateViewModel viewModel;

    private MutableLiveData<Integer> myInt;

    private AtomicInteger jShared = new AtomicInteger(0);

    Bitmap image;

    int spanCount = 2;

    String keyWord = "";

    ResultAdapter adapter;

    SearchResponseViewModel sViewModel;
    ImageViewModel imageViewModel;
    ErrorViewModel errorViewModel;
    GridLayoutManager gridLayoutManager;

    Activity parentActivity = getActivity();

    ArrayList<ResultItem> resultList;

    //private ImageView avatarImageView;

    public fragment_result() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_result.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_result newInstance(String param1, String param2) {
        fragment_result fragment = new fragment_result();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_result, container, false);

        TextView showKey = rootView.findViewById(R.id.keyWordView);
        Button change = rootView.findViewById(R.id.changeView);



        sViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) new ViewModelProvider.NewInstanceFactory()).get(SearchResponseViewModel.class);
        imageViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) new ViewModelProvider.NewInstanceFactory()).get(ImageViewModel.class);
        errorViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) new ViewModelProvider.NewInstanceFactory()).get(ErrorViewModel.class);


        Bundle args = getArguments();
        //Bundle bundle = new Bundle();

        //String keyWord = "";
        //String sample = "dada";

        if (args != null)
        {
            keyWord = args.getString("keyWord", "");

        }

        showKey.setText("Results for \""+ keyWord + "\"");




            //Starting the APISearchThread Thread
            APISearchThread searchThread = new APISearchThread(keyWord, requireContext(), sViewModel);
            searchThread.start();

        //int j = 0;

            sViewModel.response.observe(getViewLifecycleOwner(), new Observer<String>() {
                @Override
                public void onChanged(String s) {


                    Toast.makeText(getContext(), "Search Complete", Toast.LENGTH_LONG).show();
                    ImageRetrievalThread imageRetrievalThread = new ImageRetrievalThread(requireActivity(), sViewModel, imageViewModel, errorViewModel,jShared  ,requireActivity());
                    imageRetrievalThread.start();


                }
            });





            //viewModel = new ViewModelProvider(this).get(IterateViewModel.class);
            //updateMyInt(j);


            imageViewModel.image.observe(getViewLifecycleOwner(), new Observer<Bitmap>() {
                @Override
                public void onChanged(Bitmap bitmap) {




                        ResultItem resultItem = new ResultItem(bitmap);

                        // Add the ResultItem to the resultList
                        resultList.add(resultItem);

                        // Notify the adapter that the data has changed
                        adapter.notifyDataSetChanged();



                }
            });



            errorViewModel.errorCode.observe(getViewLifecycleOwner(), new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    //
                }
            });



        //End For loop



        resultList = new ArrayList<>();

        viewModel = new ViewModelProvider(this).get(IterateViewModel.class);



        RecyclerView rv =rootView.findViewById(R.id.result_recycler);
        spanCount = 2;
        gridLayoutManager = new GridLayoutManager(requireContext(), spanCount, GridLayoutManager.VERTICAL, false);
        //rv.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        rv.setLayoutManager(gridLayoutManager);
        adapter =new ResultAdapter(resultList, viewModel);
        rv.setAdapter(adapter);

        change.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (spanCount == 2)
                {
                    spanCount = 1;

                    change.setText("View 2");
                }
                else
                {
                    spanCount = 2;
                    change.setText("View 1");
                }

                gridLayoutManager.setSpanCount(spanCount);
                //rv.setLayoutManager(gridLayoutManager);

                adapter.notifyDataSetChanged();
            }
        });


        //avatarImageView = rootView.findViewById(R.id.temp);


        /*viewModel.getImage().observe(getViewLifecycleOwner(), new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap bitmap) {
                // Update the ImageView when the LiveData changes
                avatarImageView.setImageBitmap(bitmap);
            }
        });

        //Save Image
        Bitmap imageBitmap = ((BitmapDrawable) avatarImageView.getDrawable()).getBitmap();*/






        return rootView;
    }

    public void loadImages(Bitmap map,ArrayList<ResultItem> list, ResultAdapter adapterIn)
    {
        for (int j = 0; j < 15; j++)
        {
            ResultItem resultItem = new ResultItem(map);
            resultList.add(resultItem);
            adapterIn.notifyDataSetChanged();

        }
    }




}