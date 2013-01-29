package com.erlanggatjhie.emotextcon.customlistview;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.erlanggatjhie.emotextcon.activities.R;
import com.erlanggatjhie.emotextcon.model.Emoticon;

public class EmoticonListViewAdapter extends ArrayAdapter<Emoticon>{
	private Context context;
	
	public EmoticonListViewAdapter(Context context, int textViewResourceId,
			List<Emoticon> items) {
		super(context, textViewResourceId, items);
		this.context = context;
	}
	
	public class EmoticonViewHolder {
		TextView descTextView;
		TextView contentTextView;
	}
	
    public View getView(int position, View convertView, ViewGroup parent) {
    	EmoticonViewHolder holder = null;
        Emoticon emoticon = getItem(position);
 
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
 
        holder.descTextView.setText(emoticon.getDescription());
        holder.contentTextView.setText(emoticon.getContent());
 
        return convertView;
    }
}
