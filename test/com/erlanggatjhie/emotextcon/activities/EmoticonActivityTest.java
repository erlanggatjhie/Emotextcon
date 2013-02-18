package com.erlanggatjhie.emotextcon.activities;

import org.azeckoski.reflectutils.ReflectUtils;
import org.mockito.Mockito;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;

import com.erlanggatjhie.emotextcon.db.EmoticonDbHelper;
import com.erlanggatjhie.emotextcon.db.EmoticonDbRepository;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.shadows.ShadowSQLiteOpenHelper;

public abstract class EmoticonActivityTest {
	protected EmoticonDbRepository emoticonDbRepository;
	
	protected <T extends Activity> void setupDbRepository(T activity) {
		emoticonDbRepository = (EmoticonDbRepository) ReflectUtils.getInstance().getFieldValue(activity, "dbRepository");
		
		EmoticonDbHelper dbHelper = Mockito.spy(new EmoticonDbHelper(null));
		ShadowSQLiteOpenHelper shadowHelper = Robolectric.shadowOf(dbHelper);
		SQLiteDatabase db = Mockito.spy(shadowHelper.getReadableDatabase());
		dbHelper.onCreate(db);
		
		Mockito.when(dbHelper.getWritableDatabase()).thenReturn(db);
		Mockito.when(dbHelper.getReadableDatabase()).thenReturn(db);
		Mockito.doNothing().when(db).close();
		
		ReflectUtils.getInstance().setFieldValue(emoticonDbRepository, "dbHelper", dbHelper);		
	}
	
}
