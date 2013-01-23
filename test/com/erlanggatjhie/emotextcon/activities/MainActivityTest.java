package com.erlanggatjhie.emotextcon.activities;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.IsNot.not;

import java.util.ArrayList;
import java.util.List;

import org.azeckoski.reflectutils.ReflectUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.erlanggatjhie.emotextcon.MainActivity;
import com.erlanggatjhie.emotextcon.R;
import com.erlanggatjhie.emotextcon.customlistview.EmoticonListViewAdapter;
import com.erlanggatjhie.emotextcon.model.Emoticon;
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
}
