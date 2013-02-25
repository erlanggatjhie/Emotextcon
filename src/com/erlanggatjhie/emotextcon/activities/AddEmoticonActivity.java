package com.erlanggatjhie.emotextcon.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.erlanggatjhie.emotextcon.constants.RequestResultConstants;
import com.erlanggatjhie.emotextcon.db.EmoticonDbRepository;
import com.erlanggatjhie.emotextcon.model.Emoticon;

public class AddEmoticonActivity extends Activity {
	private EmoticonDbRepository dbRepository;
	private boolean isUpdated;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_edit_emoticon_activity);
		initialiseComponent();
	}
	
	@Override
	public void onBackPressed() {
		Intent intent = new Intent();
		intent.putExtra(RequestResultConstants.IS_UPDATED_INTENT_KEY, isUpdated);
		setResult(RequestResultConstants.ADD_EDIT_EMOTICON_RESULT, intent);
		super.onBackPressed();
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
						isUpdated = true;
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
