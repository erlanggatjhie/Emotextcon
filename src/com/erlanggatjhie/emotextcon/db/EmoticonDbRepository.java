package com.erlanggatjhie.emotextcon.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.erlanggatjhie.emotextcon.model.Emoticon;

public class EmoticonDbRepository {
	private EmoticonDbHelper dbHelper;
	
	public EmoticonDbRepository(Context context) {
		this.dbHelper = new EmoticonDbHelper(context);
	}
	
	public List<Emoticon> getAllEmoticons() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		List<Emoticon> emoticons = new ArrayList<Emoticon>();
		
		String[] projection = {
				EmoticonEntry.COLUMN_NAME_CONTENT,
				EmoticonEntry.COLUMN_NAME_DESCRIPTION
		};
		
		Cursor cursor = db.query(
				EmoticonEntry.TABLE_NAME, 
				projection, 
				null, 
				null, 
				null, 
				null, 
				EmoticonEntry.COLUMN_NAME_DESCRIPTION);
		
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
	
	private String getValueWithColumn(Cursor cursor, String columnName) {
		return cursor.getString(cursor.getColumnIndexOrThrow(columnName));	
	}
}
