package com.melbasolutions.melbapp.main;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.*;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import httpHandlers.HttpRequestHandler;
import httpHandlers.StatsHandler;
import org.json.JSONException;
import org.json.JSONObject;
import sqlite.DatabaseHelper;
import sqlite.StreepjesReaderContract;


import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by Boris on 2/15/2016.
 */
public class HomeActivity extends AppCompatActivity {

    private FragmentTabHost fragmentTabHost;

    private URL url;
    private SharedPreferences prefs;
    private int userId;

    private DatabaseHelper databaseHelper;
    private String[] personalStats;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set the layout.
        setContentView(R.layout.activity_home);

        //Make view adapter for swiping left and right between tabs.
        CollectionPagerAdapter adapter = new CollectionPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager_home);
        viewPager.setAdapter(adapter);

        //Setup the tablayout.
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout_home);
        tabLayout.setupWithViewPager(viewPager);

        //Setup Toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        toolbar.setTitle(getString(R.string.title_home));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Setup preferences for user id.
        prefs = getSharedPreferences(getString(R.string.pref_pref_name), Context.MODE_PRIVATE);
        userId = prefs.getInt(getString(R.string.pref_user_id),-1);

        //Get the local SQLite Database.
        databaseHelper = new DatabaseHelper(getBaseContext());
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Log.i("boris:",databaseHelper.parseCreateString());

        ContentValues values = new ContentValues();
        values.put(StreepjesReaderContract.StreepEntry.COLUMN_NAME_USERID, userId);
        values.put(StreepjesReaderContract.StreepEntry.COLUMN_NAME_ADDEDBY, userId);
        values.put(StreepjesReaderContract.StreepEntry.COLUMN_NAME_AMOUNT, 1);

        db.insert(StreepjesReaderContract.StreepEntry.TABLE_NAME_STREEPJES, StreepjesReaderContract.StreepEntry.COLUMN_NAME_USERID, values);

        //Do all the stuff for getting the stats from the database.
        try {
            URL url = new URL(getString(R.string.webservice_location) + "?do=getstats&user=" + userId);
            new StatsHandler(this).execute(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void addStreepje() throws IOException {
        URL url = new URL(getString(R.string.webservice_location) + "?do=addStreepje&user=" + userId + "&amount=1");
        new HttpRequestHandler(this, Enums.CommandTypes.STREEP).execute(url);
    }

    public void updateStats(){
        double pricePerBeer = Double.parseDouble("" + ((TextView) findViewById(R.id.stats_global_price_per_beer)).getText());
        int amount = Integer.parseInt("" + ((TextView) findViewById(R.id.stats_personal_beer_week)).getText());
        int total = Integer.parseInt("" + ((TextView) findViewById(R.id.stats_personal_beer_total)).getText());
        double userBeerPrice = amount * pricePerBeer;
        total ++;
        amount = amount + 1;

        ((TextView) findViewById(R.id.stats_personal_beer_week)).setText("" + amount);
        ((TextView) findViewById(R.id.stats_personal_beer_cost_total)).setText(String.format("%.2f",userBeerPrice));
        ((TextView) findViewById(R.id.stats_personal_beer_total)).setText("" + total);
    }
    //makes sure data gets set from the ASyncTask.
    public void setPersonalStats(String[] params) {
        personalStats = params;
    }

    public void updateTextViews(JSONObject json) {
        Log.i("hoi",json.toString());
        try {
            double pricePerBeer = json.getDouble("beerprice");
            int beerAmount = json.getInt("userbeeramount");
            double userBeerPrice = beerAmount * pricePerBeer;

            ((TextView) findViewById(R.id.stats_personal_beer_week)).setText(json.getString("userbeeramount"));
            ((TextView) findViewById(R.id.stats_personal_beer_total)).setText(json.getString("totalbeercount"));
            ((TextView) findViewById(R.id.stats_global_price_per_beer)).setText(String.format("%.2f",pricePerBeer));
            ((TextView) findViewById(R.id.stats_personal_beer_cost_total)).setText(String.format("%.2f",userBeerPrice));

            ((TextView) findViewById(R.id.stats_everyone_beer_price)).setText(String.format("%.2f",pricePerBeer));
            ((TextView) findViewById(R.id.stats_everyone_total_beer)).setText(json.getString("totalbeercount"));


            ((TextView) findViewById(R.id.button_personal_stripe)).setText("Streep 1 bier voor " + json.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

