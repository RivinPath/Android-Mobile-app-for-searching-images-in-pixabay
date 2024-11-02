package com.example.imagesearch;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.concurrent.atomic.AtomicInteger;

public class ImageRetrievalThread extends Thread {

    private RemoteUtilities remoteUtilities;
    private SearchResponseViewModel sViewModel;
    private ImageViewModel imageViewModel;
    private ErrorViewModel errorViewModel;

    private IterateViewModel iViewModel;
    private Activity uiActivity;

    private Fragment fragment;

    private Context context;

    private MutableLiveData<Integer> myInt;

    private AtomicInteger jShared;
    private int maxImages = 15;
    private int jValue = 0;

    private ResultAdapter adapter;

    /*public ImageRetrievalThread(Fragment fragment, SearchResponseViewModel viewModel, ImageViewModel imageViewModel, ErrorViewModel errorViewModel) {
        remoteUtilities = RemoteUtilities.getInstance(fragment);
        this.sViewModel = viewModel;
        this.imageViewModel = imageViewModel;
        this.errorViewModel = errorViewModel;
        //this.uiActivity=uiActivity;
        this.fragment=fragment;
    }*/

    public ImageRetrievalThread(Context context, SearchResponseViewModel viewModel, ImageViewModel imageViewModel, ErrorViewModel errorViewModel,  AtomicInteger jShared  , Activity uiActivity) {
        this.context = context;
        this.sViewModel = viewModel;
        this.imageViewModel = imageViewModel;
        this.errorViewModel = errorViewModel;
        this.remoteUtilities = RemoteUtilities.getInstance(context);
        //this.myInt = myInt;
        this.jShared = jShared;

        this.uiActivity=uiActivity;
    }




    public void run(){
        //iViewModel = new ViewModelProvider(this).get(IterateViewModel.class);



        while (jValue < maxImages)
        {
            //int jValue = jShared.incrementAndGet();

            //jValue++;

            Log.d("MyTag", "jValue = " + jValue);


            String endpoint = getEndpoint(sViewModel.getResponse(),jValue);
            if (endpoint == null) {
                uiActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(uiActivity, "No images found", Toast.LENGTH_LONG).show();
                        errorViewModel.setErrorCode(errorViewModel.getErrorCode() + 1);
                    }
                });
            } else {
                Bitmap image = getImageFromUrl(endpoint);

                try {
                    //Thread.sleep(3000);
                } catch (Exception e) {
                }
                imageViewModel.setImage(image);
            }

            jValue++;
        }
    }

    private String getEndpoint(String data,int num){
        String imageUrl = null;
        try {
            JSONObject jBase = new JSONObject(data);
            JSONArray jHits = jBase.getJSONArray("hits");
            if(jHits.length()>0){
                JSONObject jHitsItem = jHits.getJSONObject(num);
                imageUrl = jHitsItem.getString("largeImageURL");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return imageUrl;
    }

    private Bitmap getImageFromUrl(String imageUrl){
        Bitmap image = null;
        Uri.Builder url = Uri.parse(imageUrl).buildUpon();
        String urlString = url.build().toString();
        HttpURLConnection connection = remoteUtilities.openConnection(urlString);
        if(connection!=null){
            if(remoteUtilities.isConnectionOkay(connection)==true){
                image = getBitmapFromConnection(connection);
                connection.disconnect();
            }
        }
        return image;
    }

    public Bitmap getBitmapFromConnection(HttpURLConnection conn){
        Bitmap data = null;
        try {
            InputStream inputStream = conn.getInputStream();
            byte[] byteData = getByteArrayFromInputStream(inputStream);
            data = BitmapFactory.decodeByteArray(byteData,0,byteData.length);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return data;
    }

    private byte[] getByteArrayFromInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[4096];
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        return buffer.toByteArray();
    }

}
