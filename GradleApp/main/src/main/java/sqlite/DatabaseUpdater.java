package sqlite;

import android.database.sqlite.SQLiteDatabase;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Boris on 2/23/2016.
 */
public class DatabaseUpdater {
    private SQLiteDatabase db;
    private JSONObject data;

    public DatabaseUpdater(SQLiteDatabase db, JSONObject data) {
        this.data = data;
        this.db = db;
    }

    public void update() {
        //for (JSONArray entry : data.)
    }


}
