package com.erlanggatjhie.emotextcon.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddEmoticonActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_emoticon_activity);
		initialiseComponent();
	}

	private void initialiseComponent() {
		Button addButton = (Button) findViewById(R.id.addEmoticonButton);
		final EditText contentTextView = (EditText) findViewById(R.id.contentAddEmoticonEditText);
		addButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if ("".equals(contentTextView.getText().toString().trim())) {
					
					Toast.makeText(AddEmoticonActivity.this, getResources().getString(R.string.no_content_error_message), Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
