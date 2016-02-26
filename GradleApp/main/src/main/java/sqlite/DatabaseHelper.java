package sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.melbasolutions.melbapp.main.Utility.DateFormatUtil;

import java.util.Date;

/**
 * Created by Boris on 2/22/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Melba_Database.db";
    public static final int DATABASE_VERSION = 3;

    //Create Table Query.
    public static final String CREATE_SQL_TABLE =
            "CREATE TABLE " + StreepjesReaderContract.StreepEntry.TABLE_NAME_STREEPJES
                    + " (" + StreepjesReaderContract.StreepEntry._ID + " INTEGER PRIMARY KEY, " +
                    StreepjesReaderContract.StreepEntry.COLUMN_NAME_USERID + " INTEGER, " +
                    StreepjesReaderContract.StreepEntry.COLUMN_NAME_ADDEDBY + " INTEGER, " +
                    StreepjesReaderContract.StreepEntry.COLUMN_NAME_AMOUNT + " INTEGER, " +
                    StreepjesReaderContract.StreepEntry.COLUMN_NAME_DATEADDED + " TEXT DEFAULT CURRENT_TIMESTAMP)";
    //Delete Table Query.
    public static String DELETE_SQL_TABLE = "DROP TABLE IF EXISTS " + StreepjesReaderContract.StreepEntry.TABLE_NAME_STREEPJES;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SQL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("Boris:", DELETE_SQL_TABLE);
        db.execSQL(DELETE_SQL_TABLE);
        onCreate(db);
    }

    public void put(ContentValues content) {
        getWritableDatabase().insert(StreepjesReaderContract.StreepEntry.TABLE_NAME_STREEPJES,StreepjesReaderContract.StreepEntry.COLUMN_NAME_USERID, content);
    }

    public void streepFor(int userId) {
        ContentValues content = new ContentValues();
        content.put(StreepjesReaderContract.StreepEntry.COLUMN_NAME_ADDEDBY, userId);
        content.put(StreepjesReaderContract.StreepEntry.COLUMN_NAME_USERID, userId);
        content.put(StreepjesReaderContract.StreepEntry.COLUMN_NAME_AMOUNT, 1);
        String date = DateFormatUtil.getDateFormat().format(new Date());
        content.put(StreepjesReaderContract.StreepEntry.COLUMN_NAME_DATEADDED, date);
        getWritableDatabase().insert(StreepjesReaderContract.StreepEntry.TABLE_NAME_STREEPJES, StreepjesReaderContract.StreepEntry.COLUMN_NAME_USERID, content);
    }

    public String parseCreateString() {
        return CREATE_SQL_TABLE;
    }
}
