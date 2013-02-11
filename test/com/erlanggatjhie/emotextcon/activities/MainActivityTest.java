package com.erlanggatjhie.emotextcon.activities;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.azeckoski.reflectutils.ReflectUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.erlanggatjhie.emotextcon.db.EmoticonDbRepository;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

	private MainActivity mainActivity;
	
	@Before
	public void setup() {
		mainActivity = new MainActivity();
		mainActivity.onCreate(null);
	}
	
	@Test
	public void shouldHaveTitle() {
		TextView titleView = (TextView) mainActivity.findViewById(R.id.titleTextView);
		assertThat(titleView.getText().toString(), equalTo("Emotextcon"));
	}
	
	@Test
	public void shouldDisplayEmptyView() {	
		EmoticonDbRepository dbRepository = (EmoticonDbRepository) 
				ReflectUtils.getInstance().getFieldValue(mainActivity, "dbRepository");
		
		dbRepository.deleteAllEmoticons();
		mainActivity.refreshListView();
		
		GridView emoticonGridView = (GridView) mainActivity.findViewById(R.id.emoticonGridView);
		
		assertThat(emoticonGridView.getChildCount(), is(0));
		
		TextView view = (TextView) emoticonGridView.getEmptyView();
		
		assertThat(view.getText().toString(), equalTo(mainActivity.getString(R.string.no_emoticon)));
		assertThat(view.getVisibility(), equalTo(View.VISIBLE));
	}
}
