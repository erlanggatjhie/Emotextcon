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
		
		items.add(new EmoticonRowItem(expectedDescription, expectedContent));
		
		View rowItem = getRowItemWhenNoListPreviouslyDisplayed(0, items);
		
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

		View rowItem = getRowItemWhenThereIsListItemPreviouslyDisplayed(0, items);
		
		TextView emoticonDescriptionTextView = (TextView) rowItem.findViewById(R.id.emoticonDescriptionTextView);
		TextView emoticonContentTextView = (TextView) rowItem.findViewById(R.id.emoticonContentTextView);
		
		assertThat(emoticonDescriptionTextView.getText().toString(), equalTo(expectedDescription));
		assertThat(emoticonContentTextView.getText().toString(), equalTo(expectedContent));		
	}
	
	@Test
	public void shouldGetSecondItemWhenNoListItemPreviouslyDisplayed() {
		String expectedDescription = "testDesc";
		String expectedContent = "testContent";
		
		items.add(new EmoticonRowItem("desc2", "content2"));
		items.add(new EmoticonRowItem(expectedDescription, expectedContent));
		items.add(new EmoticonRowItem("desc3", "content3"));
		
		View rowItem = getRowItemWhenNoListPreviouslyDisplayed(1, items);
		
		TextView emoticonDescriptionTextView = (TextView) rowItem.findViewById(R.id.emoticonDescriptionTextView);
		TextView emoticonContentTextView = (TextView) rowItem.findViewById(R.id.emoticonContentTextView);
		
		assertThat(emoticonDescriptionTextView.getText().toString(), equalTo(expectedDescription));
		assertThat(emoticonContentTextView.getText().toString(), equalTo(expectedContent));		
	}
	
	@Test
	public void shouldGetSecondItemWhenThereIsListItemPreviouslyDisplayed() {
		String expectedDescription = "testDesc";
		String expectedContent = "testContent";
		
		items.add(new EmoticonRowItem("desc2", "content2"));
		items.add(new EmoticonRowItem(expectedDescription, expectedContent));
		items.add(new EmoticonRowItem("desc3", "content3"));
		
		View rowItem = getRowItemWhenThereIsListItemPreviouslyDisplayed(1, items);
		
		TextView emoticonDescriptionTextView = (TextView) rowItem.findViewById(R.id.emoticonDescriptionTextView);
		TextView emoticonContentTextView = (TextView) rowItem.findViewById(R.id.emoticonContentTextView);
		
		assertThat(emoticonDescriptionTextView.getText().toString(), equalTo(expectedDescription));
		assertThat(emoticonContentTextView.getText().toString(), equalTo(expectedContent));				
	}
	
	private View getRowItemWhenNoListPreviouslyDisplayed(int position, List<EmoticonRowItem> items) {
		when(mockConvertView.findViewById(R.id.emoticonDescriptionTextView)).thenReturn(new TextView(null));
		when(mockConvertView.findViewById(R.id.emoticonContentTextView)).thenReturn(new TextView(null));
		
		return emoticonListViewAdapter.getView(position, null, null);
	}
	
	private View getRowItemWhenThereIsListItemPreviouslyDisplayed(int position, List<EmoticonRowItem> items) {
		EmoticonListViewAdapter.EmoticonViewHolder viewHolder = emoticonListViewAdapter.new EmoticonViewHolder();
		viewHolder.contentTextView = new TextView(null);
		viewHolder.descTextView = new TextView(null);
		mockConvertView.setTag(viewHolder);
		
		when(mockConvertView.findViewById(R.id.emoticonDescriptionTextView)).thenReturn(viewHolder.descTextView);
		when(mockConvertView.findViewById(R.id.emoticonContentTextView)).thenReturn(viewHolder.contentTextView);
		when(mockConvertView.findViewById(R.id.emoticonDescriptionTextView)).thenReturn(viewHolder.descTextView);
		when(mockConvertView.findViewById(R.id.emoticonContentTextView)).thenReturn(viewHolder.contentTextView);
		
		return emoticonListViewAdapter.getView(position, mockConvertView, null);
	}
}
