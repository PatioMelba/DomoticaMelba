package httpHandlers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.melbasolutions.melbapp.main.Enums;
import com.melbasolutions.melbapp.main.HomeActivity;
import com.melbasolutions.melbapp.main.LoginActivity;
import com.melbasolutions.melbapp.main.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Boris on 2/22/2016.
 */
public class BasicHttpHandler extends AsyncTask<URL, Void, String> {

    private HttpURLConnection httpURLConnection;
    private URL url;

    protected Context context;
    protected Activity parent;
    protected String message;

    protected BufferedReader in;
    protected OutputStreamWriter out;

    //Progress dialog for showing that the task is doing something on the background.
    protected ProgressDialog pd;
    int responseCode;


    public BasicHttpHandler(Activity parent) {
        this.parent = parent;
        this.context = parent.getApplicationContext();
        pd = new ProgressDialog(parent);
    }

    public BasicHttpHandler(Activity parent, String message) {
        this.parent = parent;
        this.context = parent.getApplicationContext();
        pd = new ProgressDialog(parent);
        pd.setMessage(message);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd.show();
    }

    @Override
    protected String doInBackground(URL... params) {
        try {
            url = params[0];
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(30000);
            httpURLConnection.setConnectTimeout(30000);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setChunkedStreamingMode(0);
            httpURLConnection.setRequestMethod("GET");

            OutputStreamWriter out = new OutputStreamWriter(httpURLConnection.getOutputStream());
            out.write("");
            out.flush();
            out.close();
            responseCode = httpURLConnection.getResponseCode();

            //setup input reader.
            in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

        } catch (ProtocolException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //TODO: do something with this if desired.
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        //close the progress dialog.
        pd.dismiss();
    }
}