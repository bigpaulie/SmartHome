package com.paul_resume.smarthome.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by paul on 18.05.2015.
 */
public class DbButtonsHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Buttons.db",
                               DATABASE_TABLE = "buttons";
    public static final Integer DATABASE_VERSION = 1;



    public DbButtonsHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
