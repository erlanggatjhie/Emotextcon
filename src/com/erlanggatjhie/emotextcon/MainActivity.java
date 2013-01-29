package com.erlanggatjhie.emotextcon;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.GridView;

import com.erlanggatjhie.emotextcon.customlistview.EmoticonListViewAdapter;
import com.erlanggatjhie.emotextcon.db.EmoticonDbRepository;
import com.erlanggatjhie.emotextcon.model.Emoticon;

public class MainActivity extends Activity {
	private List<Emoticon> emoticons;
	private EmoticonDbRepository dbRepository;
	
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
		dbRepository = new EmoticonDbRepository(this);
		emoticons = dbRepository.getAllEmoticons();
		
		GridView emoticonGridView = (GridView) findViewById(R.id.emoticonGridView);
		emoticonGridView.setEmptyView(findViewById(R.id.noEmoticonTextView));
		emoticonGridView.setAdapter(new EmoticonListViewAdapter(this, R.layout.emoticon_row_item, emoticons));
	}
}
