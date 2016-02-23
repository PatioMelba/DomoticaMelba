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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Boris on 2/16/2016.
 */
public class HttpRequestHandler extends AsyncTask<URL, Void, String> {

    private HttpURLConnection httpURLConnection;
    private Context context;
    private Activity parent;
    private String message;
    private Enums.CommandTypes command;
    private URL url;
    private JSONObject jsonObject;

    //Progress dialog for showing that the task is doing something on the background.
    private ProgressDialog pd;
    int responseCode;

    private StringBuilder sb;
    public HttpRequestHandler(Activity parent, Enums.CommandTypes command) {
        this.parent = parent;
        this.context = parent.getApplicationContext();
        this.command = command;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd = new ProgressDialog(parent);
        pd.setMessage("This should be done within a few seconds.");
        pd.setTitle("Loading data from the server.");
        pd.show();
    }

    @Override
    protected String doInBackground(URL... params) {
        try {
            url = params[0];
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setChunkedStreamingMode(0);
            httpURLConnection.setRequestMethod("GET");
            OutputStreamWriter out = new OutputStreamWriter(httpURLConnection.getOutputStream());
            out.write("");
            out.flush();
            out.close();

            responseCode = httpURLConnection.getResponseCode();
            sb = new StringBuilder();

            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String line;
            while((line = in.readLine()) != null) {
                sb.append(line);
            }


            String t = sb.toString();
            jsonObject = new JSONObject(t);

            Log.i("saf",sb.toString());
            switch (this.command) {
                case LOGIN:
                    message = in.readLine();
                    break;
                case STREEP:
                    break;
                case GETSTATS:

                    Log.i("hier",sb.toString());
                    break;
            }

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        //Toast.makeText(context, "1 Biertje Added", Toast.LENGTH_SHORT).show();
        //Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        switch (command) {
            case LOGIN:
                if (message != null && message != "") {
                    //Toast.makeText(context, url.toString(), Toast.LENGTH_LONG);
                    String[] params = message.split(":");
                    if (params[0].equals("succes")) {
                        if (parent instanceof LoginActivity) {
                            ((LoginActivity) parent).logIn(Integer.parseInt(params[1]));
                        }
                    } else {
                        Toast.makeText(context, "Invalid Login", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case STREEP:
                if (parent instanceof HomeActivity) {
                    ((HomeActivity) parent).updateStats();
                }
                break;
            case GETSTATS:
                //TODO: alleen doen als response goed was
                if (responseCode != 200 && parent instanceof HomeActivity) {
                    ((HomeActivity) parent).updateTextViews(jsonObject);
                }
                break;
        }

        //close the progress dialog.
        pd.dismiss();
    }
}
