package com.erlanggatjhie.emotextcon.db;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.erlanggatjhie.emotextcon.model.Emoticon;

public class EmoticonDbRepository {
	private EmoticonDbHelper dbHelper;
	
	public EmoticonDbRepository(Context context) {
		this.dbHelper = new EmoticonDbHelper(context);
	}
	
	public List<Emoticon> getAllEmoticons() {
		//SQLiteDatabase db = dbHelper.getReadableDatabase();
		return null;
		
	}
}
