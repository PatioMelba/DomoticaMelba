package com.melbasolutions.Melbapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

/**
 * Created by Boris on 2/15/2016.
 */
public class HomeActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_fragment);

        Button button = (Button) findViewById(R.id.add_streepje);

        button

    }
}