package httpHandlers;

import android.app.Activity;
import com.melbasolutions.melbapp.main.HomeActivity;
import com.melbasolutions.melbapp.main.R;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Boris on 2/22/2016.
 */
public class StatsHandler extends BasicHttpHandler {

    public StatsHandler(Activity parent) {
        super(parent);
        pd.setMessage(context.getString(R.string.progress_dialog_message_getstats));
        pd.setTitle(context.getString(R.string.progress_dialog_title_getstats));
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(URL... params) {
        super.doInBackground(params);
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while((line = in.readLine()) != null) {
                sb.append(line);
            }

            String t = sb.toString();

            //returns the string a Json object can be created from.
            return t;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        if (parent instanceof HomeActivity) {
            try {
                ((HomeActivity) parent).updateTextViews(new JSONObject(s));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        super.onPostExecute(s);
    }
}
