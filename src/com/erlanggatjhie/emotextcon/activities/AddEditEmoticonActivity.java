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

public abstract class AddEditEmoticonActivity extends Activity {
	protected boolean isUpdated;
	protected EmoticonDbRepository dbRepository;
	
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
	
	protected abstract void renameButton(Button button);
	
	protected abstract void updateEmoticonToDB(EditText descriptionEditText, EditText contentEditText);
	
	protected abstract void setupContentAndDescriptionEditText(EditText descriptionEditText, EditText contentEditText);
	
	protected void showToastMessage(int resourceId) {
		Toast.makeText(AddEditEmoticonActivity.this, getResources().getString(resourceId), Toast.LENGTH_SHORT).show();	
	}

	private void initialiseComponent() {
		dbRepository = new EmoticonDbRepository(this);
		
		Button addEditButton = (Button) findViewById(R.id.addEditEmoticonButton);
		renameButton(addEditButton);
		
		final EditText contentEditText = (EditText) findViewById(R.id.contentAddEditEmoticonEditText);
		final EditText descriptionEditText = (EditText) findViewById(R.id.descriptionAddEditEmoticonEditText);
		setupContentAndDescriptionEditText(descriptionEditText, contentEditText);
		
		addEditButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (checkEditTextIsEmpty(descriptionEditText)) {
					showToastMessage(R.string.no_description_error_message);
				} else if (checkEditTextIsEmpty(contentEditText)) {
					showToastMessage(R.string.no_content_error_message);
				} else {
					updateEmoticonToDB(descriptionEditText, contentEditText);
				}
			}
		});
	}
	
	private boolean checkEditTextIsEmpty(EditText editText) {
		return "".equals(editText.getText().toString().trim());
	}
	

}
