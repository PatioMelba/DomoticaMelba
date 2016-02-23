package sqlite;

import android.provider.BaseColumns;

/**
 * Created by Boris on 2/22/2016.
 */
public final class StreepjesReaderContract {
    public StreepjesReaderContract() {}

    public static abstract class StreepEntry implements BaseColumns {
        public static final String TABLE_NAME_STREEPJES = "streepjes";
        public static final String COLUMN_NAME_USERID = "user_id";
        public static final String COLUMN_NAME_ADDEDBY = "added_by";
        public static final String COLUMN_NAME_AMOUNT = "amount";
        public static final String COLUMN_NAME_DATEADDED = "date_added";
    }

}
