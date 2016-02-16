package com.melbasolutions.Melbapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Boris & Pim on 2/15/2016.
 */
public class MainActivity extends Activity {

    private SharedPreferences prefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Create shared prefs.
        prefs = getSharedPreferences(getString(R.string.pref_pref_name), Context.MODE_PRIVATE);
        prefs.edit().clear().commit();
        boolean loggedInBefore = prefs.getBoolean(getString(R.string.pref_logged_in_before), false);

        if (loggedInBefore) {
            //show home activity.
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        } else {
            //show log in fragment/activity.
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);

        }


        TextView txt = (TextView) findViewById(R.id.LoginTitle);
        Typeface CustomFont = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-SemiBold.ttf");
        txt.setTypeface(CustomFont);
    }
}