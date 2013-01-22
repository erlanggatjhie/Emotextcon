package com.erlanggatjhie.emotextcon.customlistview;

import java.util.List;

import com.erlanggatjhie.emotextcon.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EmoticonListViewAdapter extends ArrayAdapter<EmoticonRowItem>{
	private Context context;
	
	public EmoticonListViewAdapter(Context context, int textViewResourceId,
			List<EmoticonRowItem> items) {
		super(context, textViewResourceId, items);
		this.context = context;
	}
	
	public class EmoticonViewHolder {
		TextView descTextView;
		TextView contentTextView;
	}
	
    public View getView(int position, View convertView, ViewGroup parent) {
    	EmoticonViewHolder holder = null;
        EmoticonRowItem rowItem = getItem(position);
 
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.emoticon_row_item, null);
            holder = new EmoticonViewHolder();
            holder.descTextView = (TextView) convertView.findViewById(R.id.emoticonDescriptionTextView);
            holder.contentTextView = (TextView) convertView.findViewById(R.id.emoticonContentTextView);
            convertView.setTag(holder);
        } else {
            holder = (EmoticonViewHolder) convertView.getTag();
        }
 
        holder.descTextView.setText(rowItem.getDescription());
        holder.contentTextView.setText(rowItem.getContent());
 
        return convertView;
    }
}
