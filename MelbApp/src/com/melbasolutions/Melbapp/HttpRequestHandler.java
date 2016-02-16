package com.melbasolutions.Melbapp;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Boris on 2/16/2016.
 */
public class HttpRequestHandler extends AsyncTask<URL, Void, String>{

    private HttpURLConnection httpURLConnection;
    private Context context;
    private int responseCode;
    public HttpRequestHandler(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(URL... params) {
        try {
            URL url = params[0];
            httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setChunkedStreamingMode(0);
            httpURLConnection.setRequestMethod("GET");
            responseCode = httpURLConnection.getResponseCode();


            OutputStreamWriter out = new OutputStreamWriter(httpURLConnection.getOutputStream());
            out.write("");
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        Toast.makeText(context, "1 Biertje Added", Toast.LENGTH_SHORT).show();
    }
}
