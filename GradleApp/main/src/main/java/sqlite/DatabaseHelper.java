package sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Boris on 2/22/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Melba_Database.db";
    public static final int DATABASE_VERSION = 2;

    public static final String CREATE_SQL_TABLE =
            "CREATE TABLE " + StreepjesReaderContract.StreepEntry.TABLE_NAME_STREEPJES
                    + " (" + StreepjesReaderContract.StreepEntry._ID + " INTEGER PRIMARY KEY, " +
                    StreepjesReaderContract.StreepEntry.COLUMN_NAME_USERID + " INTEGER, " +
                    StreepjesReaderContract.StreepEntry.COLUMN_NAME_ADDEDBY + " INTEGER, " +
                    StreepjesReaderContract.StreepEntry.COLUMN_NAME_AMOUNT + " INTEGER, " +
                    StreepjesReaderContract.StreepEntry.COLUMN_NAME_DATEADDED + " TEXT DEFAULT CURRENT_TIMESTAMP)";
    public static String DELETE_SQL_TABLE = "DELETE IF EXISTS " + StreepjesReaderContract.StreepEntry.TABLE_NAME_STREEPJES;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SQL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_SQL_TABLE);
        onCreate(db);
    }

    public String parseCreateString() {
        return CREATE_SQL_TABLE;
    }
}
