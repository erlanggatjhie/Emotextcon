package com.erlanggatjhie.emotextcon.activities;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.IsNot.not;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.widget.ListView;
import android.widget.TextView;

import com.erlanggatjhie.emotextcon.MainActivity;
import com.erlanggatjhie.emotextcon.R;
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
	public void shouldDisplayTheFirstEmoticon() {
		ListView emoticonListView = (ListView) mainActivity.findViewById(R.id.emoticonListView);
		assertThat(emoticonListView.getItemAtPosition(0), not(null));
	}
}
