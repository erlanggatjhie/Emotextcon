package com.erlanggatjhie.emotextcon.activities;

import static com.xtremelabs.robolectric.Robolectric.shadowOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.azeckoski.reflectutils.ReflectUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Intent;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.GridView;
import android.widget.TextView;

import com.erlanggatjhie.emotextcon.constants.RequestResultConstants;
import com.erlanggatjhie.emotextcon.model.Emoticon;
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
	
	@Test
	public void shouldAddEmoticonAfterAddingEmoticon() {
		emoticonDbRepository.deleteAllEmoticons();
		
		String expectedDescription = "desc";
		String expectedContent = "content";
		
		mainActivity.onOptionsItemSelected(new TestMenuItem(R.id.addEmoticonMenuItem));
		
		emoticonDbRepository.insertEmoticon(new Emoticon(expectedDescription, expectedContent));
		
		shadowOf(mainActivity).receiveResult(new Intent(mainActivity, AddEmoticonActivity.class), 
				RequestResultConstants.ADD_EDIT_EMOTICON_RESULT, 
				new Intent().putExtra("IS_UPDATED", true));
		
		GridView emoticonGridView = (GridView) mainActivity.findViewById(R.id.emoticonGridView);
		assertThat(emoticonGridView.getChildCount(), is(1));		
		
		View emoticonTextView = emoticonGridView.getChildAt(0);
		TextView descriptionTextView = (TextView) emoticonTextView.findViewById(R.id.emoticonDescriptionTextView);
		TextView contentTextView = (TextView) emoticonTextView.findViewById(R.id.emoticonContentTextView);
		
		assertThat("Added emoticon has different description",
				descriptionTextView.getText().toString(), equalTo(expectedDescription));
		
		assertThat("Added emoticon has different content",
				contentTextView.getText().toString(), equalTo(expectedContent));
			
	}
	
	@Test
	public void shouldGoToEditEmoticonActivity() {
		int emoticonId = 100;
		
		emoticonDbRepository.deleteAllEmoticons();
		emoticonDbRepository.insertEmoticon(new Emoticon(emoticonId, "desc", "content"));
		
		mainActivity.refreshListView();
		
		MenuItem spyTestMenuItem = mock(MenuItem.class);
		when(spyTestMenuItem.getItemId()).thenReturn(R.id.editEmoticonContextualMenuItem);

		ContextMenuInfo menuInfo = new AdapterContextMenuInfo(null, 0, emoticonId);
		ReflectUtils.getInstance().setFieldValue(menuInfo, "id", emoticonId);
		when(spyTestMenuItem.getMenuInfo()).thenReturn(menuInfo);
		
		mainActivity.onContextItemSelected(spyTestMenuItem);
		
		ShadowActivity shadowActivity = shadowOf(mainActivity);
		Intent startedIntent = shadowActivity.getNextStartedActivity();
		ShadowIntent shadowIntent = shadowOf(startedIntent);

		assertThat("Click on edit menu does not go to edit emoticon activity",
				shadowIntent.getIntentClass().getName(), equalTo(EditEmoticonActivity.class.getName()));		
		
		assertThat("Incorrect emoticon id", 
				shadowIntent.getExtras().getInt(RequestResultConstants.EMOTICON_ID_INTENT_KEY), 
				is(emoticonId));
	}
}
