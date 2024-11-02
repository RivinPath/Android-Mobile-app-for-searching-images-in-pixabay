package com.example.imagesearch;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_search#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_search extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Handler uiHandler = new Handler(Looper.getMainLooper());



    public fragment_search() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_search.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_search newInstance(String param1, String param2) {
        fragment_search fragment = new fragment_search();
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

        View rootView = inflater.inflate(R.layout.fragment_search, container, false);



        EditText searchBar = rootView.findViewById(R.id.searchBar);
        Button searchButton = rootView.findViewById(R.id.searchButton);

        ProgressBar progressBar = rootView.findViewById(R.id.progressBar);
        progressBar.setMax(100);
        int splashDuration = 2000;

        progressBar.setVisibility(rootView.INVISIBLE);







        fragment_result  resultFragment = new fragment_result();

        searchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {


                String keyWord =searchBar.getText().toString();

                Bundle bundle = new Bundle();

                bundle.putString("keyWord", keyWord);
                resultFragment.setArguments(bundle);

                //Toast.makeText(getContext(), keyWord, Toast.LENGTH_SHORT).show();

                if (keyWord.isEmpty())
                {
                    Toast.makeText(getContext(), "Please Enter Key Word", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    progressBar.setVisibility(rootView.VISIBLE);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {


                            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.Frame, resultFragment);
                            transaction.addToBackStack(null);


                            transaction.commit();


                        }
                    }, 3000);
                }


            }
        });

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                int duration = 3000; // 3 seconds
                int interval = duration / 100; // Calculate the interval for updates

                for (int progress = 0; progress <= 100; progress++) {
                    final int finalProgress = progress;
                    uiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(finalProgress);
                        }
                    });

                    try {
                        Thread.sleep(interval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();*/

        return rootView;
    }
}