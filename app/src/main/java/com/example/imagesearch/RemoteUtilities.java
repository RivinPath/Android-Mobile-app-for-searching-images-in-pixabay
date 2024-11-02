package com.example.imagesearch;

import android.app.Activity;
import android.widget.Toast;

//import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import android.app.Activity;
import android.content.Context;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RemoteUtilities {

    public static RemoteUtilities remoteUtilities = null;
    private Context context;

    private Context uiContext;


    public RemoteUtilities(Context uiContext) {
        this.uiContext = uiContext;
    }

    public void setUiContext(Context uiContext) {
        this.uiContext = uiContext;
    }

    public static RemoteUtilities getInstance(Context uiContext) {
        if (remoteUtilities == null) {
            remoteUtilities = new RemoteUtilities(uiContext);
        }
        remoteUtilities.setUiContext(uiContext);
        return remoteUtilities;
    }

    public HttpURLConnection openConnection(String urlString) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(urlString);
            conn = (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (conn == null) {
            //showToast("Check Internet");
        }
        return conn;
    }

    public boolean isConnectionOkay(HttpURLConnection conn) {
        try {
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            showToast("Problem with API Endpoint");

        }
        return false;
    }

    public String getResponseString(HttpURLConnection conn) {
        String data = null;
        try {
            InputStream inputStream = conn.getInputStream();
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            data = result.toString(StandardCharsets.UTF_8.name());
        } catch (IOException e) {
            e.printStackTrace();
            showToast("Error reading response");
        }
        return data;
    }

    private void showToast(String message) {
        if (context instanceof Activity) {
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

}
