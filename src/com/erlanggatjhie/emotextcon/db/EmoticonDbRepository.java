package com.erlanggatjhie.emotextcon.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.erlanggatjhie.emotextcon.model.Emoticon;

public class EmoticonDbRepository {
	private EmoticonDbHelper dbHelper;
	
	public EmoticonDbRepository(Context context) {
		this.dbHelper = new EmoticonDbHelper(context);
	}
	
	public List<Emoticon> getAllEmoticons() {
		SQLiteDatabase db = null;
		Cursor cursor = null;
		
		try {
			db = dbHelper.getReadableDatabase();
			cursor = getCursorWithReadAllEmoticons(db);
			return getAllEmoticonByCursor(cursor);
		} finally {
			cursor.close();
			db.close();
		}
	}

	public boolean insertEmoticon(Emoticon emoticon) {
		SQLiteDatabase db = null;
		try {
			db = dbHelper.getWritableDatabase();
			insertEmoticonToDb(db, emoticon);
			return true;
		} catch (SQLException ex) {
			return false;
		} finally {
			db.close();
		}
	}
	
	private ContentValues getContentValuesForEmoticon(Emoticon emoticon) {
		ContentValues values = new ContentValues();
		values.put(EmoticonEntry.COLUMN_NAME_CONTENT, emoticon.getContent());
		values.put(EmoticonEntry.COLUMN_NAME_DESCRIPTION, emoticon.getDescription());
		
		return values;
	}
	
	private void insertEmoticonToDb(SQLiteDatabase db, Emoticon emoticon) {
		db.insertOrThrow(EmoticonEntry.TABLE_NAME, null, getContentValuesForEmoticon(emoticon));		
	}
	
	private String getValueWithColumn(Cursor cursor, String columnName) {
		return cursor.getString(cursor.getColumnIndexOrThrow(columnName));	
	}
	
	private List<Emoticon> getAllEmoticonByCursor(Cursor cursor) {
		List<Emoticon> emoticons = new ArrayList<Emoticon>();
		
		if (cursor.moveToFirst()) {
			do {
				emoticons.add(new Emoticon(
					getValueWithColumn(cursor, EmoticonEntry.COLUMN_NAME_DESCRIPTION),
					getValueWithColumn(cursor, EmoticonEntry.COLUMN_NAME_CONTENT))
				); 
			} while(cursor.moveToNext());
		}
		
		return emoticons;
	}
	
	private Cursor getCursorWithReadAllEmoticons(SQLiteDatabase db) {
		String[] projection = {
				EmoticonEntry.COLUMN_NAME_CONTENT,
				EmoticonEntry.COLUMN_NAME_DESCRIPTION
		};

		return db.query(
				EmoticonEntry.TABLE_NAME, 
				projection, 
				null, 
				null, 
				null, 
				null, 
				EmoticonEntry.COLUMN_NAME_DESCRIPTION);
	}
	
	
}
