package com.erlanggatjhie.emotextcon.activities;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.erlanggatjhie.emotextcon.MainActivity;
import com.erlanggatjhie.emotextcon.R;
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
		EmoticonDbRepository dbRepository = new EmoticonDbRepository(null);
		dbRepository.deleteAllEmoticons();
		
		GridView emoticonListView = (GridView) mainActivity.findViewById(R.id.emoticonGridView);
		
		assertThat(emoticonListView.getChildCount(), is(0));
		
		TextView view = (TextView) emoticonListView.getEmptyView();
		
		assertThat(view.getText().toString(), equalTo(mainActivity.getString(R.string.no_emoticon)));
		assertThat(view.getVisibility(), equalTo(View.VISIBLE));
	}
}
