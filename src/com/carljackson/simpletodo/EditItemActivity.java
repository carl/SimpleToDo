package com.carljackson.simpletodo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends Activity {
	EditText editText;
	int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		position = getIntent().getIntExtra("position",  0);
		String value = getIntent().getStringExtra("value");
		editText = (EditText) findViewById(R.id.editText);
		editText.setText(value);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}

	public void saveItemAndFinish(View view) {
		EditText editText = (EditText) findViewById(R.id.editText);
		Intent data = new Intent();
		data.putExtra("value", editText.getText().toString());
		data.putExtra("position",  position);
		setResult(RESULT_OK, data);
		finish();
	}
}
