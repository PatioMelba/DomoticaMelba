package com.melbasolutions.Melbapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.XmlResourceParser;
import android.os.AsyncTask;
import android.widget.Toast;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Boris on 2/16/2016.
 */
public class HttpRequestHandler extends AsyncTask<URL, Void, String>{

    private HttpURLConnection httpURLConnection;
    private Context context;
    private Activity parent;
    private int responseCode;
    private String message;
    private Enums.CommandTypes command;
    public HttpRequestHandler(Activity parent, Enums.CommandTypes command) {
        this.parent = parent;
        this.context = parent.getApplicationContext();
        this.command = command;
    }

    @Override
    protected String doInBackground(URL... params) {
        try {
            URL url = params[0];
            httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setChunkedStreamingMode(0);
            httpURLConnection.setRequestMethod("GET");
            OutputStreamWriter out = new OutputStreamWriter(httpURLConnection.getOutputStream());
            out.write("");
            out.flush();
            out.close();

            responseCode = httpURLConnection.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            switch(command) {
                case LOGIN:
                    message = in.readLine();
                    break;
                case STREEP:
                    break;
            }

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        //Toast.makeText(context, "1 Biertje Added", Toast.LENGTH_SHORT).show();
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        switch (command) {
            case LOGIN:
                String[] params = message.split(":");
                if (params[0].equals("succes")) {
                    if (parent instanceof LoginActivity) {
                        ((LoginActivity) parent).logIn(Integer.parseInt(params[1]));
                    }
                } else {
                    Toast.makeText(context, "Invalid Login", Toast.LENGTH_LONG).show();
                }
                break;
            case STREEP:
                break;
        }
    }
}
