package com.erlanggatjhie.emotextcon.customlistview;

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EmoticonListViewAdapter extends ArrayAdapter<EmoticonRowItem>{
	private Context context;
	
	public EmoticonListViewAdapter(Context context, int textViewResourceId,
			List<EmoticonRowItem> items) {
		super(context, textViewResourceId, items);
		this.context = context;
	}
	
	private class EmoticonViewHolder {
		TextView descTextView;
		TextView contentTextView;
	}
	

}
