package com.erlanggatjhie.emotextcon.activities;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.addEmoticonMenuItem:
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), AddEmoticonActivity.class);
				startActivity(intent);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void initialiseComponent() {
		dbRepository = new EmoticonDbRepository(this);

		GridView emoticonGridView = (GridView) findViewById(R.id.emoticonGridView);
		emoticonGridView.setEmptyView(findViewById(R.id.noEmoticonTextView));

		refreshListView();
	}

	protected void refreshListView() {
		emoticons = dbRepository.getAllEmoticons();
		GridView emoticonGridView = (GridView) findViewById(R.id.emoticonGridView);
		emoticonGridView.setAdapter(new EmoticonListViewAdapter(this,
				R.layout.emoticon_row_item, emoticons));
	}

}
