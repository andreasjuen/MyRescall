package com.example.juena.myrescall02.data;

import android.provider.BaseColumns;

/**
 * Created by juena on 25.06.2017.
 */

public class NumberContract {
    public static final String AUTHORITY = "com.example.juena.myrescall02";

    private NumberContract() {}

    public static final class NumberEntry implements BaseColumns {


        public static final String TABLE_NAME = "KWPSListe";
        public static final String COLUMN_ps = "ps";
        public static final String COLUMN_kw = "kw";
        public static final String COLUMN_datum = "datum";
        public static final String COLUMN_woche = "woche";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
