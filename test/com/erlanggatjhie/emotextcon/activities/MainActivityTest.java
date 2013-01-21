package com.erlanggatjhie.emotextcon.activities;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.erlanggatjhie.emotextcon.MainActivity;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

	@Test
	public void shouldHaveTitle() {
		MainActivity mainActivity = new MainActivity();
		System.out.println(mainActivity.getTitle());
	}
	
}
