package com.erlanggatjhie.emotextcon.activities;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.GridView;

import com.erlanggatjhie.emotextcon.constants.RequestResultConstants;
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
				startActivityForResult(new Intent(this, AddEmoticonActivity.class), RequestResultConstants.ADD_EDIT_EMOTICON_REQUEST);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == RequestResultConstants.ADD_EDIT_EMOTICON_REQUEST &&
				resultCode == RequestResultConstants.ADD_EDIT_EMOTICON_RESULT &&
				data.getBooleanExtra(RequestResultConstants.IS_UPDATED_INTENT_KEY, false)) {
			refreshListView();
		}
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	                                ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.context_main_activity_menu, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
    	AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) item.getMenuInfo();
	    switch (item.getItemId()) {
	        case R.id.editEmoticonContextualMenuItem:
	        	Intent intent = new Intent(this, EditEmoticonActivity.class);
	        	intent.putExtra(RequestResultConstants.EMOTICON_ID_INTENT_KEY, contextMenuInfo.targetView.getId());
	        	startActivityForResult(intent, RequestResultConstants.ADD_EDIT_EMOTICON_REQUEST);
	            return true;
	        default:
	            return super.onContextItemSelected(item);
	    }
	}

	private void initialiseComponent() {
		dbRepository = new EmoticonDbRepository(this);

		GridView emoticonGridView = (GridView) findViewById(R.id.emoticonGridView);
		emoticonGridView.setEmptyView(findViewById(R.id.noEmoticonTextView));
		registerForContextMenu(emoticonGridView);
		
		refreshListView();
	}

	protected void refreshListView() {
		emoticons = dbRepository.getAllEmoticons();
		GridView emoticonGridView = (GridView) findViewById(R.id.emoticonGridView);
		emoticonGridView.setAdapter(new EmoticonListViewAdapter(this,
				R.layout.emoticon_row_item, emoticons));
	}
}
