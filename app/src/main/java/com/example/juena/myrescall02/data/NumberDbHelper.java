package com.example.juena.myrescall02.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.juena.myrescall02.data.NumberContract.*;

/**
 * Created by juena on 25.06.2017.
 */

public class NumberDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "randomZahlenListe.db";
    private static final int DATABASE_VERSION = 5;

    public NumberDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_KWPSLISTE_TABLE = "CREATE TABLE " + NumberEntry.TABLE_NAME + " (" +
                NumberContract.NumberEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NumberEntry.COLUMN_ps + " TEXT NOT NULL, " +
                NumberEntry.COLUMN_kw + " TEXT NOT NULL, " +
                NumberEntry.COLUMN_datum + " TEXT NOT NULL, " +
                NumberEntry.COLUMN_woche + " TEXT NOT NULL, " +
                NumberEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" + ");";

        sqLiteDatabase.execSQL(SQL_CREATE_KWPSLISTE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NumberEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}

