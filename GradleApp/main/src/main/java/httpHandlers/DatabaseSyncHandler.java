package httpHandlers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.melbasolutions.melbapp.main.R;
import sqlite.DatabaseHelper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Boris on 2/23/2016.
 */
public class DatabaseSyncHandler extends BasicHttpHandler {

    DatabaseHelper db;
    Date lastUpdatedOn;
    SharedPreferences prefs;

    public DatabaseSyncHandler(Activity parent, DatabaseHelper db) {
        super(parent);
        this.db = db;
        this.prefs = parent.getSharedPreferences(parent.getString(R.string.pref_pref_name), Context.MODE_PRIVATE);
    }

    @Override
    protected String doInBackground(URL... params) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss");

        //Create null string
        String dateString = "0000-00-00.00:00:00";
        if (prefs.contains(parent.getString(R.string.pref_last_updated))) {
            dateString =  prefs.getString(parent.getString(R.string.pref_last_updated),dateString);
        }

        StringBuilder sb;
        try {
            Log.i("boris:", dateString);

            URL url = new URL(parent.getString(R.string.webservice_location) + "?do=syncStreepjes&date=" + dateString + "");

            Log.i("Boris:", url.toString());
            super.doInBackground(url);

            String line;
            sb = new StringBuilder();
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }

            Log.i("boris:", sb.toString());

            dateString = df.format(new Date());
            //updates last updates
            Log.i("Boris:", dateString);
            prefs.edit().putString(parent.getString(R.string.pref_last_updated), dateString).apply();

            return sb.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
