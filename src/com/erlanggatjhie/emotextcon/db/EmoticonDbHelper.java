package com.erlanggatjhie.emotextcon.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EmoticonDbHelper extends SQLiteOpenHelper {
	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";
	private static final String SQL_CREATE_ENTRIES =
	    "CREATE TABLE " + EmoticonEntry.TABLE_NAME + " (" +
	    		EmoticonEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP + 
	    		EmoticonEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
	    		EmoticonEntry.COLUMN_NAME_CONTENT + TEXT_TYPE + COMMA_SEP +
	    " )";

	private static final String SQL_DELETE_ENTRIES =
	    "DROP TABLE IF EXISTS " + EmoticonEntry.TABLE_NAME;
	
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Emoticons.db";

    public EmoticonDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
