package com.melbasolutions.Melbapp;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Boris & Pim on 2/15/2016.
 */
public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView txt = (TextView) findViewById(R.id.LoginTitle);
        Typeface CustomFont = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-SemiBold.ttf");
        txt.setTypeface(CustomFont);
    }
}