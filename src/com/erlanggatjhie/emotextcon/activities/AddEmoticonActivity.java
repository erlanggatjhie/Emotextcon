package com.erlanggatjhie.emotextcon.activities;

import com.erlanggatjhie.emotextcon.db.EmoticonDbRepository;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddEmoticonActivity extends Activity {
	private EmoticonDbRepository dbRepository;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_emoticon_activity);
		initialiseComponent();
	}

	private void initialiseComponent() {
		dbRepository = new EmoticonDbRepository(this);
		
		Button addButton = (Button) findViewById(R.id.addEmoticonButton);
		final EditText contentTextView = (EditText) findViewById(R.id.contentAddEmoticonEditText);
		final EditText descriptionTextView = (EditText) findViewById(R.id.descriptionAddEmoticonEditText);
		
		addButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (checkEditTextIsEmpty(descriptionTextView)) {
					Toast.makeText(AddEmoticonActivity.this, getResources().getString(R.string.no_description_error_message), Toast.LENGTH_SHORT).show();
				} else if (checkEditTextIsEmpty(contentTextView)) {
					Toast.makeText(AddEmoticonActivity.this, getResources().getString(R.string.no_content_error_message), Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		
	}
	
	private boolean checkEditTextIsEmpty(EditText editText) {
		return "".equals(editText.getText().toString().trim());
	}
}
