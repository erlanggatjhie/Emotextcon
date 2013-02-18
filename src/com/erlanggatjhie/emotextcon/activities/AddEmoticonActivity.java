package com.erlanggatjhie.emotextcon.activities;

import com.erlanggatjhie.emotextcon.db.EmoticonDbRepository;
import com.erlanggatjhie.emotextcon.model.Emoticon;

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
		final EditText contentEditText = (EditText) findViewById(R.id.contentAddEmoticonEditText);
		final EditText descriptionEditText = (EditText) findViewById(R.id.descriptionAddEmoticonEditText);
		
		addButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (checkEditTextIsEmpty(descriptionEditText)) {
					showToastMessage(R.string.no_description_error_message);
				} else if (checkEditTextIsEmpty(contentEditText)) {
					showToastMessage(R.string.no_content_error_message);
				} else {
					if (dbRepository.insertEmoticon(new Emoticon(descriptionEditText.getText().toString(), contentEditText.getText().toString()))) {
						showToastMessage(R.string.add_emoticon_success_message);
						descriptionEditText.setText("");
						contentEditText.setText("");
					} else {
						showToastMessage(R.string.add_emoticon_failure_message);
					}
				}
			}
		});
	}
	
	private boolean checkEditTextIsEmpty(EditText editText) {
		return "".equals(editText.getText().toString().trim());
	}
	
	private void showToastMessage(int resourceId) {
		Toast.makeText(AddEmoticonActivity.this, getResources().getString(resourceId), Toast.LENGTH_SHORT).show();	
	}
}
