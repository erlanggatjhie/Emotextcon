package com.erlanggatjhie.emotextcon.customlistview;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.spy;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.erlanggatjhie.emotextcon.R;
import com.xtremelabs.robolectric.RobolectricTestRunner;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

@RunWith(RobolectricTestRunner.class)
public class EmoticonListViewAdapterTest {
	private EmoticonListViewAdapter emoticonListViewAdapter;
	private Context mockContext;
	private LayoutInflater mockLayoutInflater;
	private View mockConvertView;
	private List<EmoticonRowItem> items;
	
	@Before
	public void setup() {
		mockContext = mock(Context.class);
		mockLayoutInflater = mock(LayoutInflater.class);
		mockConvertView = spy(new View(mockContext));		
		
		when(mockContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE)).thenReturn(mockLayoutInflater);
		when(mockLayoutInflater.inflate(R.layout.emoticon_row_item, null)).thenReturn(mockConvertView);
		
		items = new ArrayList<EmoticonRowItem>();
		emoticonListViewAdapter = new EmoticonListViewAdapter(mockContext, 0, items);
	}
	
	@Test
	public void shouldGetFirstItemWhenNoListItemPreviouslyDisplayed() {
		String expectedDescription = "testDesc";
		String expectedContent = "testContent";
		
		when(mockConvertView.findViewById(R.id.emoticonDescriptionTextView)).thenReturn(new TextView(null));
		when(mockConvertView.findViewById(R.id.emoticonContentTextView)).thenReturn(new TextView(null));
		
		items.add(new EmoticonRowItem(expectedDescription, expectedContent));
		
		View rowItem = emoticonListViewAdapter.getView(0, null, null);
		
		TextView emoticonDescriptionTextView = (TextView) rowItem.findViewById(R.id.emoticonDescriptionTextView);
		TextView emoticonContentTextView = (TextView) rowItem.findViewById(R.id.emoticonContentTextView);
		
		assertThat(emoticonDescriptionTextView.getText().toString(), equalTo(expectedDescription));
		assertThat(emoticonContentTextView.getText().toString(), equalTo(expectedContent));
	}
	
	@Test
	public void shouldGetFirstItemWhenThereIsListItemPreviouslyDisplayed() {
		String expectedDescription = "testDesc";
		String expectedContent = "testContent";
		
		items.add(new EmoticonRowItem(expectedDescription, expectedContent));
		
		EmoticonListViewAdapter.EmoticonViewHolder viewHolder = emoticonListViewAdapter.new EmoticonViewHolder();
		viewHolder.contentTextView = new TextView(null);
		viewHolder.descTextView = new TextView(null);
		mockConvertView.setTag(viewHolder);
		
		when(mockConvertView.findViewById(R.id.emoticonDescriptionTextView)).thenReturn(viewHolder.descTextView);
		when(mockConvertView.findViewById(R.id.emoticonContentTextView)).thenReturn(viewHolder.contentTextView);
		
		EmoticonListViewAdapter emoticonListViewAdapter = new EmoticonListViewAdapter(mockContext, 0, items);
		View rowItem = emoticonListViewAdapter.getView(0, mockConvertView, null);
		
		TextView emoticonDescriptionTextView = (TextView) rowItem.findViewById(R.id.emoticonDescriptionTextView);
		TextView emoticonContentTextView = (TextView) rowItem.findViewById(R.id.emoticonContentTextView);
		
		assertThat(emoticonDescriptionTextView.getText().toString(), equalTo(expectedDescription));
		assertThat(emoticonContentTextView.getText().toString(), equalTo(expectedContent));		
	}
}
