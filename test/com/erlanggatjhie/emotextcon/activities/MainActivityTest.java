package com.erlanggatjhie.emotextcon.activities;

import static com.xtremelabs.robolectric.Robolectric.shadowOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import org.azeckoski.reflectutils.ReflectUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.MenuInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.erlanggatjhie.emotextcon.db.EmoticonDbHelper;
import com.erlanggatjhie.emotextcon.db.EmoticonDbRepository;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowActivity;
import com.xtremelabs.robolectric.shadows.ShadowIntent;
import com.xtremelabs.robolectric.shadows.ShadowSQLiteOpenHelper;
import com.xtremelabs.robolectric.tester.android.view.TestMenu;
import com.xtremelabs.robolectric.tester.android.view.TestMenuItem;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

	private MainActivity mainActivity;
	private EmoticonDbRepository dbRepository;
	
	@Before
	public void setup() {
		mainActivity = new MainActivity();
		mainActivity.onCreate(null);
		
		setupDbRepository();
	}

	private void setupDbRepository() {
		dbRepository = (EmoticonDbRepository) ReflectUtils.getInstance().getFieldValue(mainActivity, "dbRepository");
		
		EmoticonDbHelper dbHelper = Mockito.spy(new EmoticonDbHelper(null));
		ShadowSQLiteOpenHelper shadowHelper = Robolectric.shadowOf(dbHelper);
		SQLiteDatabase db = Mockito.spy(shadowHelper.getReadableDatabase());
		dbHelper.onCreate(db);
		
		Mockito.when(dbHelper.getWritableDatabase()).thenReturn(db);
		Mockito.when(dbHelper.getReadableDatabase()).thenReturn(db);
		Mockito.doNothing().when(db).close();
		
		ReflectUtils.getInstance().setFieldValue(dbRepository, "dbHelper", dbHelper);
	}
	
	@Test
	public void shouldHaveTitle() {
		TextView titleView = (TextView) mainActivity.findViewById(R.id.titleTextView);
		assertThat(titleView.getText().toString(), equalTo("Emotextcon"));
	}
	
	@Test
	public void shouldDisplayEmptyView() {			
		dbRepository.deleteAllEmoticons();
		mainActivity.refreshListView();
		
		GridView emoticonGridView = (GridView) mainActivity.findViewById(R.id.emoticonGridView);
		
		assertThat(emoticonGridView.getChildCount(), is(0));
		
		TextView view = (TextView) emoticonGridView.getEmptyView();
		
		assertThat(view.getText().toString(), equalTo(mainActivity.getString(R.string.no_emoticon)));
		assertThat(view.getVisibility(), equalTo(View.VISIBLE));
	}
	
	@Test
	public void shouldGoToAddEmoticonActivity() {
		mainActivity.onOptionsItemSelected(new TestMenuItem(R.id.addEmoticonMenuItem));

		ShadowActivity shadowActivity = shadowOf(mainActivity);
		Intent startedIntent = shadowActivity.getNextStartedActivity();
		ShadowIntent shadowIntent = shadowOf(startedIntent);

		assertThat(shadowIntent.getIntentClass().getName(), equalTo(AddEmoticonActivity.class.getName()));
	}
	
	@Test
	public void shouldHaveAddMenuItem() {
		TestMenu testMenu = new TestMenu(mainActivity);
		new MenuInflater(mainActivity).inflate(R.menu.activity_main, testMenu);

		assertThat(testMenu.findMenuItem("@string/add_emoticon_menu_item"), notNullValue());
	}
}
