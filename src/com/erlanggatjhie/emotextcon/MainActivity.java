package com.erlanggatjhie.emotextcon;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.GridView;

import com.erlanggatjhie.emotextcon.customlistview.EmoticonListViewAdapter;
import com.erlanggatjhie.emotextcon.model.Emoticon;

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
		emoticons = getEmoticonsFromDatabase();
		
		GridView emoticonListView = (GridView) findViewById(R.id.emoticonGridView);
		emoticonListView.setAdapter(new EmoticonListViewAdapter(this, R.layout.emoticon_row_item, emoticons));
	}
	
	private ArrayList<Emoticon> getEmoticonsFromDatabase() {
		return new ArrayList<Emoticon>();
	}
}
