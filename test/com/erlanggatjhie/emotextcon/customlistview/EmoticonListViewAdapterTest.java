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
import com.erlanggatjhie.emotextcon.model.Emoticon;
import com.xtremelabs.robolectric.RobolectricTestRunner;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

@RunWith(RobolectricTestRunner.class)
public class EmoticonListViewAdapterTest {
	private static final Emoticon EMOTICON_1 = new Emoticon("desc1", "content1");
	private static final Emoticon EMOTICON_2 = new Emoticon("desc2", "content2");
	private static final Emoticon EMOTICON_3 = new Emoticon("desc3", "content3");
	
	private EmoticonListViewAdapter emoticonListViewAdapter;
	private Context mockContext;
	private LayoutInflater mockLayoutInflater;
	private View mockConvertView;
	private List<Emoticon> items;
	
	@Before
	public void setup() {
		mockContext = mock(Context.class);
		mockLayoutInflater = mock(LayoutInflater.class);
		mockConvertView = spy(new View(mockContext));		
		
		when(mockContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE)).thenReturn(mockLayoutInflater);
		when(mockLayoutInflater.inflate(R.layout.emoticon_row_item, null)).thenReturn(mockConvertView);
		
		prepareData();
		emoticonListViewAdapter = new EmoticonListViewAdapter(mockContext, 0, items);
	}
	
	@Test
	public void shouldGetSecondItemWhenNoListItemPreviouslyDisplayed() {
		View rowItem = getRowItemWhenNoListPreviouslyDisplayed(1);
		
		TextView emoticonDescriptionTextView = (TextView) rowItem.findViewById(R.id.emoticonDescriptionTextView);
		TextView emoticonContentTextView = (TextView) rowItem.findViewById(R.id.emoticonContentTextView);
		
		assertThat(emoticonDescriptionTextView.getText().toString(), equalTo(EMOTICON_2.getDescription()));
		assertThat(emoticonContentTextView.getText().toString(), equalTo(EMOTICON_2.getContent()));		
	}
	
	@Test
	public void shouldGetSecondItemWhenThereIsListItemPreviouslyDisplayed() {	
		View rowItem = getRowItemWhenThereIsListItemPreviouslyDisplayed(1);
		
		TextView emoticonDescriptionTextView = (TextView) rowItem.findViewById(R.id.emoticonDescriptionTextView);
		TextView emoticonContentTextView = (TextView) rowItem.findViewById(R.id.emoticonContentTextView);
		
		assertThat(emoticonDescriptionTextView.getText().toString(), equalTo(EMOTICON_2.getDescription()));
		assertThat(emoticonContentTextView.getText().toString(), equalTo(EMOTICON_2.getContent()));				
	}
	
	private void prepareData() {
		items = new ArrayList<Emoticon>();
		items.add(EMOTICON_1);
		items.add(EMOTICON_2);
		items.add(EMOTICON_3);
	}
	
	private View getRowItemWhenNoListPreviouslyDisplayed(int position) {
		when(mockConvertView.findViewById(R.id.emoticonDescriptionTextView)).thenReturn(new TextView(null));
		when(mockConvertView.findViewById(R.id.emoticonContentTextView)).thenReturn(new TextView(null));
		
		return emoticonListViewAdapter.getView(position, null, null);
	}
	
	private View getRowItemWhenThereIsListItemPreviouslyDisplayed(int position) {
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
