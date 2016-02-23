package com.melbasolutions.melbapp.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import httpHandlers.LoginHandler;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Boris on 2/16/2016.
 */
public class LoginActivity extends Activity {
    private Button loginButton;
    private EditText username, password;
    private SharedPreferences prefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username_input);
        password = (EditText) findViewById(R.id.password_input);

        loginButton = (Button) findViewById(R.id.button_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    md.update(password.getText().toString().getBytes());
                    StringBuffer pass = new StringBuffer();
                    byte[] messageDigest = md.digest();
                    for (int i=0; i<messageDigest.length; i++) {
                        pass.append(Integer.toHexString(0xFF & messageDigest[i]));
                    }

                    URL url = new URL(getString(R.string.webservice_location) + "?do=login&user=" + username.getText() + "&pass=" + pass.toString());
                    Toast.makeText(getBaseContext(),url.toString(),Toast.LENGTH_LONG).show();
                    LoginHandler handler = new LoginHandler(LoginActivity.this);
                    handler.execute(url);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    //call back for asynctask to log user in.
    public void logIn(int userId) {
        prefs = getSharedPreferences(getString(R.string.pref_pref_name),Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        if (!prefs.contains(getString(R.string.pref_logged_in_before))) {
            edit.putBoolean(getString(R.string.pref_logged_in_before), true);
            edit.putInt(getString(R.string.pref_user_id), userId);
            edit.apply();
        }
        Intent homeIntent = new Intent(this, HomeActivity.class);
        startActivity(homeIntent);
    }
}