package com.erlanggatjhie.emotextcon.activities;

import static com.xtremelabs.robolectric.Robolectric.shadowOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Intent;
import android.view.MenuInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowActivity;
import com.xtremelabs.robolectric.shadows.ShadowIntent;
import com.xtremelabs.robolectric.tester.android.view.TestMenu;
import com.xtremelabs.robolectric.tester.android.view.TestMenuItem;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest extends EmoticonActivityTest {
	private MainActivity mainActivity;
	
	@Before
	public void setup() {
		mainActivity = new MainActivity();
		mainActivity.onCreate(null);
		
		setupDbRepository(mainActivity);
	}
	
	@Test
	public void shouldHaveTitle() {
		TextView titleView = (TextView) mainActivity.findViewById(R.id.titleTextView);
		assertThat("Title is different that it is expected",
				titleView.getText().toString(), equalTo(mainActivity.getString(R.string.app_name)));
	}
	
	@Test
	public void shouldDisplayEmptyView() {			
		emoticonDbRepository.deleteAllEmoticons();
		mainActivity.refreshListView();
		
		GridView emoticonGridView = (GridView) mainActivity.findViewById(R.id.emoticonGridView);
		
		assertThat(emoticonGridView.getChildCount(), is(0));
		
		TextView view = (TextView) emoticonGridView.getEmptyView();
		
		assertThat("No Emoticon message is different",
				view.getText().toString(), equalTo(mainActivity.getString(R.string.no_emoticon)));
		
		assertThat("No Emoticon message is not displayed",
				view.getVisibility(), equalTo(View.VISIBLE));
	}
	
	@Test
	public void shouldGoToAddEmoticonActivity() {
		mainActivity.onOptionsItemSelected(new TestMenuItem(R.id.addEmoticonMenuItem));

		ShadowActivity shadowActivity = shadowOf(mainActivity);
		Intent startedIntent = shadowActivity.getNextStartedActivity();
		ShadowIntent shadowIntent = shadowOf(startedIntent);

		assertThat("Click on add menu does not go to add emoticon activity",
				shadowIntent.getIntentClass().getName(), equalTo(AddEmoticonActivity.class.getName()));
	}
	
	@Test
	public void shouldHaveAddMenuItem() {
		TestMenu testMenu = new TestMenu(mainActivity);
		new MenuInflater(mainActivity).inflate(R.menu.activity_main, testMenu);

		assertThat("Add menu item does not exist",
				testMenu.findMenuItem("@string/add_emoticon_menu_item"), notNullValue());
	}
}
