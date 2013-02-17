package com.erlanggatjhie.emotextcon.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EmoticonDbHelper extends SQLiteOpenHelper {
	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";
	private static final String SQL_CREATE_ENTRIES =
	    "CREATE TABLE IF NOT EXISTS " + EmoticonEntry.TABLE_NAME + " (" +
	    		EmoticonEntry.COLUMN_NAME_EMOTICON_ID + " INT PRIMARY KEY " + COMMA_SEP + 
	    		EmoticonEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
	    		EmoticonEntry.COLUMN_NAME_CONTENT + TEXT_TYPE +
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
        insertEmoticonToDb(db, "Desc 1", "Content 1");
        insertEmoticonToDb(db, "Desc 2", "Content 2");
        insertEmoticonToDb(db, "Desc 3", "Content 3");
        insertEmoticonToDb(db, "Desc 4", "Content 4");
        insertEmoticonToDb(db, "Desc 5", "Content 5");
        insertEmoticonToDb(db, "Desc 6", "Content 6");
        insertEmoticonToDb(db, "Desc 7", "Content 7");
        insertEmoticonToDb(db, "Desc 8", "Content 8");
        insertEmoticonToDb(db, "Desc 9", "Content 9");
        insertEmoticonToDb(db, "Desc 10", "Content 10");
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
    
    
	private ContentValues getContentValuesForEmoticon(String description, String content) {
		ContentValues values = new ContentValues();
		values.put(EmoticonEntry.COLUMN_NAME_CONTENT, content);
		values.put(EmoticonEntry.COLUMN_NAME_DESCRIPTION, description);
		
		return values;
	}
	
	private void insertEmoticonToDb(SQLiteDatabase db, String description, String content) {
		db.insertOrThrow(EmoticonEntry.TABLE_NAME, null, getContentValuesForEmoticon(description, content));		
	}
}
