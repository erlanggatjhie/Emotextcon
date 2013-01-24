package com.erlanggatjhie.emotextcon.db;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.erlanggatjhie.emotextcon.model.Emoticon;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class EmoticonDbRepositoryTest {
	@Test
	public void shouldReturnAllEmoticons() {
		EmoticonDbRepository emoticonDbRepository = new EmoticonDbRepository(null);
		
		List<Emoticon> expectedEmoticons = new ArrayList<Emoticon>();
		expectedEmoticons.add(new Emoticon("desc1", "content1"));
		expectedEmoticons.add(new Emoticon("desc2", "content2"));
		expectedEmoticons.add(new Emoticon("desc3", "content3"));
		
		List<Emoticon> actualEmoticons = emoticonDbRepository.getAllEmoticons();
		
		for (int i = 0 ; i < expectedEmoticons.size(); i++) {
			assertThat(actualEmoticons.get(i), equalTo(expectedEmoticons.get(i)));
		}
	}
}
