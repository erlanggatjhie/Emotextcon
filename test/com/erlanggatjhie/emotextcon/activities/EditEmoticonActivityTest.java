package com.erlanggatjhie.emotextcon.activities;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.util.List;

import org.azeckoski.reflectutils.ReflectUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import com.erlanggatjhie.emotextcon.constants.RequestResultConstants;
import com.erlanggatjhie.emotextcon.db.EmoticonDbRepository;
import com.erlanggatjhie.emotextcon.model.Emoticon;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowHandler;
import com.xtremelabs.robolectric.shadows.ShadowToast;

@RunWith(RobolectricTestRunner.class)
public class EditEmoticonActivityTest extends EmoticonActivityTest {
	private final int EMOTICON_ID = 1;
	private final String EMOTICON_DESCRIPTION = "description";
	private final String EMOTICON_CONTENT = "content";
	
	private final String UPDATED_EMOTICON_DESCRIPTION = "description1";
	private final String UPDATED_EMOTICON_CONTENT = "content1";
	
	private EditEmoticonActivity editEmoticonActivity;
	private Button editButton;
	private EditText descriptionEditText;
	private EditText contentEditText;
	
	@Before
	public void setup() {
		editEmoticonActivity = new EditEmoticonActivity();
		editEmoticonActivity.onCreate(null);
		
		ShadowToast.reset();

		setupDbRepository(editEmoticonActivity);
		
		emoticonDbRepository.deleteAllEmoticons();
		emoticonDbRepository.insertEmoticon(new Emoticon(EMOTICON_ID, EMOTICON_DESCRIPTION, EMOTICON_CONTENT));
		
		Intent intent = new Intent();
		intent.putExtra(RequestResultConstants.EMOTICON_ID_INTENT_KEY, EMOTICON_ID);
		editEmoticonActivity.setIntent(intent);
		editEmoticonActivity.setupContentAndDescriptionEditText(
				(EditText) editEmoticonActivity.findViewById(R.id.descriptionAddEditEmoticonEditText), 
				(EditText) editEmoticonActivity.findViewById(R.id.contentAddEditEmoticonEditText)
				);
		
		
		editButton = (Button) editEmoticonActivity.findViewById(R.id.addEditEmoticonButton);
		descriptionEditText = (EditText) editEmoticonActivity.findViewById(R.id.descriptionAddEditEmoticonEditText);
		contentEditText = (EditText) editEmoticonActivity.findViewById(R.id.contentAddEditEmoticonEditText);
	}
	
	@Test
	public void shouldHaveButtonWithTextEdit() {
		assertThat("Button text should be edit",
				editButton.getText().toString(), equalTo(editEmoticonActivity.getString(R.string.edit_emoticon_button_text)));
	}
	
	@Test
	public void shouldPopulateTextField() {
		assertThat("Description text field is not populated correctly", 
				descriptionEditText.getText().toString(), equalTo(EMOTICON_DESCRIPTION));

		assertThat("Content text field is not populated correctly", 
				contentEditText.getText().toString(), equalTo(EMOTICON_CONTENT));
	}
	
	@Test
	public void shouldDisplayAnErrorAndGoBackToPreviousActivity() {
		EditEmoticonActivity spyEmoticonActivity = spy(editEmoticonActivity);
		
		Intent intent = new Intent();
		intent.putExtra(RequestResultConstants.EMOTICON_ID_INTENT_KEY, "xx");
		spyEmoticonActivity.setIntent(intent);
		spyEmoticonActivity.setupContentAndDescriptionEditText(
				(EditText) editEmoticonActivity.findViewById(R.id.contentAddEditEmoticonEditText), 
				(EditText) editEmoticonActivity.findViewById(R.id.descriptionAddEditEmoticonEditText)
				);		
		
		ShadowHandler.idleMainLooper();
		
		assertThat("Toast message does not show up",
				ShadowToast.getTextOfLatestToast(), equalTo(spyEmoticonActivity.getString(R.string.edit_activity_initialisation_error)));

		verify(spyEmoticonActivity, times(1)).onBackPressed();
	}
	
	@Test
	public void shouldDisplayErrorMessageWhenEmptyContentAdded() {
		descriptionEditText.setText("description");
		contentEditText.setText("");
		
		editButton.performClick();
		
		ShadowHandler.idleMainLooper();
		assertThat("Toast message does not show up",
				ShadowToast.getTextOfLatestToast(), equalTo(editEmoticonActivity.getString(R.string.no_content_error_message)));
	}
	
	@Test
	public void shouldDisplayErrorMessageWhenEmptyDescriptionAdded() {
		descriptionEditText.setText("");
		contentEditText.setText("content");
		
		editButton.performClick();
		
		ShadowHandler.idleMainLooper();
		assertThat("Toast message does not show up",
				ShadowToast.getTextOfLatestToast(), equalTo(editEmoticonActivity.getString(R.string.no_description_error_message)));
	}
	
	@Test
	public void shouldEditEmoticon() {
			
		descriptionEditText.setText(UPDATED_EMOTICON_DESCRIPTION);
		contentEditText.setText(UPDATED_EMOTICON_CONTENT);
		
		editButton.performClick();
		
		ShadowHandler.idleMainLooper();
		
		assertThat("Toast message does not show up",
				ShadowToast.getTextOfLatestToast(), equalTo(editEmoticonActivity.getString(R.string.edit_emoticon_success_message)));
		
		List<Emoticon> emoticons = emoticonDbRepository.getAllEmoticons();
		assertThat("There are more emoticons that the one that is added",
				emoticons.size(), is(1));
		
		Emoticon emoticon = emoticons.get(0);
		
		assertThat("Added emoticon has different description",
				emoticon.getDescription(), equalTo(UPDATED_EMOTICON_DESCRIPTION));
		
		assertThat("Added emoticon has different content",
				emoticon.getContent(), equalTo(UPDATED_EMOTICON_CONTENT));
	}	
	
	@Test
	public void shouldDisplayErrorMessageIfUnableToEditEmoticon() {
		emoticonDbRepository.deleteAllEmoticons();
		
		descriptionEditText.setText(UPDATED_EMOTICON_DESCRIPTION);
		contentEditText.setText(UPDATED_EMOTICON_CONTENT);
		
		// mock so insert method return false
		EmoticonDbRepository mockEmoticonDbRepository = Mockito.mock(EmoticonDbRepository.class);
		Mockito.when(mockEmoticonDbRepository.insertEmoticon(new Emoticon(EMOTICON_DESCRIPTION, EMOTICON_CONTENT))).thenReturn(false);
		ReflectUtils.getInstance().setFieldValue(editEmoticonActivity, "dbRepository", mockEmoticonDbRepository);
		
		editButton.performClick();
		
		ShadowHandler.idleMainLooper();
		
		assertThat("Error message does not show up",
				ShadowToast.getTextOfLatestToast(), equalTo(editEmoticonActivity.getString(R.string.edit_emoticon_failure_message)));		
	}

}
