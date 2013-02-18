package com.erlanggatjhie.emotextcon.activities;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.azeckoski.reflectutils.ReflectUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import android.widget.Button;
import android.widget.EditText;

import com.erlanggatjhie.emotextcon.db.EmoticonDbRepository;
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
		assertThat("Toast message does not show up",
				ShadowToast.getTextOfLatestToast(), equalTo(addEmoticonActivity.getString(R.string.no_content_error_message)));
	}
	
	@Test
	public void shouldDisplayErrorMessageWhenEmptyDescriptionAdded() {
		descriptionEditText.setText("");
		contentEditText.setText("content");
		
		addButton.performClick();
		
		ShadowHandler.idleMainLooper();
		assertThat("Toast message does not show up",
				ShadowToast.getTextOfLatestToast(), equalTo(addEmoticonActivity.getString(R.string.no_description_error_message)));
	}
	
	@Test
	public void shouldAddEmoticon() {
		emoticonDbRepository.deleteAllEmoticons();
		
		descriptionEditText.setText(EMOTICON_DESCRIPTION);
		contentEditText.setText(EMOTICON_CONTENT);
		
		addButton.performClick();
		
		ShadowHandler.idleMainLooper();
		
		assertThat("Toast message does not show up",
				ShadowToast.getTextOfLatestToast(), equalTo(addEmoticonActivity.getString(R.string.add_emoticon_success_message)));
		
		assertThat("Description is not empty", 
				descriptionEditText.getText().toString().isEmpty(), is(true));
		
		assertThat("Content is not empty",
				contentEditText.getText().toString().isEmpty(), is(true));
		
		List<Emoticon> emoticons = emoticonDbRepository.getAllEmoticons();
		assertThat("There are more emoticons that the one that is added",
				emoticons.size(), is(1));
		
		Emoticon emoticon = emoticons.get(0);
		
		assertThat("Added emoticon has different description",
				emoticon.getDescription(), equalTo(EMOTICON_DESCRIPTION));
		
		assertThat("Added emoticon has different content",
				emoticon.getContent(), equalTo(EMOTICON_CONTENT));
	}	
	
	@Test
	public void shouldDisplayErrorMessageIfUnableToAddEmoticon() {
		emoticonDbRepository.deleteAllEmoticons();
		
		descriptionEditText.setText(EMOTICON_DESCRIPTION);
		contentEditText.setText(EMOTICON_CONTENT);
		
		// mock so insert method return false
		EmoticonDbRepository mockEmoticonDbRepository = Mockito.mock(EmoticonDbRepository.class);
		Mockito.when(mockEmoticonDbRepository.insertEmoticon(new Emoticon(EMOTICON_DESCRIPTION, EMOTICON_CONTENT))).thenReturn(false);
		ReflectUtils.getInstance().setFieldValue(addEmoticonActivity, "dbRepository", mockEmoticonDbRepository);
		
		addButton.performClick();
		
		ShadowHandler.idleMainLooper();
		
		assertThat("Error message does not show up",
				ShadowToast.getTextOfLatestToast(), equalTo(addEmoticonActivity.getString(R.string.add_emoticon_failure_message)));		
	}
}
