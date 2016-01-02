package com.gsandchase.mileagelogger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Rusty on 12/22/2015.
 */
public final class MileageLog {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public MileageLog() {}

    /* Inner class that defines the table contents */
    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_MILEAGE = "mileage";
        public static final String COLUMN_WEEKLY_MILEAGE = "weekly";
        public static final String COLUMN_MONTHLY_MILEAGE = "monthly";
        public static final String COLUMN_ANNUAL_MILEAGE = "annual";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_MILEAGE + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_WEEKLY_MILEAGE + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_MONTHLY_MILEAGE + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_ANNUAL_MILEAGE + TEXT_TYPE + COMMA_SEP +
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;


    public class MileageLogDbHelper extends SQLiteOpenHelper {

        // If you change the database schema, you must increment the database version.
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "MileageLog.db";

        public MileageLogDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }


}
