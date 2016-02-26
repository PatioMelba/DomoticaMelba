package httpHandlers;

import android.app.Activity;
import android.widget.Toast;
import com.melbasolutions.melbapp.main.LoginActivity;
import com.melbasolutions.melbapp.main.R;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Boris on 2/22/2016.
 */
public class LoginHandler extends BasicHttpHandler {

    public LoginHandler(Activity parent) {
        super(parent);
        pd.setMessage(context.getString(R.string.progress_dialog_message_login));
        pd.setTitle(context.getString(R.string.progress_dialog_title_login));
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(URL... params) {
        super.doInBackground(params);
        try {
            if (in != null) {
                message = in.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //This delays the code a bit to give the user some feedback through means of the progress dialog.
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        if (message != null && message != "") {
            String[] params = message.split(":");
            if (params[0].equals("succes")) {
                if (parent instanceof LoginActivity) {
                    ((LoginActivity) parent).logIn(Integer.parseInt(params[1]));
                }
            } else {
                Toast.makeText(context, "Invalid Login", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(context, "No server response.", Toast.LENGTH_LONG).show();
        }
        //closes Progress dialog.
        super.onPostExecute(s);
    }
}
