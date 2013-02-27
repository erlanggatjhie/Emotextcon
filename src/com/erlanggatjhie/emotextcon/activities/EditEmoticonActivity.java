package com.erlanggatjhie.emotextcon.activities;

import android.widget.Button;
import android.widget.EditText;

import com.erlanggatjhie.emotextcon.constants.RequestResultConstants;
import com.erlanggatjhie.emotextcon.model.Emoticon;


public class EditEmoticonActivity extends AddEditEmoticonActivity {
	private Emoticon emoticon;
	
	@Override
	protected void renameButton(Button button) {
		button.setText(getString(R.string.edit_emoticon_button_text));
	}

	@Override
	protected void updateEmoticonToDB(EditText descriptionEditText,
			EditText contentEditText) {
		emoticon.setDescription(descriptionEditText.getText().toString());
		emoticon.setContent(contentEditText.getText().toString());
		
		if (dbRepository.updateEmoticon(emoticon)) {
			showToastMessage(R.string.edit_emoticon_success_message);
			isUpdated = true;
		} else {
			showToastMessage(R.string.edit_emoticon_failure_message);
		}				
	}

	@Override
	protected void setupContentAndDescriptionEditText(
			EditText descriptionEditText, EditText contentEditText) {	
		try {
			emoticon = dbRepository.getEmoticonById(getIntent().getExtras().getInt(RequestResultConstants.EMOTICON_ID_INTENT_KEY));
			descriptionEditText.setText(emoticon.getDescription());
			contentEditText.setText(emoticon.getContent());
		} catch (Exception e) {
			showToastMessage(R.string.edit_activity_initialisation_error);
			onBackPressed();
		}
	}
	
}
