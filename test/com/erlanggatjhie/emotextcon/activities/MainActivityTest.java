package com.erlanggatjhie.emotextcon.activities;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.util.ArrayList;
import java.util.List;

import org.azeckoski.reflectutils.ReflectUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.erlanggatjhie.emotextcon.MainActivity;
import com.erlanggatjhie.emotextcon.R;
import com.erlanggatjhie.emotextcon.db.EmoticonDbRepository;
import com.erlanggatjhie.emotextcon.model.Emoticon;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowActivity;

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
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldDisplayEmptyView() {	
		GridView emoticonGridView = (GridView) mainActivity.findViewById(R.id.emoticonGridView);

		ArrayAdapter<Emoticon> adapter = (ArrayAdapter<Emoticon>) emoticonGridView.getAdapter();
		adapter.clear();
		emoticonGridView.setAdapter(adapter);
		
		assertThat(emoticonGridView.getChildCount(), is(0));
		
		TextView view = (TextView) emoticonGridView.getEmptyView();
		
		assertThat(view.getText().toString(), equalTo(mainActivity.getString(R.string.no_emoticon)));
		assertThat(view.getVisibility(), equalTo(View.VISIBLE));
	}
}
