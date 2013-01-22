package com.erlanggatjhie.emotextcon.customlistview;

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;

public class EmoticonListViewAdapter extends ArrayAdapter<EmoticonRowItem>{
	private Context context;
	
	public EmoticonListViewAdapter(Context context, int textViewResourceId,
			List<EmoticonRowItem> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
	}
	
	private class EmoticonViewHolder {
		TextView descTextView;
		TextView contentTextView;
	}

}
