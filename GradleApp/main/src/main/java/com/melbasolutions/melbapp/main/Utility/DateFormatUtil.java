package com.melbasolutions.melbapp.main.Utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Boris on 2/25/2016.
 */
public final class DateFormatUtil {
    public static SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss", Locale.ENGLISH);
    }
}
