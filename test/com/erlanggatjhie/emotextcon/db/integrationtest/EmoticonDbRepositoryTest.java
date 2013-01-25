package com.erlanggatjhie.emotextcon.db.integrationtest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.erlanggatjhie.emotextcon.db.EmoticonDbHelper;
import com.erlanggatjhie.emotextcon.db.EmoticonDbRepository;
import com.erlanggatjhie.emotextcon.db.EmoticonEntry;
import com.erlanggatjhie.emotextcon.model.Emoticon;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class EmoticonDbRepositoryTest {
	private static final Emoticon EMOTICON_1 = new Emoticon(1, "desc1", "content1");
	private static final Emoticon EMOTICON_2 = new Emoticon(2, "desc2", "content2");
	private static final Emoticon EMOTICON_3 = new Emoticon(3, "desc3", "content3");
	private static final Emoticon EMOTICON_4 = new Emoticon(4, "desc4", "content4");
	
	private EmoticonDbRepository emoticonDbRepository;
	private SQLiteDatabase db;
	
	@Before
	public void setup() {
		emoticonDbRepository = new EmoticonDbRepository(null);
		prepareTestData();
	}
	
	@Test
	public void shouldReturnAllEmoticons() {
		List<Emoticon> expectedEmoticons = new ArrayList<Emoticon>();
		expectedEmoticons.add(EMOTICON_1);
		expectedEmoticons.add(EMOTICON_2);
		expectedEmoticons.add(EMOTICON_3);
		
		List<Emoticon> actualEmoticons = emoticonDbRepository.getAllEmoticons();
		
		assertThat(actualEmoticons.size(), equalTo(expectedEmoticons.size()));
		for (int i = 0 ; i < expectedEmoticons.size(); i++) {
			assertThat(actualEmoticons.get(i), equalTo(expectedEmoticons.get(i)));
		}
	}
	
	@Test
	public void shouldInsertEmoticon() {
		List<Emoticon> expectedEmoticons = new ArrayList<Emoticon>();
		expectedEmoticons.add(EMOTICON_1);
		expectedEmoticons.add(EMOTICON_2);
		expectedEmoticons.add(EMOTICON_3);
		expectedEmoticons.add(EMOTICON_4);
		
		emoticonDbRepository.insertEmoticon(EMOTICON_4);
		
		List<Emoticon> actualEmoticons = emoticonDbRepository.getAllEmoticons();
		assertThat(actualEmoticons.size(), equalTo(expectedEmoticons.size()));
		for (int i = 0 ; i < expectedEmoticons.size(); i++) {
			assertThat(actualEmoticons.get(i), equalTo(expectedEmoticons.get(i)));
		}
	}
	
	@Test
	public void shouldUpdateEmoticon() {
		
	}
	
	private void prepareTestData() {
		db = new EmoticonDbHelper(null).getReadableDatabase();	
		insertEmoticonToDb(db, EMOTICON_1);	
		insertEmoticonToDb(db, EMOTICON_2);	
		insertEmoticonToDb(db, EMOTICON_3);	
	}
	
	private void insertEmoticonToDb(SQLiteDatabase db, Emoticon emoticon) {
		db.insertOrThrow(EmoticonEntry.TABLE_NAME, null, getContentValuesForEmoticon(emoticon));		
	}	
	
	private ContentValues getContentValuesForEmoticon(Emoticon emoticon) {
		ContentValues values = new ContentValues();
		values.put(EmoticonEntry.COLUMN_NAME_CONTENT, emoticon.getContent());
		values.put(EmoticonEntry.COLUMN_NAME_DESCRIPTION, emoticon.getDescription());
		
		return values;
	}
	

}
