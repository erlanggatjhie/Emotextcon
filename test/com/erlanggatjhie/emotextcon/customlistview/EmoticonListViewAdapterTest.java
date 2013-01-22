package com.erlanggatjhie.emotextcon.customlistview;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;

import java.util.ArrayList;
import java.util.List;


import org.junit.Test;
import org.junit.runner.RunWith;

import com.erlanggatjhie.emotextcon.R;
import com.xtremelabs.robolectric.RobolectricTestRunner;

import android.view.View;
import android.widget.TextView;

@RunWith(RobolectricTestRunner.class)
public class EmoticonListViewAdapterTest {
	
	@Test
	public void shouldGetPlusIconWhenNoItemExist() {
		List<EmoticonRowItem> items = new ArrayList<EmoticonRowItem>();
		
		EmoticonListViewAdapter emoticonListViewAdapter = new EmoticonListViewAdapter(null, 0, items);
		View rowItem = emoticonListViewAdapter.getView(0, null, null);
		
		TextView emoticonDescriptionTextView = (TextView) rowItem.findViewById(R.id.emoticonDescriptionTextView);
		TextView emoticonContentTextView = (TextView) rowItem.findViewById(R.id.emoticonContentTextView);
		
		assertThat(emoticonDescriptionTextView.getText().toString(), equalTo("+"));
		assertThat(emoticonContentTextView.getText().toString().isEmpty(), is(true));
	}
}
