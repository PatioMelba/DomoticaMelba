package com.melbasolutions.Melbapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by Boris on 2/15/2016.
 */
public class HomeActivity extends FragmentActivity {

    private FragmentTabHost fragmentTabHost;

    private URL url;
    private SharedPreferences prefs;
    private int userId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        TabLayout tabLayout = new TabLayout(getBaseContext());
        tabLayout.addTab(tabLayout.newTab().setText("Personal"));
        tabLayout.addTab(tabLayout.newTab().setText("Everyone"));

        //Setup preferences for user id.
        prefs = getSharedPreferences(getString(R.string.pref_pref_name), Context.MODE_PRIVATE);
        userId = prefs.getInt(getString(R.string.pref_user_id),-1);

        //set URL of webservice
        try {
            url = new URL("http://localhost/MelbaApp/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }



        Button button = (Button) findViewById(R.id.add_streepje);
        button.setText("Add 1 bier voor user" + userId);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addStreepje(userId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void addStreepje(int userID) throws IOException {
        url = new URL("http://10.0.2.2:80/MelbaApp/MelbaService.php?do=addStreepje&user=" + userID + "&amount=1");

        new HttpRequestHandler(this, Enums.CommandTypes.STREEP).execute(url);


    }
}