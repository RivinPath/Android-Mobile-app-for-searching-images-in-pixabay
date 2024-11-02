package com.example.imagesearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_SEARCH_FRAGMENT = "searchFragment";
    private static final String TAG_RESULT_FRAGMENT = "resultFragment";
    private FragmentManager fragmentManager;

    private fragment_search searchFragment;
    private fragment_result resultFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();


        if (savedInstanceState == null) {

            searchFragment = new fragment_search();


            fragmentManager.beginTransaction()
                    .add(R.id.Frame, searchFragment, TAG_SEARCH_FRAGMENT)
                    .commit();
        } else {

            searchFragment = (fragment_search) fragmentManager.findFragmentByTag(TAG_SEARCH_FRAGMENT);
            resultFragment = (fragment_result) fragmentManager.findFragmentByTag(TAG_RESULT_FRAGMENT);


        }
    }

}