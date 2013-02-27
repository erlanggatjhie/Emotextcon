package com.erlanggatjhie.emotextcon.activities;

import android.widget.Button;
import android.widget.EditText;

import com.erlanggatjhie.emotextcon.model.Emoticon;


public class AddEmoticonActivity extends AddEditEmoticonActivity {
	@Override
	protected void renameButton(Button button) {
		button.setText(getString(R.string.add_emoticon_button_text));
	}
	
	@Override
	protected void updateEmoticonToDB(EditText descriptionEditText, EditText contentEditText) {
		if (dbRepository.insertEmoticon(new Emoticon(descriptionEditText.getText().toString(), contentEditText.getText().toString()))) {
			showToastMessage(R.string.add_emoticon_success_message);
			descriptionEditText.setText("");
			contentEditText.setText("");
			isUpdated = true;
		} else {
			showToastMessage(R.string.add_emoticon_failure_message);
		}		
	}

	@Override
	protected void setupContentAndDescriptionEditText(
			EditText descriptionEditText, EditText contentEditText) {
		descriptionEditText.setText("");
		contentEditText.setText("");
	}
}
