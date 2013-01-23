package com.erlanggatjhie.emotextcon;

import java.util.ArrayList;
import java.util.List;

import com.erlanggatjhie.emotextcon.customlistview.EmoticonListViewAdapter;
import com.erlanggatjhie.emotextcon.model.Emoticon;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private List<Emoticon> emoticons;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initialiseComponent();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private void initialiseComponent() {
		loadEmoticonsFromDatabase();
		
		ListView emoticonListView = (ListView) findViewById(R.id.emoticonListView);
		
		View viewWithButton = getViewWithButtonText();
		((ViewGroup)emoticonListView.getParent()).addView(viewWithButton); 
		
		emoticonListView.setEmptyView(viewWithButton);
		emoticonListView.setAdapter(new EmoticonListViewAdapter(this, R.layout.emoticon_row_item, emoticons));
	}
	
	private void loadEmoticonsFromDatabase() {
		emoticons = new ArrayList<Emoticon>();
	}
	
	private View getViewWithButtonText() {
		View view = getLayoutInflater().inflate(R.layout.emoticon_row_item, null);
		TextView descTextView = (TextView) view.findViewById(R.id.emoticonDescriptionTextView);
		TextView contentTextView = (TextView) view.findViewById(R.id.emoticonContentTextView);
		descTextView.setText("");
		contentTextView.setText("+");
		
		return view;
	}
	
}
