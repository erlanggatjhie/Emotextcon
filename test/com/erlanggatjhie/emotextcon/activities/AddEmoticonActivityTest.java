package com.erlanggatjhie.emotextcon.activities;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.widget.Button;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowHandler;
import com.xtremelabs.robolectric.shadows.ShadowToast;


@RunWith(RobolectricTestRunner.class)
public class AddEmoticonActivityTest {
	private AddEmoticonActivity addEmoticonActivity;
	private Button addButton;
	
	@Before
	public void setup() {
		addEmoticonActivity = new AddEmoticonActivity();
		addEmoticonActivity.onCreate(null);
		addButton = (Button) addEmoticonActivity.findViewById(R.id.addEmoticonButton);
	}
	
	@Test
	public void shouldDisplayErrorMessageWhenEmptyContentAdded() {
		addButton.performClick();
		
		ShadowHandler.idleMainLooper();
		assertThat(ShadowToast.getTextOfLatestToast(), equalTo(addEmoticonActivity.getResources().getString(R.string.no_content_error_message)));
	}
}
