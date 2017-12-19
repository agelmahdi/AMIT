package com.agelmahdi.amit.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ahmed El-Mahdi on 12/19/2017.
 */

public class SqliteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "park.db";
    private static final int DATABASE_VERSION = 2;

    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_PARK_TABLE =

                "CREATE TABLE " + ParkContract.ParkEntry.TABLE_NAME + " (" +

                        ParkContract.ParkEntry._ID               + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                        ParkContract.ParkEntry.COLUMN_USER_ID      + " INTEGER NOT NULL, "                 +

                        ParkContract.ParkEntry.COLUMN_USER_NAME + " TEXT,"                  +

                        ParkContract.ParkEntry.COLUMN_ADDRESS   + " TEXT, "                    +

                        ParkContract.ParkEntry.COLUMN_LNG   + " NUMERIC, "                    +

                        ParkContract.ParkEntry.COLUMN_LAT   + " NUMERIC);";


        sqLiteDatabase.execSQL(SQL_CREATE_PARK_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ParkContract.ParkEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
}
