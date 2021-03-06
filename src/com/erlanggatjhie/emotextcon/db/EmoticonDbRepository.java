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
	
	public boolean updateEmoticon(Emoticon emoticon) {
		
		SQLiteDatabase db = null;
		try {
			db = dbHelper.getWritableDatabase();
			
			String selection = String.format("%s = ?", EmoticonEntry.COLUMN_NAME_EMOTICON_ID);
			String[] selectionArgs = { emoticon.getId().toString() };
			
			return db.update(EmoticonEntry.TABLE_NAME, 
					getContentValuesForEmoticon(emoticon), 
					selection, 
					selectionArgs) == 1;
		} finally {
			db.close();
		}
	}
	
	public boolean deleteEmoticon(int id) {
		SQLiteDatabase db = null;
		try {
			db = dbHelper.getWritableDatabase();
			
			String selection = String.format("%s = ?", EmoticonEntry.COLUMN_NAME_EMOTICON_ID);
			String[] selectionArgs = { String.valueOf(id) };
			
			return db.delete(EmoticonEntry.TABLE_NAME, 
					selection, 
					selectionArgs) == 1;
		} finally {
			db.close();
		}
	}
	
	public void deleteAllEmoticons() {
		SQLiteDatabase db = null;
		try {
			db = dbHelper.getWritableDatabase();
			
			db.delete(EmoticonEntry.TABLE_NAME, 
					null, 
					null);
		} finally {
			db.close();
		}		
	}
	
	public Emoticon getEmoticonById(int id) throws Exception {
		SQLiteDatabase db = null;
		Cursor cursor = null;
		
		try {
			db = dbHelper.getReadableDatabase();
			cursor = getCursorWithSpecificEmoticon(db, id);
			if (cursor.moveToFirst()) {
				return new Emoticon(
						getIntegerValueWithColumn(cursor, EmoticonEntry.COLUMN_NAME_EMOTICON_ID),
						getStringValueWithColumn(cursor, EmoticonEntry.COLUMN_NAME_DESCRIPTION),
						getStringValueWithColumn(cursor, EmoticonEntry.COLUMN_NAME_CONTENT));
			} else {
				throw new Exception("Unable to find emoticon with id : " + id);
			}
		} finally {
			cursor.close();
			db.close();
		}
	}
	
	private ContentValues getContentValuesForEmoticon(Emoticon emoticon) {
		ContentValues values = new ContentValues();
		values.put(EmoticonEntry.COLUMN_NAME_EMOTICON_ID, emoticon.getId());
		values.put(EmoticonEntry.COLUMN_NAME_CONTENT, emoticon.getContent());
		values.put(EmoticonEntry.COLUMN_NAME_DESCRIPTION, emoticon.getDescription());
		
		return values;
	}
	
	private void insertEmoticonToDb(SQLiteDatabase db, Emoticon emoticon) {
		db.insertOrThrow(EmoticonEntry.TABLE_NAME, null, getContentValuesForEmoticon(emoticon));		
	}
	
	private String getStringValueWithColumn(Cursor cursor, String columnName) {
		return cursor.getString(cursor.getColumnIndexOrThrow(columnName));	
	}
	
	private int getIntegerValueWithColumn(Cursor cursor, String columnName) {
		return cursor.getInt(cursor.getColumnIndexOrThrow(columnName));
	}
	
	private List<Emoticon> getAllEmoticonByCursor(Cursor cursor) {
		List<Emoticon> emoticons = new ArrayList<Emoticon>();

		if (cursor.moveToFirst()) {
			do {
				emoticons.add(new Emoticon(
					getIntegerValueWithColumn(cursor, EmoticonEntry.COLUMN_NAME_EMOTICON_ID),
					getStringValueWithColumn(cursor, EmoticonEntry.COLUMN_NAME_DESCRIPTION),
					getStringValueWithColumn(cursor, EmoticonEntry.COLUMN_NAME_CONTENT))
				); 
			} while(cursor.moveToNext());
		}
		
		return emoticons;
	}
	
	private Cursor getCursorWithReadAllEmoticons(SQLiteDatabase db) {
		String[] projection = {
				EmoticonEntry.COLUMN_NAME_EMOTICON_ID,
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
	
	private Cursor getCursorWithSpecificEmoticon(SQLiteDatabase db, int id) {
		String[] projection = {
				EmoticonEntry.COLUMN_NAME_EMOTICON_ID,
				EmoticonEntry.COLUMN_NAME_CONTENT,
				EmoticonEntry.COLUMN_NAME_DESCRIPTION
		};

		return db.query(
				EmoticonEntry.TABLE_NAME, 
				projection, 
				EmoticonEntry.COLUMN_NAME_EMOTICON_ID + " = " + id, 
				null, 
				null, 
				null, 
				EmoticonEntry.COLUMN_NAME_DESCRIPTION);		
	}


}
