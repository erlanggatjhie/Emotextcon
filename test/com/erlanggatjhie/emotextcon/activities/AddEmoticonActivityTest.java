package com.erlanggatjhie.emotextcon.activities;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.widget.Button;
import android.widget.EditText;

import com.erlanggatjhie.emotextcon.model.Emoticon;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowHandler;
import com.xtremelabs.robolectric.shadows.ShadowToast;


@RunWith(RobolectricTestRunner.class)
public class AddEmoticonActivityTest extends EmoticonActivityTest {
	private final String EMOTICON_DESCRIPTION = "description";
	private final String EMOTICON_CONTENT = "content";
	
	private AddEmoticonActivity addEmoticonActivity;
	private Button addButton;
	private EditText descriptionEditText;
	private EditText contentEditText;
	
	@Before
	public void setup() {
		addEmoticonActivity = new AddEmoticonActivity();
		addEmoticonActivity.onCreate(null);
		
		setupDbRepository(addEmoticonActivity);
		
		addButton = (Button) addEmoticonActivity.findViewById(R.id.addEmoticonButton);
		descriptionEditText = (EditText) addEmoticonActivity.findViewById(R.id.descriptionAddEmoticonEditText);
		contentEditText = (EditText) addEmoticonActivity.findViewById(R.id.contentAddEmoticonEditText);
	}
	
	@Test
	public void shouldDisplayErrorMessageWhenEmptyContentAdded() {
		descriptionEditText.setText("description");
		contentEditText.setText("");
		
		addButton.performClick();
		
		ShadowHandler.idleMainLooper();
		assertThat(ShadowToast.getTextOfLatestToast(), equalTo(addEmoticonActivity.getResources().getString(R.string.no_content_error_message)));
	}
	
	@Test
	public void shouldDisplayErrorMessageWhenEmptyDescriptionAdded() {
		descriptionEditText.setText("");
		contentEditText.setText("content");
		
		addButton.performClick();
		
		ShadowHandler.idleMainLooper();
		assertThat(ShadowToast.getTextOfLatestToast(), equalTo(addEmoticonActivity.getResources().getString(R.string.no_description_error_message)));
	}
	
	@Test
	public void shouldAddEmoticon() {
		emoticonDbRepository.deleteAllEmoticons();
		
		descriptionEditText.setText(EMOTICON_DESCRIPTION);
		contentEditText.setText(EMOTICON_CONTENT);
		
		addButton.performClick();
		
		ShadowHandler.idleMainLooper();
		
		assertThat(ShadowToast.getTextOfLatestToast(), equalTo(addEmoticonActivity.getResources().getString(R.string.add_emoticon_success_message)));
		assertThat(descriptionEditText.getText().toString().isEmpty(), is(true));
		assertThat(contentEditText.getText().toString().isEmpty(), is(true));
		
		List<Emoticon> emoticons = emoticonDbRepository.getAllEmoticons();
		assertThat(emoticons.size(), is(1));
		
		Emoticon emoticon = emoticons.get(0);
		assertThat(emoticon.getDescription(), equalTo(EMOTICON_DESCRIPTION));
		assertThat(emoticon.getDescription(), equalTo(EMOTICON_CONTENT));
	}	
}
