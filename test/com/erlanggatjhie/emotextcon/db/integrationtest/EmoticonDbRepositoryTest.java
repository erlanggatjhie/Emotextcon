package com.erlanggatjhie.emotextcon.db.integrationtest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.azeckoski.reflectutils.ReflectUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.erlanggatjhie.emotextcon.db.EmoticonDbHelper;
import com.erlanggatjhie.emotextcon.db.EmoticonDbRepository;
import com.erlanggatjhie.emotextcon.db.EmoticonEntry;
import com.erlanggatjhie.emotextcon.model.Emoticon;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowSQLiteOpenHelper;

@RunWith(RobolectricTestRunner.class)
public class EmoticonDbRepositoryTest {
	private final Emoticon EMOTICON_1 = new Emoticon(1, "desc1", "content1");
	private final Emoticon EMOTICON_2 = new Emoticon(2, "desc2", "content2");
	private final Emoticon EMOTICON_3 = new Emoticon(3, "desc3", "content3");
	private final Emoticon EMOTICON_4 = new Emoticon(4, "desc4", "content4");
	
	private EmoticonDbRepository emoticonDbRepository;
	private SQLiteDatabase db;
	
	@Before
	public void setup() {
		setupDbObjectAndMockDbHelper();
		prepareTestData();
	}

	private void setupDbObjectAndMockDbHelper() {
		emoticonDbRepository = new EmoticonDbRepository(null);
		EmoticonDbHelper dbHelper = Mockito.spy(new EmoticonDbHelper(null));
		ShadowSQLiteOpenHelper shadowHelper = Robolectric.shadowOf(dbHelper);
		db = Mockito.spy(shadowHelper.getReadableDatabase());
		dbHelper.onCreate(db);
		
		Mockito.when(dbHelper.getWritableDatabase()).thenReturn(db);
		Mockito.when(dbHelper.getReadableDatabase()).thenReturn(db);
		Mockito.doNothing().when(db).close();
		
		ReflectUtils.getInstance().setFieldValue(emoticonDbRepository, "dbHelper", dbHelper);
	}
	
	@Test
	public void shouldReturnAllEmoticons() {
		List<Emoticon> expectedEmoticons = new ArrayList<Emoticon>();
		expectedEmoticons.add(EMOTICON_1);
		expectedEmoticons.add(EMOTICON_2);
		expectedEmoticons.add(EMOTICON_3);
		
		List<Emoticon> actualEmoticons = emoticonDbRepository.getAllEmoticons();
		
		assertTwoListHasTheSameEmoticons(actualEmoticons, expectedEmoticons);
	}
	
	@Test
	public void shouldUpdateEmoticon() {
		List<Emoticon> expectedEmoticons = new ArrayList<Emoticon>();
		expectedEmoticons.add(EMOTICON_1);
		expectedEmoticons.add(EMOTICON_2);
		
		EMOTICON_3.setContent(EMOTICON_4.getContent());
		EMOTICON_3.setDescription(EMOTICON_4.getDescription());
		
		expectedEmoticons.add(EMOTICON_3);
		
		assertThat("update emoticon return false", emoticonDbRepository.updateEmoticon(EMOTICON_3), is(true));
		
		List<Emoticon> actualEmoticons = emoticonDbRepository.getAllEmoticons();
		assertTwoListHasTheSameEmoticons(actualEmoticons, expectedEmoticons);	
	}
	
	@Test
	public void shouldInsertEmoticon() {
		List<Emoticon> expectedEmoticons = new ArrayList<Emoticon>();
		expectedEmoticons.add(EMOTICON_1);
		expectedEmoticons.add(EMOTICON_2);
		expectedEmoticons.add(EMOTICON_3);
		expectedEmoticons.add(EMOTICON_4);
		
		assertThat("insert emoticon return false", emoticonDbRepository.insertEmoticon(EMOTICON_4), is(true));
		List<Emoticon> actualEmoticons = emoticonDbRepository.getAllEmoticons();
		
		assertTwoListHasTheSameEmoticons(actualEmoticons, expectedEmoticons);
	}
	
	@Test
	public void shouldDeleteEmoticon() {
		List<Emoticon> expectedEmoticons = new ArrayList<Emoticon>();
		expectedEmoticons.add(EMOTICON_1);
		expectedEmoticons.add(EMOTICON_2);
		
		assertThat("delete emoticon return false", emoticonDbRepository.deleteEmoticon(EMOTICON_3.getId()), is(true));
		List<Emoticon> actualEmoticons = emoticonDbRepository.getAllEmoticons();
		
		assertTwoListHasTheSameEmoticons(actualEmoticons, expectedEmoticons);
	}
	
	@Test
	public void shouldDeleteAllEmoticons() {
		List<Emoticon> expectedEmoticons = new ArrayList<Emoticon>();
		
		emoticonDbRepository.deleteAllEmoticons();
		List<Emoticon> actualEmoticons = emoticonDbRepository.getAllEmoticons();
		
		assertTwoListHasTheSameEmoticons(actualEmoticons, expectedEmoticons);		
	}
	
	@Test
	public void shouldGetSpecificEmoticon() throws Exception {
		assertThat("Doesn't get the right emoticon", emoticonDbRepository.getEmoticonById(EMOTICON_1.getId()), equalTo(EMOTICON_1));
	}
	
	
	private void prepareTestData() {
		emoticonDbRepository.deleteAllEmoticons();
		insertEmoticonToDb(db, EMOTICON_1);	
		insertEmoticonToDb(db, EMOTICON_2);	
		insertEmoticonToDb(db, EMOTICON_3);
 	}
	
	private void insertEmoticonToDb(SQLiteDatabase db, Emoticon emoticon) {
		db.insertOrThrow(EmoticonEntry.TABLE_NAME, null, getContentValuesForEmoticon(emoticon));		
	}	
	
	private ContentValues getContentValuesForEmoticon(Emoticon emoticon) {
		ContentValues values = new ContentValues();
		values.put(EmoticonEntry.COLUMN_NAME_EMOTICON_ID, emoticon.getId());
		values.put(EmoticonEntry.COLUMN_NAME_CONTENT, emoticon.getContent());
		values.put(EmoticonEntry.COLUMN_NAME_DESCRIPTION, emoticon.getDescription());
		
		return values;
	}
	
	private void assertTwoListHasTheSameEmoticons(List<Emoticon> actualEmoticons, List<Emoticon> expectedEmoticons) {
		assertThat(actualEmoticons.size(), equalTo(expectedEmoticons.size()));
		for (int i = 0 ; i < expectedEmoticons.size(); i++) {
			assertThat("Does not read all emoticons", actualEmoticons.get(i), equalTo(expectedEmoticons.get(i)));
		}				
	}
}
