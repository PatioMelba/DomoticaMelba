package com.melbasolutions.Melbapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by Boris on 2/15/2016.
 */
public class HomeActivity extends Activity {

    private URL url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set URL of webservice
        try {
            url = new URL("http://localhost/MelbaApp/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.home_fragment);

        Button button = (Button) findViewById(R.id.add_streepje);

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addStreepje(1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void addStreepje(int userID) throws IOException {
        url = new URL("http://10.0.2.2:80/MelbaApp/MelbaService.php?do=addStreepje&user=" + userID + "&amount=1");

        new HttpRequestHandler(this).execute(url);


    }
}