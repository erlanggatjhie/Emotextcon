package com.erlanggatjhie.emotextcon;

import java.util.ArrayList;
import java.util.List;

import com.erlanggatjhie.emotextcon.model.Emoticon;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

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
		if (emoticons.isEmpty()) {
//			emoticonListView.addView(new Button(getApplicationContext()));
		}
	}
	
	private void loadEmoticonsFromDatabase() {
		emoticons = new ArrayList<Emoticon>();
	}
	
}
